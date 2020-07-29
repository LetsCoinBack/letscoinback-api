package br.com.letscoinback.config.enums;

import lombok.Getter;

public enum AwinSaleStatusEnum {
	PENDENTE("pendente", 0),
	CONFIRMADO("aprovado", 1),
	REJEITADO("recusado", 2);
	@Getter
	private String description;
	@Getter
	private Integer id;
	
	AwinSaleStatusEnum(String des, Integer id) {
		this.description = des;
		this.id = id;
	}
	
	public static Integer getByDescription (String description) {
		AwinSaleStatusEnum.values();
        for (AwinSaleStatusEnum b : AwinSaleStatusEnum.values()) {
            if (b.getDescription().equals(description)) {
            	return b.getId();
            }
        }
        return null;
	}
}