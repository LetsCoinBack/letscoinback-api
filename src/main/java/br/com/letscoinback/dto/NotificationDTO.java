package br.com.model.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationDTO {
	private Integer id;
	private Integer userId;
	private String notificationType;
	private Date date;
	private String title;
	private String body;
	private Boolean read;
}
