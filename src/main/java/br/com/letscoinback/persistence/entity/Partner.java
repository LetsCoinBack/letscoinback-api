package br.com.letscoinback.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(name = "partner", schema="public")
public class Partner {
	
	@Id
	@SequenceGenerator(name="partner_id_seq",sequenceName="partner_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="partner_id_seq")
	private Integer id;
	private String name;
	private String photo;
	private String redirect;
	@Column(columnDefinition = "NUMERIC(9,2)")
	private Float cashback;
	@Column(columnDefinition = "NUMERIC(9,2)")
	private Float userCashback;
	private Integer position;
	private Boolean available;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private ProviderEntity provider;
	
}
