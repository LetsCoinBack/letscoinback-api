package br.com.letscoinback.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.letscoinback.dto.PartnerDTO;
import br.com.letscoinback.dto.PartnerProviderDTO;
import br.com.letscoinback.service.PartnerService;

@RestController
@RequestMapping("partner")
public class PartnerController {
	
	@Autowired
	PartnerService partnerService;
	
	@GetMapping("/all")
	public List<PartnerProviderDTO> login (@AuthenticationPrincipal Jwt jwt) {
		Boolean isAdmin = jwt != null && jwt.getClaimAsString("authorities").toUpperCase().indexOf("ADMIN") > 0;
		return partnerService.getAll(isAdmin);
	}
	
	@GetMapping("/valid/url")
	public Boolean getBy (@RequestParam String url) {
		return partnerService.validadeUrlIsPartner(url);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/register/provider/{id}")
	public void registerPartner(@RequestBody PartnerDTO ptr,@PathVariable Integer id) {
		partnerService.savePartner(ptr, id);
	}

}
