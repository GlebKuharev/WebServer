package com.epam.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import com.epam.service.exception.HttpFormatException;

public class Request {

	private String _requestLine;
	private Map<String, String> _requestHeaders;
	private StringBuffer _messageBody;
	private Map<String, String> queryMap;


	public Request() {
		_requestHeaders = new HashMap<String, String>();
		_messageBody = new StringBuffer();
	}

	public Request readAndParse(BufferedReader reader) throws IOException, HttpFormatException {

		setRequestLine(reader.readLine());
		String header = reader.readLine();
		int contentLength = 0;

		while (header.length() > 0) {
			appendHeaderParameter(header);
			if (header.contains("Content-Length")) {
				contentLength = Integer.parseInt(this._requestHeaders.get("Content-Length"));
			}
			header = reader.readLine();
		}
		
		if (contentLength >0) {
			StringWriter body = new StringWriter();
			char[] buffer = new char[1024];
			int charToWrite;
			while ((charToWrite = reader.read(buffer)) != -1) {
				body.write(buffer, 0, charToWrite);
				if (charToWrite == contentLength) {
					break;
				}
			}
			appendMessageBody(body.toString());
		}
		return this;
	}

	public String getRequestLine() {
		return _requestLine;
	}

	private void setRequestLine(String requestLine) throws HttpFormatException {
		if (requestLine == null || requestLine.length() == 0) {
			throw new HttpFormatException("Invalid Request-Line: " + requestLine);
		}
		_requestLine = requestLine;
	}

	private void appendHeaderParameter(String header) throws HttpFormatException {
		int idx = header.indexOf(":");
		if (idx == -1) {
			throw new HttpFormatException("Invalid Header Parameter: " + header);
		}
		_requestHeaders.put(header.substring(0, idx), header.substring(idx + 2, header.length()));
	}

	public String getMessageBody() {
		return _messageBody.toString();
	}

	private void appendMessageBody(String bodyLine) {
		_messageBody.append(bodyLine).append("\r\n");
	}

	public String getHeaderParam(String headerName){
		return _requestHeaders.get(headerName);
	}

	@Override
	public String toString() {
		return "Request [_requestLine=" + _requestLine + ", _requestHeaders=" + _requestHeaders
				+ ", _messageBody=" + _messageBody + "]";
	}

	public String parseUri(String requestString) {
		int index1, index2;
		index1 = requestString.indexOf(' ');

		if (index1 != -1) {
			index2 = requestString.indexOf(' ', index1 + 1);
			if (index2 > index1)
				return requestString.substring(index1 + 1, index2);
		}

		return null;
	}

	public Map<String, String> mapQuery(String path) throws MalformedURLException {

		String query = path.substring(path.indexOf("?")+1);
		System.out.println("query: " + query);
		String[] params = query.split("&");  
		queryMap = new HashMap<String, String>();  
		for (String param : params)  
		{  
			String name = param.split("=")[0];  
			String value = param.split("=")[1];  
			queryMap.put(name, value);  
		}
		return queryMap;
	}

	public Map<String, String> getQueryMap() {
		return queryMap;
	}

	public void setQueryMap(Map<String, String> queryMap) {
		this.queryMap = queryMap;
	}
}


