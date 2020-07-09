package br.com.letscoinback.service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.letscoinback.config.enums.MovimentationType;
import br.com.letscoinback.config.enums.NotificationTypeEnum;
import br.com.letscoinback.exception.BusinessRunTimeException;
import br.com.letscoinback.persistence.entity.Partner;
import br.com.letscoinback.persistence.entity.PreSale;
import br.com.letscoinback.persistence.entity.Sale;
import br.com.letscoinback.persistence.entity.User;
import br.com.letscoinback.persistence.entity.Wallet;
import br.com.letscoinback.persistence.repository.PreSaleRepository;
import br.com.letscoinback.persistence.repository.SaleRepository;

@Service
public class SaleService {
	
	@Autowired
	PartnerService partnerService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	PreSaleRepository preSaleRepository;
	
	@Autowired
	SaleRepository saleRepository;
	
	@Autowired
	WalletService walletService;
	
	@Autowired
	NotificationService notificationService;
	
	@Autowired
	ConfigurationService configurationService;
	
	public Optional<Sale> getByTransacionProvider (String transaction) {
		return saleRepository.findByTransactionProvider(transaction);
	}
	
	public Sale getByPreSaleId (Long id) {
		return saleRepository.findByPreSaleId(id);
	}
	
	@Transactional
	public void confirmSale (Long preSaleId, String transaction, String value, String cashbackValue) {
		if (!validadeParams(preSaleId, transaction, value, cashbackValue)) {
			return;
		}
		Float saleValue = Float.valueOf(value);
		PreSale preSale = preSaleRepository.findById(preSaleId).get();
		Float cashback = getCashBack(preSale, saleValue);
		Wallet wallet = saveWallet(preSale.getUser().getId(), cashback, preSale);
		Sale sale = createSale(transaction, cashbackValue, saleValue, preSale, wallet);
		saleRepository.save(sale);
		String body = "A compra que você fez na loja " + preSale.getPartner().getName() + " foi confirmada, assim que o pagamento for confirmado seu crédito será liberado.";
		notificationService.sendNotification(wallet.getUserId(), "Compra confirmada", body, NotificationTypeEnum.PURCHASE_APPROVE);
	}

	private Sale createSale(String transaction, String cashbackValue, Float saleValue, PreSale preSale, Wallet wallet) {
		Sale sale = new Sale();
		sale.setCashbackValue(Float.valueOf(cashbackValue));
		sale.setWallet(wallet);
		sale.setPreSale(preSale);
		sale.setTransactionProvider(transaction);
		sale.setSaleValue(saleValue);
		return sale;
	}
	
	private Float getCashBack (PreSale preSale, Float saleValue) {
		Float cashback = preSale.getPartner().getUserCashback();
		if (cashback == null) {
			cashback = Float.valueOf(configurationService.getById("DEFAULT_CASHBACK_USER").getValue());
		}
		Float lqx = Float.valueOf(configurationService.getById("LQX_QUOTATION").getValue());
		return (saleValue * (cashback / 100F)) / lqx;
	}
	private Wallet saveWallet (Integer user, Float value, PreSale preSale) {
		String description = "Cashback - " + preSale.getPartner().getName();
		Wallet w = new Wallet();
		w.setMovimentationType(MovimentationType.ENTRADA.getDescription());
		w.setStatus("Pendente");
		w.setTransactionType("Cashback");
		w.setUserId(user);
		w.setDate(LocalDateTime.now());
		w.setValue(value);
		w.setDescription(description);
		return walletService.saveWallet(w);
	}
	
	private Boolean validadeParams (Long preSaleId, String transaction, String saleValue, String cashbackValue) { // Método alterado para Boolean, pois a lomadee nao aceite retorno diferente de 200
		if(preSaleId == null || transaction == null || saleValue == null || cashbackValue == null) {
			return false;
		}
		if ( saleRepository.findByPreSaleId(preSaleId).getId() != null) {
			return false;
		}
		return true;
	}
	
	public Long savePreSale (Integer userId, Integer partnerId) {
		PreSale pre = createPreSale(userId, partnerId);
		return preSaleRepository.save(pre).getId();
	}
	
	private PreSale createPreSale (Integer userId, Integer partnerId) {
		PreSale pre = new PreSale();
		User user = userService.getUser(userId);
		Partner partner = partnerService.getById(partnerId);
		if (user == null || partner == null) {
			throw new BusinessRunTimeException("paramter.fail");
		}
		pre.setPartner(partner);
		pre.setUser(user);
		return pre;
	}

}
