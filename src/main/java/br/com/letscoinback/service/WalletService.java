package br.com.letscoinback.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.letscoinback.dto.UserWalletDTO;
import br.com.letscoinback.interfaces.projection.WalletStatusTotalProjection;
import br.com.letscoinback.persistence.entity.Wallet;
import br.com.letscoinback.persistence.repository.WalletRepository;

@Service
public class WalletService {
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	WalletRepository walletRepository;
	
	public List<UserWalletDTO> getWalletByUser (Integer id) {
		return walletRepository.findTop30ByUserIdOrderByDate(id)
				.stream()
				.map(w -> modelMapper.map(w, UserWalletDTO.class))
				.collect(Collectors.toList());
	}
	
	
	public List<WalletStatusTotalProjection> getTotalFromUser (Integer id) {
		return walletRepository.getTotalStatusByUser(id);
	}
	
	public void saveWallet (Wallet wallet) {
		walletRepository.save(wallet);
	}
}
