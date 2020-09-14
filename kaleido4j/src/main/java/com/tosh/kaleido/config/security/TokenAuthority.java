package com.tosh.kaleido.config.security;

import org.springframework.security.core.GrantedAuthority;

public class TokenAuthority implements GrantedAuthority {
	private static final long serialVersionUID = 1L;

	private String authority;

	public TokenAuthority(String auth) {
		super();
		this.authority = auth;
	}

	@Override
	public String getAuthority() {
		return authority;
	}

}
