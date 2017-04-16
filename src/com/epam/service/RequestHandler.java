package com.epam.service;

import java.io.PrintWriter;

import com.epam.dao.BookCollection;
import com.epam.server.MultiServerThread;
import com.epam.utils.JsonHelper;
import com.epam.utils.SupportedMethods;
import com.epam.utils.XMLHelper;

public class RequestHandler {

	public String handleRequest(HttpRequestParser parsedRequest, PrintWriter out) throws Exception {

		String requestLine = parsedRequest.getRequestLine();

		if (requestLine.startsWith(SupportedMethods.GET)) {
			
			String path = parsedRequest.parseUri(requestLine);
//System.out.println("URI: " + path);
			
			switch (path) {
			case "/":
				if (parsedRequest.getHeaderParam("Accept").contains("application/xml")) {
					String responseBody = XMLHelper.marshall(BookCollection.getInstance(), out);
					MultiServerThread.sendResponse(200, responseBody, out);
				} else if (parsedRequest.getHeaderParam("Accept").contains("application/json")) {
					JsonHelper.toJson(BookCollection.getInstance(), out);
				}
				
				break;
//			default:
//				return "404";
			}
			
		} else if (requestLine.startsWith(SupportedMethods.POST)) {

		} else if (requestLine.startsWith(SupportedMethods.PUT)) {

		} else if (requestLine.startsWith(SupportedMethods.DELETE)) {

		} else {
			
		}

		return null;
	}


}
