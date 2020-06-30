package br.com.model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.model.service.ModelService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("model")
public class ModelController {
	
	@Autowired
    ModelService modelService;
	
	// As anoteções @ApiOperation e @ApiResponses serve para melhorar o nível de documentação da api no swagger
	@ApiOperation(value = "Return a String message with i18n.")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Return the string.")
	})
	@GetMapping("/hello")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String hello(@AuthenticationPrincipal Jwt jwt) {
		System.out.println(jwt.getClaimAsString("name")); // Caso precisar acessar os dados do JWT
		return modelService.hello();
	}
}
