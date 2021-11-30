package com.example.iguantask.persistence.role.model;

import com.example.iguantask.persistence.user.model.UserRole;
import org.hibernate.annotations.SQLInsert;

import javax.persistence.*;

@Entity
@Table(name = "roles",schema = "postgres")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private UserRole name;

	public Role() {

	}

	public Role(UserRole name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public UserRole getName() {
		return name;
	}

	public void setName(UserRole name) {
		this.name = name;
	}
}