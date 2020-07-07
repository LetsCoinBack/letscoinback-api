package br.com.letscoinback.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.letscoinback.config.enums.MovimentationType;
import br.com.letscoinback.config.enums.NotificationTypeEnum;
import br.com.letscoinback.dto.ChangePasswordDTO;
import br.com.letscoinback.dto.ConfigurationDTO;
import br.com.letscoinback.dto.IndicateDTO;
import br.com.letscoinback.dto.RegisterDTO;
import br.com.letscoinback.dto.UserDTO;
import br.com.letscoinback.exception.BusinessRunTimeException;
import br.com.letscoinback.persistence.entity.User;
import br.com.letscoinback.persistence.entity.Wallet;
import br.com.letscoinback.persistence.repository.UserRepository;
import br.com.letscoinback.util.Translator;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	NotificationService notificationService;
	
	@Autowired
	ConfigurationService configurationService;
	
	@Autowired
	WalletService walletService;
	
	@Autowired
	ModelMapper modelMapper;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	

	public List<IndicateDTO> getIndicateUser (Integer id) {
		return userRepository.findByIndicate(id).stream()
				.map(u -> modelMapper.map(u, IndicateDTO.class))
				.collect(Collectors.toList());
	}
	
	public IndicateDTO getUserIndicate (Integer id) {
		if (id == null) {
			return null;
		}
		User user = userRepository.findById(id).orElse(null);
		IndicateDTO teste = modelMapper.map(user, IndicateDTO.class);
		return teste;
	}
	
	public void changeAuthority (Integer id, String authority) {
		User user = userRepository.findById(id).orElse(null);
		user.setAuthority(authority);
		userRepository.save(user);
	}
	
	public void changePassword (ChangePasswordDTO pass, String id) {
		User user = userRepository.findById(Integer.valueOf(id)).orElse(null);
		validadeParams(pass, user);
		user.setPassword(passwordEncoder.encode(pass.getPassword()));
		userRepository.save(user);
	}

	private void validadeParams(ChangePasswordDTO pass, User user) {
		if (pass.getPassword() == null || pass.getOldPassword() == null) {
			throw new BusinessRunTimeException("user.password.change.failed");
		}
		if (user.getPassword().equals(passwordEncoder.encode(pass.getOldPassword()))) {
			throw new BusinessRunTimeException("user.password.incorrect");
		}
	}
	
	public ConfigurationDTO getDefaultCashback () {
		return configurationService.getById("DEFAULT_CASHBACK_USER");
	}
	
	public User getUser (Integer id) {
		return userRepository.findById(id).orElse(null);
	}
	
	public List<UserDTO> getAll () {
		return userRepository.findAll(Sort.by("name").ascending()).stream().map(u -> modelMapper.map(u, UserDTO.class)).collect(Collectors.toList());
	}
	
	public void updateUser (UserDTO user, String userId) {
		if (user.getId() == null || !user.getId().equals(Integer.valueOf(userId))) {
			throw new BusinessRunTimeException("user.update.notAllowed");
		}
		User usr = modelMapper.map(user, User.class);
		User usrSave = userRepository.findById(user.getId()).get();
		usr.setAuthority(usrSave.getAuthority());
		usr.setPassword(usrSave.getPassword());
		userRepository.save(usr);
	}
	
	@Transactional
	public void registerUser (RegisterDTO user) {
		try {
			User usr = modelMapper.map(user, User.class);
			usr.setAuthority("User");
			usr.setPassword(passwordEncoder.encode(user.getPassword()));
			usr.setAvailable(true);
			userRepository.save(usr);
			Float registrationBonus = Float.valueOf(configurationService.getById("REGISTRATION_BONUS").getValue());
			if (registrationBonus != null && registrationBonus > 0) {
				Integer myId = userRepository.findByEmail(user.getEmail()).get().getId();
				saveWallet(myId, registrationBonus, "Bônus por se cadastrar na aplicação", "Cadastro");
			}			
			saveIndicate(user, usr);
		} catch (Exception e ) {
			String msg = "user.register.failed";
			LOGGER.error(Translator.toLocale(msg), e);
			throw new BusinessRunTimeException(msg);
		}
	}

	private void saveIndicate(RegisterDTO user, User usr) {
		if (usr.getIndicate() != null) {
			Float indicationBonus = Float.valueOf(configurationService.getById("INDICATION_BONUS").getValue());
			if (indicationBonus != null && indicationBonus > 0) {
				saveWallet(user.getIndicate(), indicationBonus, "Indicação - Bônus de indicação: " + user.getName(), "Indicação");
			}
			String body = "O usuário " + usr.getName() + " que você indicou se cadastrou na ferramenta. Assim que ele efetivar seu cadastro seu bônus será creditado!";
			notificationService.sendNotification(usr.getIndicate(), "Usuário entrou na aplicação!", body, NotificationTypeEnum.INDICATE_REGISTER);
		}
	}
	
	private void saveWallet (Integer user, Float value, String description, String transactionType) {
		Wallet w = new Wallet();
		w.setStatus("Pendente");
		w.setTransactionType(transactionType);
		w.setUserId(user);
		w.setMovimentationType(MovimentationType.ENTRADA.getDescription());
		w.setValue(value);
		w.setDate(LocalDateTime.now());
		w.setDescription(description);
		walletService.saveWallet(w);
	}
}
