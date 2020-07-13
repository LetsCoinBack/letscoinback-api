package br.com.letscoinback.persistence.repository;

import java.util.List;
import java.util.Optional;

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
	List<WalletStatusTotalProjection> getUserBalance(@Param("userId") Integer userId, @Param("status") String status);
	List<WalletStatusTotalProjection> getTotalStatus (
			@Param("userId") Optional<Integer> id,
			@Param("status") Optional<String> status,
			@Param("transactionType") Optional<String> transactionType,
			@Param("movimentationType") Optional<String> movimentationType);
	List<WalletStatusTotalProjection> getTotalHistoryDashboard (
			@Param("userId") Optional<Integer> id,
			@Param("status") Optional<String> status,
			@Param("transactionType") Optional<String> transactionType,
			@Param("movimentationType") Optional<String> movimentationType);
}