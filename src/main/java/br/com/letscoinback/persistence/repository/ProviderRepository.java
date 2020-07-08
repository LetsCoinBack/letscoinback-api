package br.com.letscoinback.persistence.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.letscoinback.persistence.entity.ProviderEntity;

@Repository
@Transactional
public interface ProviderRepository extends JpaRepository<ProviderEntity, Integer> {
}