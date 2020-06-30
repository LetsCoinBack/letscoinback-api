package br.com.model.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(name = "configuration", schema="public")
public class ConfigurationEntity {
	@Id
	private String key;
	private String description;
	private String value;

}
