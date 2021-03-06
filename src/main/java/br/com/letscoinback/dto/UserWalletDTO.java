package br.com.letscoinback.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserWalletDTO implements Serializable{
	
	private static final long serialVersionUID = -7242554092745115841L;
	private Integer id;
	private LocalDateTime date;
	private String description;
	private String transactionType;
	private String movimentationType;
	private Integer userId;
	private Float value;
	private String status;

}
