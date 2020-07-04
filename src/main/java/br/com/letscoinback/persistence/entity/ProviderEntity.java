package br.com.letscoinback.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "provider", schema="public")
public class ProviderEntity {
	
	@Id
	private Integer id;
	private String name;
	private String token;
	private String user;
	private String password;
	private String publisher;
	private String paramSend;
	
}
