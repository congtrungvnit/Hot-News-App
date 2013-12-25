package com.example.hotnewsapp.http;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/**
 * Builds an HTTP request
 * 
 * <pre>
 * {@code
 * HttpRequest request = HttpRequest.get(<url>);
 * request.setUseCaches(false);
 * request.execute();
 * }
 * </pre>
 * 
 */
public class HttpRequest {

	/** HTTP method list */
	public enum Method {
		GET, POST, PUT, DELETE, OPTIONS, HEAD
	}

	/** Method for the request */
	protected Method mMethod;

	/** URL for the request */
	protected String mUrl;

	/** Header for the request */
	protected Map<String, String> mHeaders;

	/** Parameters for POST/PUT requests */
	protected List<NameValuePair> mParameters;

	/** Content body */
	protected InputStream mContentBody;

	/** Read timeout in milliseconds */
	protected int mReadTimeout = 30000;

	/** Connect timout in milliseconds */
	protected int mConnectTimeout = 10000;

	/** Use caches in request */
	protected boolean mUseCaches = true;

	/** Follow redirects */
	protected boolean mFollowRedirects = true;

	/**
	 * Constructor
	 * 
	 * @param method
	 *            HTTP method
	 * @param url
	 *            URL for the HTTP request
	 */
	public HttpRequest(Method method, String url) {
		mMethod = method;
		mUrl = url;
	}

	/**
	 * Returns a HttpRequest instance for GET method
	 * 
	 * @param url
	 *            URL for the HTTP request
	 * @return HttpRequest
	 */
	public static HttpRequest get(String url) {
		return new HttpRequest(Method.GET, url);
	}

	/**
	 * Returns a HttpRequest instance for POST method
	 * 
	 * @param url
	 *            URL for the HTTP request
	 * @return HttpRequest
	 */
	public static HttpRequest post(String url) {
		return new HttpRequest(Method.POST, url);
	}

	/**
	 * Returns a HttpRequest instance for PUT method
	 * 
	 * @param url
	 *            URL for the HTTP request
	 * @return HttpRequest
	 */
	public static HttpRequest put(String url) {
		return new HttpRequest(Method.PUT, url);
	}

	/**
	 * Returns a HttpRequest instance for DELETE method
	 * 
	 * @param url
	 *            URL for the HTTP request
	 * @return HttpRequest
	 */
	public static HttpRequest delete(String url) {
		return new HttpRequest(Method.DELETE, url);
	}

	/**
	 * Returns a HttpRequest instance for OPTIONS method
	 * 
	 * @param url
	 *            URL for the HTTP request
	 * @return HttpRequest
	 */
	public static HttpRequest options(String url) {
		return new HttpRequest(Method.OPTIONS, url);
	}

	/**
	 * Returns a HttpRequest instance for HEAD method
	 * 
	 * @param url
	 *            URL for the HTTP request
	 * @return HttpRequest
	 */
	public static HttpRequest head(String url) {
		return new HttpRequest(Method.HEAD, url);
	}

	/**
	 * Returns the HTTP method
	 * 
	 * @return Method
	 */
	public Method getMethod() {
		return mMethod;
	}

	/**
	 * Returns the URL
	 * 
	 * @return String URL
	 */
	public String getUrl() {
		return mUrl;
	}

	/**
	 * Executes the HTTP request and returns an HttpResponse
	 * 
	 * @see HttpClient
	 * @see HttpResponse
	 * @return HttpResponse
	 * @throws IOException
	 */
	public HttpResponse execute() throws IOException {
		return HttpClient.execute(this);
	}

	/**
	 * Sets an HTTP header
	 * 
	 * @param key
	 *            Header key
	 * @param value
	 *            Header value
	 * @return HttpRequest
	 */
	public HttpRequest setHeader(String key, String value) {
		if (mHeaders == null) {
			mHeaders = new HashMap<String, String>();
		}
		mHeaders.put(key, value);
		return this;
	}

	/**
	 * Returns an HTTP header
	 * 
	 * @param key
	 *            Header key
	 * @return header value
	 */
	public String getHeader(String key) {
		if (mHeaders != null && mHeaders.containsKey(key)) {
			return mHeaders.get(key);
		}
		return null;
	}

	/**
	 * Returns the HTTP headers
	 * 
	 * @return Map<String, String> headers
	 */
	public Map<String, String> getHeaders() {
		if (mHeaders != null) {
			return mHeaders;
		} else {
			return Collections.emptyMap();
		}
	}

