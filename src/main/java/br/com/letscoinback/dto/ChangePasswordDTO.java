package br.com.model.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordDTO implements Serializable{
	private static final long serialVersionUID = 1886332441122605023L;
	String password;
	String oldPassword;
}
