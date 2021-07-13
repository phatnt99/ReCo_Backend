package com.dcat.ReCo.dtos;

import javax.validation.constraints.NotNull;

import com.dcat.ReCo.annotations.UniqueColumn;

public class RegisterUserDTO {
	@NotNull
	@UniqueColumn(column = "username", message = "username đã tồn tại!")
	private String username;

	@NotNull
	@UniqueColumn(column = "email", message = "email đã tồn tại!")
	private String email;

	private String password;

	private String role;

	public RegisterUserDTO(String username, String email, String password) {
		super();
		this.username = username;
		this.email    = email;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
