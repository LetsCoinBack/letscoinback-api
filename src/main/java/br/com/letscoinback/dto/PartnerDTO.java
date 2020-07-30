package br.com.letscoinback.dto;

import java.util.List;

import lombok.Data;

@Data
public class PartnerDTO {
	private Integer id;
	private String name;
	private String photo;
	private String redirect;
	private Integer userCashback;
	private Integer position;
	private Boolean available;
	private List<String> segments;
}
