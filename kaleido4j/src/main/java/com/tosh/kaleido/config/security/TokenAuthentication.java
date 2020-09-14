package com.tosh.kaleido.config.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class TokenAuthentication extends AbstractAuthenticationToken {

	private static final long serialVersionUID = 1L;
	private UserDetails user;
	private String credentials;

	public TokenAuthentication(String token) {
		super(null);
		this.credentials = token;
	}

	public TokenAuthentication(UserDetails user, String token, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.user = user;
		this.credentials = token;
		setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		return credentials;
	}

	@Override
	public Object getPrincipal() {
		return user;
	}

}
