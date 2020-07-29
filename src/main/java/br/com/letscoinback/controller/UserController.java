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

import br.com.letscoinback.dto.ChangePasswordDTO;
import br.com.letscoinback.dto.ConfigurationDTO;
import br.com.letscoinback.dto.IndicateDTO;
import br.com.letscoinback.dto.NotificationDTO;
import br.com.letscoinback.dto.RegisterDTO;
import br.com.letscoinback.dto.UserDTO;
import br.com.letscoinback.service.NotificationService;
import br.com.letscoinback.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	NotificationService notificationService;
	
	@GetMapping("/notification")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER') or hasAuthority('MASTER')")
	public List<NotificationDTO> getNotification (@AuthenticationPrincipal Jwt jwt) {
		Integer id = Integer.valueOf(jwt.getClaimAsString("id"));
		return notificationService.getUserNotification(id);
	}
	
	@GetMapping("/indicate")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER') or hasAuthority('MASTER')")
	public List<IndicateDTO> getIndicate (@AuthenticationPrincipal Jwt jwt) {
		Integer id = Integer.valueOf(jwt.getClaimAsString("id"));
		return userService.getIndicateUser(id);
	}
	
	@GetMapping("/notification/read/{id}")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER') or hasAuthority('MASTER')")
	public void readNotification (@PathVariable Integer id) {
		notificationService.readNotification(id);
	}
	@GetMapping("/all")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MASTER')")
	public List<UserDTO> login () {
		return userService.getAll();
	}
	
	@GetMapping("/authority/change/{id}")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MASTER')")
	public void changeAuthority (@PathVariable Integer id, @RequestParam String authority) {
		userService.changeAuthority(id, authority);
	}

	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER') or hasAuthority('MASTER')")
	@PostMapping("/update")
	public void updateUser(@RequestBody UserDTO user, @AuthenticationPrincipal Jwt jwt) {
		userService.updateUser(user, jwt.getClaimAsString("id"));
	}
	
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER') or hasAuthority('MASTER')")
	@PostMapping("/password/change")
	public void changePassword(@RequestBody ChangePasswordDTO pass, @AuthenticationPrincipal Jwt jwt) {
		userService.changePassword(pass, jwt.getClaimAsString("id"));
	}
	
	@PostMapping("/register")
	public void registerUser(@RequestBody RegisterDTO user) {
		userService.registerUser(user);
	}
	
	@GetMapping("/cashback/default")
	public ConfigurationDTO getDefaultCashback () {
		return userService.getDefaultCashback();
	}
	
	@GetMapping("/indicate/{id}")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER') or hasAuthority('MASTER')")
	public IndicateDTO getUserIndicate (@PathVariable Integer id) {
		return userService.getUserIndicate(id);
	}
}
