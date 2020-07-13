package br.com.letscoinback.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlockChainResponseBodyDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String to;
	private String from;
	private String password;
	private String txid;
	private Boolean success;
}
