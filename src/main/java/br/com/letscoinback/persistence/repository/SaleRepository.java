package br.com.letscoinback.persistence.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.letscoinback.persistence.entity.Sale;

@Repository
@Transactional
public interface SaleRepository extends JpaRepository<Sale, Long> {
	Optional<Sale> findByTransactionProvider(String transactionProvider);
	Sale findByPreSaleId (Long id);
}