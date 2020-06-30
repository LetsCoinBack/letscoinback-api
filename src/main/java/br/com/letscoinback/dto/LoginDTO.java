package br.com.model.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO implements Serializable{
	private static final long serialVersionUID = -8969823155107990091L;
	private String email;
	private String password;
}
