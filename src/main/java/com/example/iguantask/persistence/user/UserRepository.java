package com.example.iguantask.persistence.user;

import com.example.iguantask.persistence.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

	Optional<User> findById(Long id);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	List<User> findAll();
}
