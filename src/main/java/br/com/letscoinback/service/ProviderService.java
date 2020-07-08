package br.com.letscoinback.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.letscoinback.persistence.entity.ProviderEntity;
import br.com.letscoinback.persistence.repository.ProviderRepository;

@Service
public class ProviderService {

	@Autowired
	ProviderRepository providerRepository;
	
	public List<ProviderEntity> getAll () {
		return providerRepository.findAll();
	}
	
	public ProviderEntity getById(Integer id) {
		return providerRepository.findById(id).orElse(null);
	}
}
