package br.com.letscoinback.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.letscoinback.dto.ProviderDTO;
import br.com.letscoinback.service.ProviderService;

@RestController
@RequestMapping("provider")
public class ProviderController {
	
	
	@Autowired
    ProviderService providerService;
	
	@Autowired
	ModelMapper modelMapper;
	
	@GetMapping("/all")
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<ProviderDTO> getAll() {
		return providerService.getAll().stream()
					.map(p -> modelMapper.map(p, ProviderDTO.class))
					.collect(Collectors.toList());
	}

}
