package br.com.letscoinback.config.enums;

import lombok.Getter;

public enum MovimentationType {
	ENTRADA("Entrada"),
	SAIDA("Saída");
	
	@Getter
	private String description;
	
	MovimentationType(String des) {
		this.description = des;
	}
}