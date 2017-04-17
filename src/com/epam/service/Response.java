package com.epam.service;

import java.io.IOException;
import java.io.Writer;

public class Response {

	private Writer outWriter;
	private String version;
	private int statusCode;
	private String server;
	private String contentType;
	private String contentLength;
	private String body;
	private String connection;
	
	public Response() {
		
	}
	
	public Response(Writer outWriter) {
		this.setOutWriter(outWriter);
		this.server = "Java HTTP Server" + "\r\n";
		this.version = "HTTP/1.1 ";
		this.connection = "close\r\n";
	}

	public void send() throws IOException {
		String statusLine = version + statusCode + "\r\n";
		
		outWriter.write(statusLine); 
		outWriter.write("Server: " + server);
		
		if (body != null) {
			outWriter.write("Content-Type: " + contentType + "\r\n");
			outWriter.write("Content-Length: " + body.length() + "\r\n");
		}
		
		outWriter.write("Connection: " + connection);
		outWriter.write("\r\n");
		
		if (body != null)
			outWriter.write(body);
		
		outWriter.close();
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContentLength() {
		return contentLength;
	}

	public void setContentLength(String contentLength) {
		this.contentLength = contentLength;
	}

	public String getConnection() {
		return connection;
	}

	public void setConnection(String connection) {
		this.connection = connection;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Writer getOutWriter() {
		return outWriter;
	}

	public void setOutWriter(Writer out) {
		this.outWriter = out;
	}

}
