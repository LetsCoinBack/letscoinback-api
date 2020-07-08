package br.com.letscoinback.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "sale", schema = "public")
public class Sale {
	
	@Id
	@SequenceGenerator(name="sale_id_seq",sequenceName="sale_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="sale_id_seq")
	@Column(columnDefinition = "SERIAL")
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "pre_sale_id")
	private PreSale preSale;
	
	@ManyToOne
	@JoinColumn(name = "wallet_id")
	private Wallet wallet;
	
	
	@Column(columnDefinition = "NUMERIC(9,2)")
	private Float saleValue;
	@Column(columnDefinition = "NUMERIC(9,2)")
	private Float cashbackValue;
	@Column(name = "transection_provider", unique=true)
	private String transactionProvider;
}
