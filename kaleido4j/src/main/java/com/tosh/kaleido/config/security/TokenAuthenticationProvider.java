package com.tosh.kaleido.config.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.tosh.kaleido.config.Operator;

@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {
	@Autowired
	private Operator operator;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if (!supports(authentication.getClass())) {
			return null;
		}
		if (authentication.isAuthenticated()) {
			return null;
		}
		String token = (String) authentication.getCredentials();
		if (Strings.isBlank(token)) {
			return null;
		}
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new TokenAuthority("admin"));
		// token自动延期
		// 配置操作者
		operator.setOperator("user", "time");
		UserDetails user = User.builder().username("").password("").roles("ADMIN").build();
		// 权限鉴权与角色鉴权共存
		authorities.addAll(user.getAuthorities());
		Authentication auth = new TokenAuthentication(user, token, authorities);
		return auth;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return TokenAuthentication.class.isAssignableFrom(authentication);
	}
}
