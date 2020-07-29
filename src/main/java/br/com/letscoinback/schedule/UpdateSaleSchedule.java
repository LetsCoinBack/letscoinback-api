package br.com.letscoinback.schedule;

import java.io.StringReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import br.com.letscoinback.config.enums.AwinSaleStatusEnum;
import br.com.letscoinback.config.enums.NotificationTypeEnum;
import br.com.letscoinback.config.enums.ProviderSaleStatusEnum;
import br.com.letscoinback.dto.ScheduleSaleDTO;
import br.com.letscoinback.exception.BusinessRunTimeException;
import br.com.letscoinback.persistence.entity.ProviderEntity;
import br.com.letscoinback.persistence.entity.Sale;
import br.com.letscoinback.persistence.entity.Wallet;
import br.com.letscoinback.service.NotificationService;
import br.com.letscoinback.service.ProviderService;
import br.com.letscoinback.service.SaleService;
import br.com.letscoinback.service.WalletService;

@Component
@EnableScheduling
public class UpdateSaleSchedule {

	@Autowired
	ProviderService providerService;
	
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	SaleService saleService;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	WalletService walletService;
	
	@Autowired
	NotificationService notificationService;
	
	@Value("${apimode.schedule.enable}")
	Boolean enable;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UpdateSaleSchedule.class);
	
	@Scheduled(cron = "0 0 1 * * *", zone = "America/Sao_Paulo")
	public void updateSale() {
		if (!enable) {
			return;
		}
		LOGGER.info("Iniciando a JOB - Sincronização de vendas com provedores");
		List<ProviderEntity> providers = providerService.getAll();
		LOGGER.info("Provedores encontrados: " + providers.size());
		providers.forEach(p -> updateProviderSale(p));
	}

	private void updateProviderSale (ProviderEntity provider) {
		LOGGER.info("Atualizando o provedor: " + provider.getName());
		String lomadee = "Lomadee";
		String awin = "Awin";
		try {
			if (lomadee.equals(provider.getName())) {
				updateLomadee(provider);
			}
			if (awin.equals(provider.getName())) {
				updateAwin(provider);
			}
		} catch (Exception e) {
			LOGGER.error("Falha ao atualizar provedor", e);
		}
	}

	private void updateAwin(ProviderEntity provider) {
		LocalDate startDate = provider.getLastUpdate() == null ? LocalDate.now().minusDays(15) : provider.getLastUpdate().toLocalDate();
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(provider.getUrl() + provider.getPublisher() + "/transactions")
				.queryParam("accessToken", provider.getUser())
				.queryParam("timezone", "UTC")
				.queryParam("startDate", startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "T00:00:00" )
				.queryParam("endDate", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "T23:59:59");
		@SuppressWarnings("unchecked")
		JSONObject[] response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET,new HttpEntity<>(new HttpHeaders()), JSONObject[].class)
													  .getBody();
		validateSales(response);
		
	}

	private void validateSales(JSONObject[] response) {
		List<JSONObject> listResponse = Arrays.asList(response);
		listResponse.forEach(json -> {
			try {
				String id = json.getString("id");
				if (id != null) {
					ScheduleSaleDTO dto = new ScheduleSaleDTO();
					Long preSaleId = Long.valueOf(json.getJSONObject("clickRefs").getString("clickRef"));
					dto.setAssociateId(preSaleId);
					dto.setTransaction(id);
					dto.setStatusId(AwinSaleStatusEnum.getByDescription(json.getString("commissionStatus")));
					dto.setTotalValue(json.getJSONObject("saleAmount").getString("amount"));
					dto.setComissionValue(json.getJSONObject("commissionAmount").getString("amount"));
					updateSale(dto);
				}
			} catch (JSONException e) {
				LOGGER.error("Falha ao mapear JSON", e);
			}
		});
	}
	
	private void updateLomadee (ProviderEntity provider) {
		String token = getToken(provider);
		getXmlSales(provider, token);
	}

	private void getXmlSales(ProviderEntity provider, String token) {
		try {
			LocalDate startDate = provider.getLastUpdate() == null ? LocalDate.now().minusDays(15) : provider.getLastUpdate().toLocalDate();
			
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(provider.getUrl() + "reportTransaction/")
					.queryParam("token", token)
					.queryParam("publisherId", provider.getPublisher())
					.queryParam("startDate", startDate.format(DateTimeFormatter.ofPattern("ddMMyyyy")))
					.queryParam("endDate", LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy")));
			ResponseEntity<String> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET,new HttpEntity<>(new HttpHeaders()), String.class);
			Document doc = convertStringToXMLDocument(response.getBody());
			executeXml(doc);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessRunTimeException("provider.sale.find");
		}
	}

	private void executeXml(Document doc) {
		NodeList children = doc.getChildNodes().item(0).getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			if (children.item(i).getNodeName().equals("item")) {
				updateSale(convertXmlToDto(children.item(i).getChildNodes()));
			}
		}
	}
	
	private void updateSale (ScheduleSaleDTO dto) {
		Sale sale = saleService.getByTransacionProvider(dto.getTransaction())
				.orElse(saleService.getByPreSaleId(dto.getAssociateId()));
		if (sale == null) {
			LOGGER.info("Pré venda não encontrada, confirmando a venda!");
			sale = saleService.confirmSale(dto.getAssociateId().toString(), dto.getTransaction(), dto.getTotalValue(), dto.getComissionValue());
		}
		String status = ProviderSaleStatusEnum.getById(dto.getStatusId());
		Wallet wallet = sale.getWallet();
		if (!wallet.getStatus().equals(status)) {
			LOGGER.info("Atualizando venda: " + sale.getId());
			wallet.setStatus(status);
			walletService.saveWallet(wallet);
			String tiyle = "Status da Compra: " + status;
			String body = "A compra que você fez na loja " + sale.getPreSale().getPartner().getName() + ", recebeu status: " + status + " pela loja. Verifique sua carteira!";
			notificationService.sendNotification(wallet.getUserId(), tiyle, body, NotificationTypeEnum.PURCHASE_CHANGE);
		}
	}

	private ScheduleSaleDTO convertXmlToDto(NodeList children) {
		ScheduleSaleDTO dto = new ScheduleSaleDTO();
		for (int i = 0; i < children.getLength(); i++) {
			Node node = children.item(i);
			if (node.getNodeName().equals("statusId")) {
				dto.setStatusId(Integer.valueOf(node.getTextContent()));
			}
			if (node.getNodeName().equals("transactionId")) {
				dto.setTransaction(node.getTextContent());
			}
			if (node.getNodeName().equals("associateId")) {
				dto.setAssociateId(Long.valueOf(node.getTextContent()));
			}
			if (node.getNodeName().equals("commission")) {
				dto.setComissionValue(node.getTextContent());
			}
			if (node.getNodeName().equals("gmv")) {
				dto.setTotalValue(node.getTextContent());
			}
		}
		return dto;
	}
	
	private static Document convertStringToXMLDocument(String xmlString) {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	private String getToken(ProviderEntity provider) {
		try {
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(provider.getUrl() + "createToken/")
					.queryParam("user", provider.getUser()).queryParam("password", provider.getPassword());

			return restTemplate
					.exchange(builder.toUriString(), HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), Map.class)
					.getBody().get("token").toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessRunTimeException("provider.login.failed");
		}
	}

}
