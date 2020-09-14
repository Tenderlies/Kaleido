package com.tosh.kaleido.config.interceptor;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.common.util.concurrent.RateLimiter;

public class RateLimiterInterceptor extends HandlerInterceptorAdapter {
	public enum LimitType {
		DROP, WAIT
	}

	private RateLimiter limiter;
	private LimitType limitType;

	public RateLimiterInterceptor() {
		this.limiter = RateLimiter.create(100);
		this.limitType = LimitType.DROP;
	}

	public RateLimiterInterceptor(double tps, LimitType limitType) {
		this.limiter = RateLimiter.create(tps);
		this.limitType = limitType;
	}

	public RateLimiterInterceptor(double permitsPerSecond, long warmupPeriodSecond, LimitType limitType) {
		this.limiter = RateLimiter.create(permitsPerSecond, warmupPeriodSecond, TimeUnit.MILLISECONDS);
		this.limitType = limitType;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (limitType.equals(LimitType.DROP)) {
			if (limiter.tryAcquire()) {
				return super.preHandle(request, response, handler);
			}
		} else {
			limiter.acquire();
			return super.preHandle(request, response, handler);
		}
		throw new IllegalCallerException();
	}
}
