package com.example.iguantask.rest.model;

import java.util.List;

public class UserResponse {
	private String token;
	private String type = "Bearer";
	private Long id;
	private String username;
	private String firstname;
	private String lastname;
	private String phone;
	private String email;
	private String gender;
	private List<String> roles;

	public UserResponse() {
	}

	public UserResponse(String token, Long id, String username, String firstname,
						String lastname, String phone, String email, String gender,
						List<String> roles) {
		this.token = token;
		this.id = id;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.phone = phone;
		this.email = email;
		this.gender = gender;
		this.roles = roles;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}
}
