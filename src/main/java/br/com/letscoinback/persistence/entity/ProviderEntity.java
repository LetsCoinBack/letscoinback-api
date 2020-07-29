package br.com.letscoinback.persistence.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
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
	@Column(name="username")
	private String user;
	private String password;
	private String publisher;
	private String paramSend;
	@Column(columnDefinition = "TIMESTAMP")
	private LocalDateTime lastUpdate;
	private String url;
}
