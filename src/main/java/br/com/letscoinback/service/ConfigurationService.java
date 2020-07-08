package br.com.letscoinback.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.letscoinback.dto.ConfigurationDTO;
import br.com.letscoinback.exception.BusinessRunTimeException;
import br.com.letscoinback.persistence.entity.ConfigurationEntity;
import br.com.letscoinback.persistence.repository.ConfigurationRepository;
import br.com.letscoinback.util.Translator;

@Service
public class ConfigurationService {
	
	@Autowired
	ConfigurationRepository configurationRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	public List<ConfigurationDTO> getAll () {
		return configurationRepository.findAll().stream()
					.map(c -> modelMapper.map(c, ConfigurationDTO.class))
					.collect(Collectors.toList());
	}
	
	
	public void save (ConfigurationDTO config) {
		if (config.getDescription() == null || config.getKey() == null || config.getValue() == null) {
			throw new BusinessRunTimeException(Translator.toLocale("paramter.fail"));
		}
		configurationRepository.save(modelMapper.map(config, ConfigurationEntity.class));
	}
	
	public ConfigurationDTO getById (String id) {
		return modelMapper.map(configurationRepository.findById(id).orElse(null), ConfigurationDTO.class);
	}
}
