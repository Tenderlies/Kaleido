package com.tosh.kaleido.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class CORSInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		// host in blacklist
		if ("blacklist".equals(request.getRemoteAddr())) {
			response.setStatus(HttpStatus.OK.value());
			return false;
		}
		// simple
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Expose-Headers", "MyResponseHeader");
		// preflight
		if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
			response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS");
			response.setHeader("Access-Control-Allow-Headers", "YouRequestHeaders");
			response.setHeader("Access-Control-Max-Age", "86400"); // seconds
			// request in blacklist
			if ("blackMethod".equals(request.getHeader("Access-Control-Request-Method"))
					|| "blackHeaders".equals(request.getHeader("Access-Control-Request-Headers"))) {
				response.setStatus(HttpStatus.OK.value());
				return false;
			}
		}
		return true;
	}
}