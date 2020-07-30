package br.com.letscoinback.persistence.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.letscoinback.persistence.entity.PartnerXSegment;

@Repository
@Transactional
public interface PartnerXSegmentRepository extends JpaRepository<PartnerXSegment, Integer> {
	List<PartnerXSegment> findByPartnerId(Integer id);
	Optional<PartnerXSegment> findByPartnerIdAndPartnerSegmentId(Integer partnerId, Integer partnerSegmentId);
}