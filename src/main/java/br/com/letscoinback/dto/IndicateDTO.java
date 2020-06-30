package br.com.model.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IndicateDTO implements Serializable{

	private static final long serialVersionUID = 2150290030048489872L;
	private Integer id;
	private String name;
	private String email;
	private String cel;

}