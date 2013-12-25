package com.example.hotnewsapp.http;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.protocol.HTTP;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * HTTP client helper class. Don't use directly!
 * 
 * @see HttpRequest
 * @see HttpResponse
 */
public class HttpClient {

	/**
	 * This class can't be instantiated
	 */
	private HttpClient() {
	}

	/**
	 * Executes an HTTP request
	 * 
	 * @param request
	 * @return HttpResponse
	 * @throws IOException
	 */
	public static HttpResponse execute(HttpRequest request) throws IOException {
		HttpRequest.Method method = request.getMethod();
		URL url = new URL(request.getUrl());

		// open connection
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setInstanceFollowRedirects(request.isFollowRedirects());
		connection.setReadTimeout(request.getReadTimeout());
		connection.setConnectTimeout(request.getConnectTimeout());
		connection.setRequestMethod(method.name());
		connection.setDoInput(true);

		// headers
		for (Map.Entry<String, String> entry : request.getHeaders().entrySet()) {
			connection.setRequestProperty(entry.getKey(), entry.getValue());
		}

		if (method == HttpRequest.Method.POST
				|| method == HttpRequest.Method.PUT) {
			connection.setUseCaches(false);
			connection.setDoOutput(true);
			InputStream contentBodyInputStream = request.getContentBody();
			List<NameValuePair> parameters = request.getParameters();
			if (parameters != null) {
				UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
						parameters, HTTP.UTF_8);
				if (request.getHeader("Content-Type") == null) {
					connection.setRequestProperty("Content-Type", formEntity
							.getContentType().getValue());
				}
				connection.setFixedLengthStreamingMode((int) formEntity
						.getContentLength());
				OutputStream outputStream = new BufferedOutputStream(
						connection.getOutputStream());
				try {
					formEntity.writeTo(outputStream);
				} finally {
					outputStream.close();
				}
			} else if (contentBodyInputStream != null) {
				int bytesRead;
				byte[] buffer = new byte[1024];
				OutputStream outputStream = new BufferedOutputStream(
						connection.getOutputStream());

				try {
					while ((bytesRead = contentBodyInputStream.read(buffer)) != -1) {
						outputStream.write(buffer, 0, bytesRead);
					}
					outputStream.flush();
				} finally {
					outputStream.close();
				}
			}
		} else if (method == HttpRequest.Method.GET) {
			connection.setUseCaches(request.isUseCaches());
			connection.setChunkedStreamingMode(0);
		} else {
			connection.setUseCaches(false);
			connection.setChunkedStreamingMode(0);
		}

		return new HttpResponse(connection);
	}

	/**
	 * Checks if the device has connectivity
	 * 
	 * @param context
	 * @return Boolean true if network is available
	 */
	public static Boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;
	}
}
