package com.example.iguantask.persistence.role;

import com.example.iguantask.persistence.role.model.Role;
import com.example.iguantask.persistence.user.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(UserRole name);
}
