package com.epam.service;

import java.io.PrintWriter;
import java.util.List;
import java.util.ListIterator;

import com.epam.dao.BookCollection;
import com.epam.pojo.Book;
import com.epam.utils.JsonHelper;
import com.epam.utils.SupportedMethods;
import com.epam.utils.XMLHelper;

//to be optimized within the following days
public class RequestHandler {

	public static void handleRequest(Request parsedRequest, PrintWriter out) throws Exception {

		String requestLine = parsedRequest.getRequestLine();
		String path = parsedRequest.parseUri(requestLine);

		if (requestLine.startsWith(SupportedMethods.GET)) {

			switch (path) {
			case "/book":
				if (parsedRequest.getHeaderParam("Accept").contains("application/xml")) {
					Response response = new Response(out);
					response.setBody(XMLHelper.marshall(BookCollection.getInstance()));
					response.setStatusCode(200);
					response.setContentType("application/xml");
					response.send();
				} else if (parsedRequest.getHeaderParam("Accept").contains("application/json")) {
					Response response = new Response(out);
					response.setBody(JsonHelper.toJson(BookCollection.getInstance()));
					response.setStatusCode(200);
					response.setContentType("application/json");
					response.send();
				}
				break;
			default:
				Response response = new Response(out);
				response.setStatusCode(404);
				response.send();
				break;
			}

		} else if (requestLine.startsWith(SupportedMethods.POST)) {
			
			if (parsedRequest.getHeaderParam("Content-Type").contains("application/xml")) {
				Response response = new Response(out);
				Book book = XMLHelper.unmarshall(parsedRequest.getMessageBody());
				int param_id = book.getId();
				List<Book> bookList = BookCollection.getInstance().getBookList();
				ListIterator<Book> iter = bookList.listIterator();
				while(iter.hasNext()){
					if(iter.next().getId() == param_id) {
						iter.remove();
						iter.add(book);
						response.setStatusCode(200);
						response.send();
						return;
					}
				}
				response.setStatusCode(400);
				response.send();
			}
			else if (parsedRequest.getHeaderParam("Content-Type").contains("application/json")) {
				Response response = new Response(out);
				Book book = JsonHelper.toObject(parsedRequest.getMessageBody());
				int param_id = book.getId();
				List<Book> bookList = BookCollection.getInstance().getBookList();
				ListIterator<Book> iter = bookList.listIterator();
				while(iter.hasNext()){
					if(iter.next().getId() == param_id) {
						iter.remove();
						iter.add(book);
						response.setStatusCode(200);
						response.send();
						return;
					}
				}
				response.setStatusCode(400);
				response.send();
			}
			else {
				Response response = new Response(out);
				response.setStatusCode(400);
				response.send();
			}

		} else if (requestLine.startsWith(SupportedMethods.PUT)) {

			if (parsedRequest.getHeaderParam("Content-Type").contains("application/xml")) {
				Response response = new Response(out);
				Book newBook = XMLHelper.unmarshall(parsedRequest.getMessageBody());
				boolean flag = true;
				for (Book book: BookCollection.getInstance().getBookList()) {
					if (book.getId() == newBook.getId()) {
						flag = false;
						break;
					}
				}
				if (flag) {
					BookCollection.getInstance().addBook(newBook);
					response.setStatusCode(201);
				}
				else {
					response.setStatusCode(400);
				}
				response.send();
			}
			else if (parsedRequest.getHeaderParam("Content-Type").contains("application/json")) {
				Response response = new Response(out);
				Book newBook = JsonHelper.toObject(parsedRequest.getMessageBody());
				boolean flag = true;
				for (Book book: BookCollection.getInstance().getBookList()) {
					if (book.getId() == newBook.getId()) {
						flag = false;
						break;
					}
				}
				if (flag) {
					BookCollection.getInstance().addBook(newBook);
					response.setStatusCode(201);
				}
				else {
					response.setStatusCode(400);
				}
				response.send();
			}
			else {
				Response response = new Response(out);
				response.setStatusCode(400);
				response.send();
			}

		} else if (requestLine.startsWith(SupportedMethods.DELETE)) {

			Response response = new Response(out);
			int param_id = Integer.parseInt(parsedRequest.mapQuery(path).get("id"));
			List<Book> bookList = BookCollection.getInstance().getBookList();
			ListIterator<Book> iter = bookList.listIterator();
			while(iter.hasNext()){
				if(iter.next().getId() == param_id) {
					iter.remove();
					response.setStatusCode(200);
					response.send();
					return;
				}
			}
			response.setStatusCode(400);
			response.send();
		} else {

		}
	}


}
