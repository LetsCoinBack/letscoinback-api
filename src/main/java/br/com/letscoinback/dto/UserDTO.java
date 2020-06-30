package br.com.model.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO implements Serializable{

	private static final long serialVersionUID = 2150290030048489872L;
	private Integer id;
	private String name;
	private Boolean available;
	private String email;
	private String cel;
	private String wallet;
	private String authority;
	private Integer indicate;
	private String photo;
}