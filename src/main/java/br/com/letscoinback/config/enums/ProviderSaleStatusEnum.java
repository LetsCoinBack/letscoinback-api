package br.com.letscoinback.config.enums;

import lombok.Getter;

public enum ProviderSaleStatusEnum {
	PENDENTE("Pendente", 0),
	CONFIRMADO("Confirmado", 1),
	REJEITADO("Rejeitado", 2);
	@Getter
	private String description;
	@Getter
	private Integer id;
	
	ProviderSaleStatusEnum(String des, Integer id) {
		this.description = des;
		this.id = id;
	}
	
	public static String getById (Integer id) {
		ProviderSaleStatusEnum.values();
        for (ProviderSaleStatusEnum b : ProviderSaleStatusEnum.values()) {
            if (b.getId().equals(id)) {
            	return b.getDescription();
            }
        }
        return null;
	}
}