package br.com.letscoinback.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.letscoinback.config.BlockChainConfig;
import br.com.letscoinback.dto.BlockChainDrawBodyDTO;
import br.com.letscoinback.dto.BlockChainResponseBodyDTO;
import br.com.letscoinback.dto.UserWalletDTO;
import br.com.letscoinback.exception.BusinessRunTimeException;
import br.com.letscoinback.interfaces.projection.WalletStatusTotalProjection;
import br.com.letscoinback.persistence.entity.User;
import br.com.letscoinback.persistence.entity.Wallet;
import br.com.letscoinback.persistence.repository.WalletRepository;
import br.com.letscoinback.util.Translator;

@Service
public class WalletService {
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	BlockChainConfig blockChainConfig;
	
	@Autowired
	ConfigurationService configurationService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	WalletRepository walletRepository;
	
	@Value("${blockchain.api.url}")
	String blockChainUrl;
	
	public List<UserWalletDTO> getWalletByUser (Integer id) {
		return walletRepository.findTop30ByUserIdOrderByDate(id)
				.stream()
				.map(w -> modelMapper.map(w, UserWalletDTO.class))
				.collect(Collectors.toList());
	}
	
	public void transfer(Integer userId, Float drawValue) {
		List<WalletStatusTotalProjection> userBalance = walletRepository.getUserBalance(userId, "Confirmado");
		Float minDraw = Float.valueOf(configurationService.getById("MIN_DRAW_MONEY").getValue());
		Float balance = calcBalance(userBalance);
		if (drawValue < minDraw && drawValue < balance) {
			throw new BusinessRunTimeException("wallet.draw.balance.fail");
		}
		doDraw(userId, drawValue);		
	}
	
	private void doDraw (Integer userId, Float drawValue) {
		User user = userService.getUser(userId);
		try {
			String url = blockChainUrl + blockChainConfig.getGuid() + "/payment";
			BlockChainDrawBodyDTO body = createRequestBody(drawValue, user);
			HttpEntity<BlockChainDrawBodyDTO> request = new HttpEntity<>(body, new HttpHeaders());
		    BlockChainResponseBodyDTO response = restTemplate.exchange(url, HttpMethod.POST, request, BlockChainResponseBodyDTO.class).getBody();
		    validateResponse(response);		    
		    walletRepository.save(createWallet(userId, drawValue));
		} catch (Exception e) {
			throw new RuntimeException(Translator.toLocale("wallet.draw.balance.fail")); 
		}
	}

	private  Wallet createWallet(Integer userId, Float value) {
		Wallet wallet = new Wallet();
		wallet.setDate(LocalDateTime.now());
		wallet.setDescription("Saque - Transferência LQX");
		wallet.setMovimentationType("Saída");
		wallet.setStatus("Confirmado");
		wallet.setTransactionType("Saque");
		wallet.setUserId(userId);
		wallet.setValue(value);
		return wallet;
	}
	
	private void validateResponse(BlockChainResponseBodyDTO response) {
		if (!response.getSuccess()) {
			throw new BusinessRunTimeException("wallet.draw.balance.fail");
		}
	}

	private BlockChainDrawBodyDTO createRequestBody(Float drawValue, User user) {
		BlockChainDrawBodyDTO body = new BlockChainDrawBodyDTO();
		body.setAmount(drawValue);
		body.setFrom(blockChainConfig.getAddress());
		body.setPassword(blockChainConfig.getPassword());
		body.setTo(user.getWallet());
		return body;
	}
	
	private Float calcBalance (List<WalletStatusTotalProjection> userBalance) {
		Float balance = 0F;
		for (WalletStatusTotalProjection b : userBalance) {
			if ("Entrada".equals(b.getMovimentationType())) {
				balance += b.getVlrTotal();
			}
			if ("Saída".equals(b.getMovimentationType())) {
				balance -= b.getVlrTotal();
			}
		}
		return balance;
	}
	
	public List<WalletStatusTotalProjection> getTotalFromUser (Integer id) {
		return walletRepository.getTotalStatus(Optional.of(id), null, null, null);
	}
	
	public List<WalletStatusTotalProjection> getTotalDashboard (
			Boolean history,
			Optional<Integer> id, 
			Optional<String> status, 
			Optional<String> transactionType, 
			Optional<String> movimentationType) {
		if (history) {
			return walletRepository.getTotalHistoryDashboard(id, status, transactionType, movimentationType);	
		} else {
			return walletRepository.getTotalStatus(id, status, transactionType, movimentationType);	
		}
	}
	
	public Wallet saveWallet (Wallet wallet) {
		return walletRepository.save(wallet);
	}
}
