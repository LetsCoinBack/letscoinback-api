package br.com.letscoinback.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationDTO {
	private Integer id;
	private Integer userId;
	private String notificationType;
	private LocalDateTime date;
	private String title;
	private String body;
	private Boolean read;
}
