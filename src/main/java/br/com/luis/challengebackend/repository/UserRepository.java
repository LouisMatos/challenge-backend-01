package br.com.luis.challengebackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.luis.challengebackend.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

}
