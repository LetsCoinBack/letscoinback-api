package br.com.model.persistence.entity;

import java.sql.Timestamp;

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
@Table(name = "notification", schema="public")
public class NotificationEntity {
	
	@Id
	@SequenceGenerator(name="notification_id_seq",sequenceName="notification_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="notification_id_seq")
	private Integer id;
	
	@Column(name="user_id")
	private Integer userId;
	
	@Column(name="notification_type")
	private String notificationType;
	
	private Timestamp date;
	
	private String title;
	private String body;
	private Boolean read;
}
