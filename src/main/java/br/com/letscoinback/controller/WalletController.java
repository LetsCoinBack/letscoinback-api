package br.com.letscoinback.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.letscoinback.dto.UserWalletDTO;
import br.com.letscoinback.interfaces.projection.WalletStatusTotalProjection;
import br.com.letscoinback.service.WalletService;

@RestController
@RequestMapping("wallet")
public class WalletController {
	
	@Autowired
	WalletService walletService;

	@GetMapping("/user")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public List<UserWalletDTO> getWallet(@AuthenticationPrincipal Jwt jwt) {
		Integer id = Integer.valueOf(jwt.getClaimAsString("id"));
		return walletService.getWalletByUser(id);
	}
	
	@GetMapping("/user/draw")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public void drawMoney(@AuthenticationPrincipal Jwt jwt, @RequestParam("value") Float value) {
		Integer id = Integer.valueOf(jwt.getClaimAsString("id"));
		 walletService.transfer(id, value);
	}
	
	@GetMapping("/user/balance")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public List<WalletStatusTotalProjection> getTotalStatusByUser(@AuthenticationPrincipal Jwt jwt) {
		Integer id = Integer.valueOf(jwt.getClaimAsString("id"));
		return walletService.getTotalFromUser(id);
	}	
	
	@GetMapping("/balance")
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<WalletStatusTotalProjection> getTotal(
		@RequestParam("status") Optional<String> status, 
		@RequestParam("transactionType") Optional<String> transactionType, 
		@RequestParam("movimentationType") Optional<String> movimentationType) {
		return walletService.getTotalDashboard(false, null, status, transactionType, movimentationType);
	}
	
	@GetMapping("/balance/history")
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<WalletStatusTotalProjection> getTotalHistory(
		@RequestParam("status") Optional<String> status, 
		@RequestParam("transactionType") Optional<String> transactionType, 
		@RequestParam("movimentationType") Optional<String> movimentationType) {
		return walletService.getTotalDashboard(true, null, status, transactionType, movimentationType);
	}
}
