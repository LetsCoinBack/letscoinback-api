package br.com.model.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.model.config.enums.NotificationTypeEnum;
import br.com.model.dto.ChangePasswordDTO;
import br.com.model.dto.ConfigurationDTO;
import br.com.model.dto.IndicateDTO;
import br.com.model.dto.RegisterDTO;
import br.com.model.dto.UserDTO;
import br.com.model.exception.BusinessRunTimeException;
import br.com.model.persistence.entity.User;
import br.com.model.persistence.repository.ConfigurationRepository;
import br.com.model.persistence.repository.UserRepository;
import br.com.model.util.Translator;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	ConfigurationRepository configurationRepository;
	
	@Autowired
	NotificationService notificationService;
	
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
		if (pass.getPassword() == null || pass.getOldPassword() == null) {
			throw new BusinessRunTimeException("user.password.change.failed");
		}
		if (user.getPassword().equals(passwordEncoder.encode(pass.getOldPassword()))) {
			throw new BusinessRunTimeException("user.password.incorrect");
		}
		user.setPassword(passwordEncoder.encode(pass.getPassword()));
		userRepository.save(user);
	}
	
	public ConfigurationDTO getDefaultCashback () {
		return modelMapper.map(configurationRepository.findById("DEFAULT_CASHBACK_USER").orElse(null)
				, ConfigurationDTO.class);
	}
	
	public User getUser (Integer id) {
		return userRepository.findById(id).orElse(null);
	}
	
	public List<UserDTO> getAll () {
		return userRepository.findAll().stream().map(u -> modelMapper.map(u, UserDTO.class)).collect(Collectors.toList());
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
	
	public void registerUser (RegisterDTO user) {
		try {
			User usr = modelMapper.map(user, User.class);
			usr.setAuthority("User");
			usr.setPassword(passwordEncoder.encode(user.getPassword()));
			usr.setAvailable(true);
			userRepository.save(usr);
			if (usr.getIndicate() != null) {
				String body = "O usuário " + usr.getName() + " que você indicou se cadastrou na ferramenta. Assim que ele efetivar seu cadastro seu bônus será creditado!";
				notificationService.sendNotification(usr.getIndicate(), "Usuário entrou na aplicação!", body, NotificationTypeEnum.INDICATE_REGISTER);
			}
		} catch (Exception e ) {
			String msg = "user.register.failed";
			LOGGER.error(Translator.toLocale(msg), e);
			throw new BusinessRunTimeException(msg);
		}
	}
}
