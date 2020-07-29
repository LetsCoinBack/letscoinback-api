package br.com.letscoinback.persistence.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.letscoinback.persistence.entity.MasterConfigurationEntity;

@Repository
@Transactional
public interface MasterConfigurationRepository extends JpaRepository<MasterConfigurationEntity, String> {
}