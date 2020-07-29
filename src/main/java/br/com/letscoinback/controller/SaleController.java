package br.com.letscoinback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.letscoinback.service.SaleService;

@RestController
@RequestMapping("sale")
public class SaleController {

	@Autowired
    SaleService saleService;
	
	@GetMapping("/confirm")
	public void confirmPreSale (
			@RequestParam(required = false) String preSaleId, 
			@RequestParam(required = false) String transaction, 
			@RequestParam(required = false) String saleValue,
			@RequestParam(required = false) String cashbackValue) {
		try {
			saleService.confirmSale(preSaleId, transaction, saleValue, cashbackValue);	
		} catch (Exception e) {
			return;
		}		
	}

	@GetMapping("/pre/{id}")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER') or hasAuthority('MASTER')")
	public Long createPreSale(@AuthenticationPrincipal Jwt jwt, @PathVariable Integer id) {
		Integer userId = Integer.valueOf(jwt.getClaimAsString("id"));
		return saleService.savePreSale(userId, id);
	}
}