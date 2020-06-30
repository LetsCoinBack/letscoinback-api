package br.com.model.service;

import java.util.Map;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import br.com.model.dto.LoginDTO;
import br.com.model.dto.UserDTO;
import br.com.model.exception.BusinessRunTimeException;
import br.com.model.persistence.repository.UserRepository;
import br.com.model.util.Translator;

@Service
public class LoginService {

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Value("${apimodel.auth.url}")
	String authUrl;
	
	@Value("${apimodel.auth.authorization}")
	String authAuthorization;
	
	@Value("${apimodel.auth.grantType}")
	String authGrantType;
	

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);
	
	
	public UserDTO getUser(Integer id) {
		try {
			return modelMapper.map(userRepository.findById(id).get(), UserDTO.class);	
		} catch (Exception e) {
			String msg = "login.user.notFound";
			LOGGER.error(Translator.toLocale(msg), e);
			throw new BusinessRunTimeException(msg);
		}		
	}
	
	public Map<String, Object> login (LoginDTO user) {
		try {
			String url = authUrl + "/oauth/token";
			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(getBody(user), getHeaders());
		    return restTemplate.exchange(url, HttpMethod.POST, request, Map.class).getBody();	       
		} catch (Exception e) {
			throw new RuntimeException(Translator.toLocale("login.failed")); 
		}
	}
	
	private MultiValueMap<String, String> getBody (LoginDTO user) {
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("username", user.getEmail());
		body.add("password", user.getPassword());
		body.add("grant_type", authGrantType);
		return body;
	}
	
	private HttpHeaders getHeaders () {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.add("Authorization", "Basic " + authAuthorization);
		return headers;
	}
}
