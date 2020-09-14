package com.tosh.kaleido.config;

import org.springframework.stereotype.Component;

@Component
public class Operator {
	private ThreadLocal<String> user = new ThreadLocal<String>();
	private ThreadLocal<String> time = new ThreadLocal<String>();
	private ThreadLocal<String> ip = new ThreadLocal<String>();
	private ThreadLocal<Boolean> publish = new ThreadLocal<Boolean>();

	public void setOperator(String user, String dateTime) {
		this.user.set(user);
		this.time.set(dateTime);
	}

	public String getUser() {
		return this.user.get();
	}

	public void setIP(String ip) {
		this.ip.set(ip);
	}

	public String getIP() {
		return this.ip.get();
	}

	public String getOperateTime() {
		return this.time.get();
	}

	public void setPublish(boolean isPublish) {
		this.publish.set(isPublish);
	}

	public boolean isPublish() {
		return this.publish.get() == null ? false : this.publish.get();
	}

	public void clear() {
		this.user.remove();
		this.time.remove();
		this.ip.remove();
		this.publish.remove();
	}
}
