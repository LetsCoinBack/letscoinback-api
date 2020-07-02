package br.com.letscoinback.persistence.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.letscoinback.dto.WalletStatusTotalDTO;
import br.com.letscoinback.persistence.entity.Wallet;

@Repository
@Transactional
public interface WalletRepository extends JpaRepository<Wallet, Integer> {
	List<WalletRepository> findTop30ByUserId(Integer userId, Sort sort);
	@Query(nativeQuery = true)
	WalletStatusTotalDTO getTotalStatusByUser (@Param("userId") Integer id);
}