package br.com.model.config.enums;

import lombok.Getter;

public enum NotificationTypeEnum {
	BALANCE_RELEASED("SALDO_LIBERADO"),
	PURCHASE_APPROVE("COMPRA_APROVADA"),
	INDICATE_REGISTER("INDICADO_REGISTRADO");
	
	@Getter
	private String description;
	
	NotificationTypeEnum(String des) {
		this.description = des;
	}
}