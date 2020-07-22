package br.com.letscoinback.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleSaleDTO implements Serializable {
	private static final long serialVersionUID = 7057230133154584945L;
	private Integer statusId;
	private String transaction;
	private Long associateId;
	private String totalValue;
	private String comissionValue;
}
