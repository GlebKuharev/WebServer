package com.epam.server;

import java.net.*;

import com.epam.service.HttpFormatException;
import com.epam.service.HttpRequestParser;
import com.epam.service.RequestHandler;

import java.io.*;

public class MultiServerThread extends Thread {
	
	private Socket socket = null;
	
	static final String HTML_START =
			"<html>" +
			"<title>HTTP Server in java</title>" +
			"<body>";

			static final String HTML_END =
			"</body>" +
			"</html>";

	public MultiServerThread(Socket socket) {
		super("KKMultiServerThread");
		this.socket = socket;
	}

	public void run() {

		try (
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(
						new InputStreamReader(
								socket.getInputStream()));
				) {

			String request = readRequest(in);
			//System.out.println(request);
			HttpRequestParser parsedRequest = new HttpRequestParser().parseRequest(request);
			//System.out.println(parsedRequest);
			String response = new RequestHandler().handleRequest(parsedRequest, out);		
//			sendResponse(response, out);
			socket.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sendResponse (int statusCode, String responseString, Writer out) throws Exception {

		String statusLine = null;
		String serverdetails = "Server: Java HTTPServer";
		String contentLengthLine = null;
		String contentTypeLine = "Content-Type: text/html" + "\r\n";

		if (statusCode == 200)
			statusLine = "HTTP/1.1 200 OK" + "\r\n";
		else
			statusLine = "HTTP/1.1 404 Not Found" + "\r\n";

		responseString = MultiServerThread.HTML_START + responseString + MultiServerThread.HTML_END;
		contentLengthLine = "Content-Length: " + responseString.length() + "\r\n";

		out.write(statusLine); 
		out.write(serverdetails);
		out.write(contentTypeLine);
		out.write(contentLengthLine);
		out.write("Connection: close\r\n");
		out.write("\r\n");
		out.write(responseString);
		out.close();
	}

	public static String readRequest(BufferedReader in) {
		String inputLine;
		StringBuffer request = new StringBuffer();

		try {
			while ((inputLine = in.readLine()) != null) {
				request.append(inputLine + "\r\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return request.toString();
	}
}
