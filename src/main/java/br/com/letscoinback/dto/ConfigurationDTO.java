package br.com.model.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfigurationDTO implements Serializable{
	
	private static final long serialVersionUID = -7999895286670436465L;
	private String key;
	private String description;
	private String value;
}
