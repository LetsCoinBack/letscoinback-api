package br.com.letscoinback.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.letscoinback.dto.UserWalletDTO;
import br.com.letscoinback.persistence.repository.WalletRepository;

@Service
public class WalletService {
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	WalletRepository walletRepository;
	
	public List<UserWalletDTO> getWalletByUser (Integer id) {
		return walletRepository.findTop30ByUserId(id, Sort.by("date").ascending())
				.stream()
				.map(w -> modelMapper.map(w, UserWalletDTO.class))
				.collect(Collectors.toList());
	}
	
}
