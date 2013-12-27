package com.example.hotnewsapp.http;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

import org.apache.http.HeaderElement;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeaderValueParser;
import org.apache.http.message.HeaderValueParser;
import org.apache.http.protocol.HTTP;

/**
 * Builds an HTTP response
 * 
 * @see HttpRequest
 */
public class HttpResponse {

    /** HttpURLConnection connection */
    protected HttpURLConnection mConnection;

    /**
     * Constructor
     *
     * @param connection HttpURLConnection instance
     * @throws IOException
     */
    public HttpResponse(HttpURLConnection connection) throws IOException {
        mConnection = connection;
        mConnection.connect();
    }

    /** 
     * Returns the response <code>InputStream</code>
     *
     * @return InputStream
     * @throws IOException
     */ 
    public InputStream getInputStream() throws IOException {
        return mConnection.getInputStream();
    }

    /** 
     * Converts the response <code>InputStream</code> and returns a
     * <code>BufferedInputStream</code>
     *
     * @return InputStream
     * @throws IOException
     */
    public InputStream getBufferedInputStream() throws IOException {
        InputStream is = new BufferedInputStream(mConnection.getInputStream());
        return is;
    }

    /**
     * Converts the response <code>InputStream</code> and returns a
     * <code>Reader</code>
     *
     * @return Reader
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    public Reader getReader() throws IOException, UnsupportedEncodingException {
        InputStream is = this.getBufferedInputStream();
        return new InputStreamReader(is, getCharset());
    }

    /**
     * Converts the response <code>InputStream</code> and returns a
     * <code>String</code>
     *
     * @return String
     * @throws IOException
     */
    public String getResponseString() throws IOException {
        int len;
        char[] buffer = new char[2048];

        Reader reader = this.getReader();
        StringBuilder builder = new StringBuilder();

        while ((len = reader.read(buffer)) >= 0) {
            builder.append(buffer, 0, len);
        }

        return builder.toString();
    }

    /**
     * Returns the value from an header
     *
     * @param key Header key
     * @return String Header value
     */
    public String getHeader(String key) {
        return mConnection.getHeaderField(key);
    }

    /**
     * Returns the status code
     *
     * @return int
     * @throws IOException
     */
    public int getStatusCode() throws IOException {
        return mConnection.getResponseCode();
    }

    /**
     * Returns the content encoding
     *
     * @return String
     */
    public String getContentEncoding() {
        return mConnection.getContentEncoding();
    }

    /**
     * Returns the content type
     *
     * @return String
     */
    public String getContentType() {
        return mConnection.getContentType();
    }

    /**
     * Returns the content length
     *
     * @return String
     */
    public int getContentLength() {
        return mConnection.getContentLength();
    }

    /**
     * Returns the charset
     *
     * @return String
     */
    public String getCharset() {
        String contentType = mConnection.getContentType();

        if (contentType != null) {
            HeaderValueParser parser = new BasicHeaderValueParser();
            HeaderElement[] values = BasicHeaderValueParser.parseElements(contentType, parser);
            if (values.length > 0) {
                NameValuePair param = values[0].getParameterByName("charset");
                if (param != null) {
                    return param.getValue();
                }
            }
        }

        // No encoding specified
        return HTTP.UTF_8;
    }

    /**
     * Disconnects
     */
    public void disconnect() {
        mConnection.disconnect();
    }
}
