package br.com.letscoinback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.letscoinback.service.SaleService;

@RestController
@RequestMapping("sale")
public class SaleController {

	@Autowired
    SaleService saleService;
	
	@GetMapping("/pre/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Long createPreSale(@AuthenticationPrincipal Jwt jwt, @RequestParam Integer id) {
		Integer userId = Integer.valueOf(jwt.getClaimAsString("id"));
		return saleService.savePreSale(userId, id);
	}
}