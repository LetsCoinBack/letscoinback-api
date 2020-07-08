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
@Table(name = "pre_sale", schema="public")
public class PreSale {
	
	@Id
	@SequenceGenerator(name="pre_sale_id_seq",sequenceName="pre_sale_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pre_sale_id_seq")
	@Column(columnDefinition = "SERIAL")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	

	@ManyToOne
	@JoinColumn(name = "partner_id")
	private Partner partner;

}
