package com.tosh.kaleido.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity.IgnoredRequestConfigurer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class TalentsSecurityConfigurer extends WebSecurityConfigurerAdapter {

	@Autowired
	private TokenAuthenticationProvider tokenAuthenticationProvider;

	@Autowired
	private TokenAuthenticationFilter tokenAuthenticationFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(tokenAuthenticationProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/**
		 * 验证所有请求，要求必须登录
		 * 使用注解授权 @Secured({"ROLE_ADMIN"}) @PreAuthorize("hasAuthority('admin')")
		 */
		http.addFilterAfter(tokenAuthenticationFilter, BasicAuthenticationFilter.class).authorizeRequests().anyRequest()
				.authenticated();
		// 关闭CRSF，不然POST/PUT/DELETE请求会被拒绝
		http.csrf().disable();
		http.formLogin().disable();
		http.logout().disable();
		http.requestCache().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		/**
		 * 放行有两种方式 1. 在此处配置Pattern 2. 在方法上添加注解@PreAuthorize("permitAll")
		 */
		IgnoredRequestConfigurer irc = web.ignoring();
		irc.mvcMatchers("/**");
	}
}
