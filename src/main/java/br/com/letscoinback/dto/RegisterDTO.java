package br.com.model.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO implements Serializable{

	private static final long serialVersionUID = -7299745198805395116L;
	private String name;
	private String email;
	private String password;
	private Integer indicate;
}
