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
@Table(name = "notification", schema="public")
public class NotificationEntity {
	
	@Id
	@SequenceGenerator(name="notification_id_seq",sequenceName="notification_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="notification_id_seq")
	private Integer id;
	private Integer userId;
	private String notificationType;
	@Column(columnDefinition = "TIMESTAMP")
	private LocalDateTime date;
	private String title;
	private String body;
	private Boolean read;
}
