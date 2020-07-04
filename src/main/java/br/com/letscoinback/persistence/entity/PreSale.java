package br.com.letscoinback.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "provider", schema="public")
public class PreSale {
	
	@Id
	@SequenceGenerator(name="partner_id_seq",sequenceName="partner_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="partner_id_seq")
	@Column(columnDefinition = "SERIAL")
	private Long id;
	
	@OneToMany
	@JoinColumn(name = "user_id")
	private User user;
	

	@OneToMany
	@JoinColumn(name = "partner_id")
	private Partner partner;

}
