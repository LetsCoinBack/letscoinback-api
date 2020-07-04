package br.com.letscoinback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.letscoinback.exception.BusinessRunTimeException;
import br.com.letscoinback.persistence.entity.Partner;
import br.com.letscoinback.persistence.entity.PreSale;
import br.com.letscoinback.persistence.entity.User;
import br.com.letscoinback.persistence.repository.PreSaleRepository;

@Service
public class SaleService {
	
	@Autowired
	PartnerService partnerService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	PreSaleRepository preSaleRepository;
	
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
