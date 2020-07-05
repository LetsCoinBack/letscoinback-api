package br.com.letscoinback.config.enums;

import lombok.Getter;

public enum NotificationTypeEnum {
	PURCHASE_CHANGE("COMPRA_ALTERADA"),
	PURCHASE_APPROVE("COMPRA_APROVADA"),
	INDICATE_REGISTER("INDICADO_REGISTRADO");
	
	@Getter
	private String description;
	
	NotificationTypeEnum(String des) {
		this.description = des;
	}
}