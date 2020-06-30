package br.com.model.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.model.config.enums.NotificationTypeEnum;
import br.com.model.dto.NotificationDTO;
import br.com.model.exception.BusinessRunTimeException;
import br.com.model.persistence.entity.NotificationEntity;
import br.com.model.persistence.repository.NotificationRepository;

@Service
public class NotificationService {
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	NotificationRepository notificationRepository;
	
	public void sendNotification (Integer userId, String title, String body, NotificationTypeEnum type) {
		if (userId == null || title == null || body == null || type == null) {
			throw new BusinessRunTimeException("paramter.fail");
		}
		NotificationEntity not = new NotificationEntity();
		not.setBody(body);
		not.setTitle(title);
		not.setNotificationType(type.getDescription());
		not.setUserId(userId);
		not.setRead(false);
		notificationRepository.save(not);
	}
	
	public List<NotificationDTO> getUserNotification(Integer id) {
		List<NotificationDTO> notRead = notificationRepository.findByUserIdAndRead(id, true).stream()
				.map(n -> modelMapper.map(n, NotificationDTO.class))
				.collect(Collectors.toList());
		
		List<NotificationDTO> read = notificationRepository.findTop5ByUserIdAndRead(id, false, Sort.by("date").descending()).stream()
				.map(n -> modelMapper.map(n, NotificationDTO.class))
				.collect(Collectors.toList());
		
		return Stream.concat(read.stream(), notRead.stream()).collect(Collectors.toList());
	}
	
	public void readNotification (Integer id) {
		Optional<NotificationEntity> notification = notificationRepository.findById(id);
		notification.ifPresent(n -> {
			if (!n.getRead()) {
				n.setRead(true);
				notificationRepository.save(n);	
			}
		});		
	}
}
