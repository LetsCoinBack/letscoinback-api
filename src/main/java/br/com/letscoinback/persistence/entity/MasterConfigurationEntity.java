package br.com.letscoinback.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(name = "master_configuration", schema="public")
public class MasterConfigurationEntity {
	@Id
	private String key;
	private String description;
	private String value;

}
