package br.com.letscoinback.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.letscoinback.dto.ConfigurationDTO;
import br.com.letscoinback.service.MasterConfigurationService;

@RestController
@RequestMapping("configuration/master/")
public class ConfigurationController {
	
	
	@Autowired
    MasterConfigurationService configurationService;
	
	@GetMapping("/all")
	@PreAuthorize("hasAuthority('MASTER')")
	public List<ConfigurationDTO> getAll() {
		return configurationService.getAll();
	}
	
	@PostMapping("/save") 
	@PreAuthorize("hasAuthority('MASTER')")
	public void save(@RequestBody ConfigurationDTO config) {
		configurationService.save(config);
	}
}
