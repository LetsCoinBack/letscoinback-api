package br.com.letscoinback.persistence.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.letscoinback.persistence.entity.User;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByEmail(String email);
	List<User> findByIndicate (Integer id);
}