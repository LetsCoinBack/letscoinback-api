package br.com.letscoinback.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalletStatusTotalDTO implements Serializable {
	private static final long serialVersionUID = 6108463529277242887L;
	private String status;
	private Float vlrTotal;
}
