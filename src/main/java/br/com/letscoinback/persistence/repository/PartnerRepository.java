package br.com.letscoinback.persistence.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.letscoinback.persistence.entity.Partner;

@Repository
@Transactional
public interface PartnerRepository extends JpaRepository<Partner, Integer> {
	List<Partner> findByAvailable(Boolean available, Sort sort);
	List<Partner> findByRedirectStartsWith (String url);
}