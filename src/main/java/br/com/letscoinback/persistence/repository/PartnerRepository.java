package br.com.model.persistence.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.model.persistence.entity.Partner;

@Repository
@Transactional
public interface PartnerRepository extends JpaRepository<Partner, Integer> {
}