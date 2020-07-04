package br.com.letscoinback.persistence.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.letscoinback.interfaces.projection.WalletStatusTotalProjection;
import br.com.letscoinback.persistence.entity.Wallet;

@Repository
@Transactional
public interface WalletRepository extends JpaRepository<Wallet, Integer> {
	List<Wallet> findTop30ByUserIdOrderByDate(Integer userId);
	List<WalletStatusTotalProjection> getTotalStatusByUser (@Param("userId") Integer id);
}