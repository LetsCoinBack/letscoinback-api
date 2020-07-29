package br.com.letscoinback.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.letscoinback.dto.MasterProviderDTO;
import br.com.letscoinback.persistence.entity.ProviderEntity;
import br.com.letscoinback.persistence.repository.ProviderRepository;

@Service
public class ProviderService {

	@Autowired
	ProviderRepository providerRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	public List<ProviderEntity> getAll () {
		return providerRepository.findAll();
	}
	
	public List<MasterProviderDTO> getAllMaster () {
		return providerRepository.findAll().stream()
				.map(n -> modelMapper.map(n, MasterProviderDTO.class) )
				.collect(Collectors.toList());
	}
	
	public ProviderEntity getById(Integer id) {
		return providerRepository.findById(id).orElse(null);
	}
	
	public void updateProvider (MasterProviderDTO provider) {
		ProviderEntity prv = modelMapper.map(provider, ProviderEntity.class);
		ProviderEntity n = providerRepository.findById(prv.getId()).get();
		prv.setName(n.getName());
		prv.setLastUpdate(n.getLastUpdate());
		providerRepository.save(prv);
	}
}
