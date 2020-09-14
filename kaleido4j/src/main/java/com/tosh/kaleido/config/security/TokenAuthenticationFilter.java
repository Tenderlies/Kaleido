package com.tosh.kaleido.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tosh.kaleido.config.Operator;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {
	@Autowired
	private Operator operator;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			String token = request.getHeader("accessToken");
			context.setAuthentication(new TokenAuthentication(token));
		}
		try {
			filterChain.doFilter(request, response);
		} finally {
			operator.clear();
		}
	}
}
