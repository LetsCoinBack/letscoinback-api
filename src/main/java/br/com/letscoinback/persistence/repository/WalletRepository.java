package br.com.letscoinback.persistence.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.letscoinback.persistence.entity.Wallet;

@Repository
@Transactional
public interface WalletRepository extends JpaRepository<Wallet, Integer> {
	List<WalletRepository> findTop30ByUserId(Integer userId, Sort sort);
}