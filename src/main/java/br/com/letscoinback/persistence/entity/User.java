package br.com.model.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(name = "user", schema="public")
public class User {
	
	@Id
	@SequenceGenerator(name="user_id_seq",sequenceName="user_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="user_id_seq")
	private Integer id;
	private String name;
	private Boolean available;
	private String email;
	private String cel;
	private String password;
	private String wallet;
	private String photo;
	private String authority;
	private Integer indicate;

}
