package com.tosh.kaleido.utils;

import java.net.HttpCookie;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpMethod;

public class JettyClient {
	public static HttpClient client = new HttpClient();

	public static ContentResponse get(String url) {
		return get(url, null, null);
	}

	public static ContentResponse get(String url, Map<String, String> params) {
		return get(url, params, null);
	}

	public static ContentResponse get(String url, Map<String, String> params, Map<String, String> headers) {
		return get(url, params, headers, null);
	}

	public static ContentResponse get(String url, Map<String, String> params, Map<String, String> headers,
			HttpCookie cookie) {
		return request(url, HttpMethod.GET, params, headers, null, cookie);
	}

	public static ContentResponse post(String url) {
		return post(url, null, null, null);
	}

	public static ContentResponse post(String url, Map<String, String> headers) {
		return post(url, null, headers, null);
	}

	public static ContentResponse post(String url, String body) {
		return post(url, null, null, body);
	}

	public static ContentResponse post(String url, Map<String, String> headers, String body) {
		return post(url, null, headers, body);
	}

	public static ContentResponse post(String url, Map<String, String> params, Map<String, String> headers,
			String body) {
		return post(url, params, headers, body, null);
	}

	public static ContentResponse post(String url, Map<String, String> params, Map<String, String> headers, String body,
			HttpCookie cookie) {
		return request(url, HttpMethod.POST, params, headers, body, cookie);
	}

	public static ContentResponse put(String url) {
		return put(url, null, null, null);
	}

	public static ContentResponse put(String url, Map<String, String> headers) {
		return put(url, null, headers, null);
	}

	public static ContentResponse put(String url, String body) {
		return put(url, null, null, body);
	}

	public static ContentResponse put(String url, Map<String, String> headers, String body) {
		return put(url, null, headers, body);
	}

	public static ContentResponse put(String url, Map<String, String> params, Map<String, String> headers,
			String body) {
		return put(url, params, headers, body, null);
	}

	public static ContentResponse put(String url, Map<String, String> params, Map<String, String> headers, String body,
			HttpCookie cookie) {
		return request(url, HttpMethod.PUT, params, headers, body, cookie);
	}

	public static ContentResponse delete(String url) {
		return delete(url, null, null);
	}

	public static ContentResponse delete(String url, Map<String, String> params) {
		return delete(url, params, null);
	}

	public static ContentResponse delete(String url, Map<String, String> params, Map<String, String> headers) {
		return delete(url, params, headers, null);
	}

	public static ContentResponse delete(String url, Map<String, String> params, Map<String, String> headers,
			HttpCookie cookie) {
		return request(url, HttpMethod.DELETE, params, headers, null, cookie);
	}

	public static ContentResponse request(String url, HttpMethod method, Map<String, String> params,
			Map<String, String> headers, String body, HttpCookie cookie) {
		Request request = client.newRequest(url);
		request.method(method);

		if (params != null && !params.isEmpty()) {
			for (Entry<String, String> param : params.entrySet()) {
				request.param(param.getKey(), param.getValue());
			}
		}

		if (body != null && !body.isEmpty()) {
			request.content(new StringContentProvider(body, "utf-8"));
		}

		if (headers != null && !headers.isEmpty()) {
			for (Entry<String, String> header : headers.entrySet()) {
				request.header(header.getKey(), header.getValue());
			}
		}
		if (cookie != null) {
			request.cookie(cookie);
		}
		try {
			return request.send();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}
}
