package com.tosh.kaleido.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class SpringClient {
	private static RestTemplate restTemplate = new RestTemplate();

	public static ResponseEntity<String> get(String url, Object... params) {
		return get(url, null, params);
	}

	public static ResponseEntity<String> get(String url, HttpHeaders headers, Object... params) {
		return request(url, HttpMethod.GET, null, headers, params);
	}

	public static ResponseEntity<String> post(String url, Object... params) {
		return post(url, null, null, params);
	}

	public static ResponseEntity<String> post(String url, String body, Object... params) {
		return post(url, null, body, params);
	}

	public static ResponseEntity<String> post(String url, HttpHeaders headers, Object... params) {
		return post(url, headers, null, params);
	}

	public static ResponseEntity<String> post(String url, HttpHeaders headers, String body, Object... params) {
		return request(url, HttpMethod.POST, body, headers, params);
	}

	public static ResponseEntity<String> put(String url, Object... params) {
		return put(url, null, null, params);
	}

	public static ResponseEntity<String> put(String url, String body, Object... params) {
		return put(url, null, body, params);
	}

	public static ResponseEntity<String> put(String url, HttpHeaders headers, Object... params) {
		return put(url, headers, null, params);
	}

	public static ResponseEntity<String> put(String url, HttpHeaders headers, String body, Object... params) {
		return request(url, HttpMethod.PUT, body, headers, params);
	}

	public static ResponseEntity<String> delete(String url, Object... params) {
		return delete(url, null, params);
	}

	public static ResponseEntity<String> delete(String url, HttpHeaders headers, Object... params) {
		return request(url, HttpMethod.DELETE, null, headers, params);
	}

	public static ResponseEntity<String> request(String url, HttpMethod method, String body, HttpHeaders headers,
			Object... params) {
		return request(url, method, body, headers, String.class, params);
	}

	public static <T> ResponseEntity<T> request(String url, HttpMethod method, String body, HttpHeaders headers,
			Class<T> cls, Object... params) {
		HttpEntity<String> reqEntity = new HttpEntity<String>(body, headers);
		return restTemplate.exchange(url, method, reqEntity, cls, params);
	}
}
