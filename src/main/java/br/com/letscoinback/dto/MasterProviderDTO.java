package br.com.letscoinback.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MasterProviderDTO implements Serializable{

	private static final long serialVersionUID = 8806283950695546740L;
	private Integer id;
	private String name;
	private String user;
	private String password;
	private String publisher;
	private String paramSend;
	private String url;

}
