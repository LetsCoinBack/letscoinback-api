package br.com.letscoinback.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartnerSegmentDTO implements Serializable{

	private static final long serialVersionUID = 3003658318203300860L;
	private Integer id;
	private String description;
}
