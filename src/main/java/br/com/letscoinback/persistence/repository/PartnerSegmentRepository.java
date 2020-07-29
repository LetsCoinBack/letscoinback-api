package br.com.letscoinback.persistence.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.letscoinback.persistence.entity.PartnerSegment;

@Repository
@Transactional
public interface PartnerSegmentRepository extends JpaRepository<PartnerSegment, Integer> {
	Optional<PartnerSegment> findByDescription(String description);
}