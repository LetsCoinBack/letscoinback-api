package br.com.letscoinback.schedule;

import java.io.StringReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

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
	
	@Scheduled(cron = "0 0 1 * * *", zone = "America/Sao_Paulo")
	public void updateSale() {
		if (!enable) {
			return;
		}
		List<ProviderEntity> providers = providerService.getAll();
		providers.forEach(p -> updateProviderSale(p));
	}

	@Transactional
	private void updateProviderSale (ProviderEntity provider) {
		String token = getToken(provider);
		getXmlSales(provider, token);
	}


	private void getXmlSales(ProviderEntity provider, String token) {
		try {
			LocalDate startDate = provider.getLastUpdate() == null ? LocalDate.now().minusDays(15) : provider.getLastUpdate().toLocalDate();
			
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(provider.getUrl() + "reportTransaction/")
					.queryParam("token", token)
					.queryParam("publisherId", provider.getPublisher())
					.queryParam("startDate", startDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")))
					.queryParam("endDate", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));

			String response = restTemplate
					.exchange(builder.toUriString(), HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), String.class)
					.getBody();
			Document doc = convertStringToXMLDocument(response);
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
			return;
		}
		String status = ProviderSaleStatusEnum.getById(dto.getStatusId());
		Wallet wallet = sale.getWallet();
		if (!wallet.getStatus().equals(status)) {
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