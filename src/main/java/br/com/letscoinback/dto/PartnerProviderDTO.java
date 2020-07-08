package br.com.letscoinback.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartnerProviderDTO implements Serializable{

	private static final long serialVersionUID = 3509340315625880301L;
	private Integer id;
	private String name;
	private String photo;
	private String redirect;
	private Integer userCashback;
	private Integer position;
	private Boolean available;
	private ProviderDTO provider;
}
