package br.com.letscoinback.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartnerUserDTO implements Serializable{
	private static final long serialVersionUID = 3509340315625880301L;
	private Integer id;
	private String name;
	private String photo;
	private ProviderDTO provider;
}