	/**
	 * Sets a parameter for POST or PUT requests
	 * 
	 * @param name
	 *            Parameter name
	 * @param value
	 *            Parameter value
	 * @return HttpRequest
	 */
	public HttpRequest setParameter(String name, String value) {
		if (mParameters == null) {
			mParameters = new ArrayList<NameValuePair>();
		}
		mParameters.add(new BasicNameValuePair(name, value));
		return this;
	}

	/**
	 * Sets parameters for POST or PUT requests
	 * 
	 * @param parameters
	 *            List of <code>NameValuePair</code> parameters
	 * @return HttpRequest
	 */
	public HttpRequest setParameters(List<NameValuePair> parameters) {
		mParameters = parameters;
		return this;
	}

	/**
	 * Returns the parameters for POST or PUT requests
	 * 
	 * @return List&lt;NameValuePair&gt; List of <code>NameValuePair</code>
	 *         parameters
	 */
	public List<NameValuePair> getParameters() {
		return mParameters;
	}

	/**
	 * Sets the content body using a InputStream
	 * 
	 * @param contentBody
	 *            InputStream
	 * @return HttpRequest
	 */
	public HttpRequest setContentBody(InputStream contentBody) {
		mContentBody = contentBody;
		return this;
	}

	/**
	 * Sets the content body using a String
	 * 
	 * @param contentBodyString
	 *            String
	 * @return HttpRequest
	 */
	public HttpRequest setContentBody(String contentBodyString) {
		mContentBody = new ByteArrayInputStream(contentBodyString.getBytes());
		return this;
	}

	/**
	 * Returns the content body InputStream
	 * 
	 * @return Content body InputStream
	 */
	public InputStream getContentBody() {
		return mContentBody;
	}

	/**
	 * Sets the Content-Type in headers
	 * 
	 * @param contentType
	 *            String
	 * @return HttpRequest
	 */
	public HttpRequest setContentType(String contentType) {
		this.setHeader("Content-Type", contentType);
		return this;
	}

	/*
	 * public void setRequestProperty() { if (Build.VERSION.SDK != null &&
	 * Build.VERSION.SDK_INT > 13) this.setRequestProperty("Connection",
	 * "close"); }
	 */

	/**
	 * Returns the Content-Type in headers
	 * 
	 * @return Content-Type string
	 */
	public String getContentType() {
		if (mHeaders != null && mHeaders.containsKey("Content-Type")) {
			mHeaders.get("Content-Type");
		}
		return null;
	}

	/**
	 * Sets the User-Agent for the request
	 * 
	 * @param userAgent
	 *            User-Agent string
	 * @return HttpRequest
	 */
	public HttpRequest setUserAgent(String userAgent) {
		this.setHeader("User-Agent", userAgent);
		return this;
	}

	/**
	 * Returns the HTTP read timeout in milliseconds
	 * 
	 * @return int
	 */
	public int getReadTimeout() {
		return mReadTimeout;
	}

	/**
	 * Sets the HTTP read timeout
	 * 
	 * @param readTimeout
	 *            Read timeout in milliseconds
	 * @return HttpRequest
	 */
	public HttpRequest setReadTimeout(int readTimeout) {
		mReadTimeout = readTimeout;
		return this;
	}

	/**
	 * Returns the HTTP connect timeout in milliseconds
	 * 
	 * @return int
	 */
	public int getConnectTimeout() {
		return mConnectTimeout;
	}

	/**
	 * Sets the HTTP connect timeout
	 * 
	 * @param connectTimeout
	 *            Connect timeout in milliseconds
	 * @return HttpRequest
	 */
	public HttpRequest setConnectTimeout(int connectTimeout) {
		mConnectTimeout = connectTimeout;
		return this;
	}

	/**
	 * Test if use caches in the request
	 * 
	 * @return Returns <code>true</code> if use caches
	 */
	public boolean isUseCaches() {
		return mUseCaches;
	}

	/**
	 * Sets the use caches in the request
	 * 
	 * @param useCaches
	 *            <code>true</code> if use caches
	 * @return HttpRequest
	 */
	public HttpRequest setUseCaches(boolean useCaches) {
		mUseCaches = useCaches;
		return this;
	}

	/**
	 * Test if follow redirects in the request
	 * 
	 * @return Returns <code>true</code> if follow redirects
	 */
	public boolean isFollowRedirects() {
		return mFollowRedirects;
	}

	/**
	 * Sets the use follow redirects in the request
	 * 
	 * @param followRedirects
	 *            <code>true</code> if follow redirects
	 * @return HttpRequest
	 */
	public HttpRequest setFollowRedirects(boolean followRedirects) {
		mFollowRedirects = followRedirects;
		return this;
	}

}
