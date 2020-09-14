package com.tosh.kaleido.config.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LogInterceptor extends HandlerInterceptorAdapter {

	private ThreadLocal<Long> startTime = new ThreadLocal<Long>();

	private String reqSep = System.lineSeparator() + "==> ";
	private String respSep = System.lineSeparator() + "<== ";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		startTime.set(System.currentTimeMillis());
		StringBuilder info = new StringBuilder();
		info.append(reqSep).append(request.getMethod()).append(" ").append(request.getRequestURI());
		info.append(reqSep).append("Params: ").append(JSON.toJSONString(request.getParameterMap()));
		if (Long.compare(request.getContentLengthLong(), 0L) > 0) {
			info.append(reqSep).append("Content-Type: ").append(request.getContentType()).append(" | Content-Length: ")
					.append(request.getContentLength());
		}
		log.info(info.toString());
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		long elasped = System.currentTimeMillis() - startTime.get();
		startTime.remove();
		StringBuilder info = new StringBuilder();
		info.append(respSep).append(request.getMethod()).append(" ").append(request.getRequestURI());
		info.append(respSep).append("Status: ").append(response.getStatus()).append(" | Elasped: ").append(elasped)
				.append(" millis");
		if (response.getContentType() != null) {
			info.append(respSep).append("Content-Type: ").append(response.getContentType());
		}
		log.info(info.toString());
		super.postHandle(request, response, handler, modelAndView);
	}
}
