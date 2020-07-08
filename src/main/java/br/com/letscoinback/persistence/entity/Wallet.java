package br.com.letscoinback.persistence.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "wallet", schema = "public")
public class Wallet {

	@Id
	@SequenceGenerator(name = "wallet_id_seq", sequenceName = "wallet_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wallet_id_seq")
	private Integer id;
	@Column(columnDefinition = "TIMESTAMP")
	private LocalDateTime date;
	private String description;
	private String transactionType;
	
	private String movimentationType;
	
	private Integer userId;
	@Column(columnDefinition = "NUMERIC(9,2)")
	private Float value;
	private String status;

}
