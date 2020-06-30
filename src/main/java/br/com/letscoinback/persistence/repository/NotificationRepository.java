package br.com.model.persistence.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.model.persistence.entity.NotificationEntity;

@Repository
@Transactional
public interface NotificationRepository extends JpaRepository<NotificationEntity, Integer> {
	List<NotificationEntity> findByUserIdAndRead(Integer id, Boolean read);
	List<NotificationEntity> findTop5ByUserIdAndRead(Integer id, Boolean read, Sort sort);
}
