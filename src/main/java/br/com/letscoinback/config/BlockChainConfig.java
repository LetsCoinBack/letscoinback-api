package br.com.letscoinback.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Validated
@Component
@ConfigurationProperties("wallet")
@Getter
@Setter
public class BlockChainConfig {
	
	private String guid;	
	private String password;
	private String address;
	
}
