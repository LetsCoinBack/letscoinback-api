package br.com.letscoinback.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProviderDTO implements Serializable{

	private static final long serialVersionUID = 8806283950695546740L;
	private Long id;
	private Long name;
	private String paramSend;	

}
