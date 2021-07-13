package com.dcat.ReCo.models;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class AuthUser extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = 1L;
	
	private com.dcat.ReCo.models.User user;

	public AuthUser(com.dcat.ReCo.models.User user, Collection<? extends GrantedAuthority> authorities) {
		super(user.getUsername(), user.getPassword(), authorities);
		this.user = user;
	}

	public com.dcat.ReCo.models.User getUser() {
		return user;
	}

	public void setUser(com.dcat.ReCo.models.User user) {
		this.user = user;
	}

}
