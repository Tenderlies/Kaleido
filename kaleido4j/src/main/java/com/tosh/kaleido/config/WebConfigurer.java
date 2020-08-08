package com.tosh.kaleido.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.tosh.kaleido.interceptor.CORSInterceptor;
import com.tosh.kaleido.interceptor.LogInterceptor;
import com.tosh.kaleido.interceptor.RateLimiterInterceptor;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {

	private int permitsPerSecond = 500;

	@Autowired
	private CORSInterceptor corsInterceptor;
	@Autowired
	private LogInterceptor logInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(corsInterceptor).addPathPatterns("/**");
		registry.addInterceptor(logInterceptor).addPathPatterns("/**");
		registry.addInterceptor(new RateLimiterInterceptor(permitsPerSecond, RateLimiterInterceptor.LimitType.DROP))
				.addPathPatterns("/**");
	}

}
