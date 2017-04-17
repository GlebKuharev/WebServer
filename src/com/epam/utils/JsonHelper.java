package com.epam.utils;

import java.io.IOException;

import com.epam.pojo.Book;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHelper {

	public static String toJson (Object obj) {
		
		ObjectMapper mapper = new ObjectMapper();
		String responseBody = null;
		try {
			responseBody = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj).toString();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return responseBody;

	}

	public static Book toObject(String messageBody) {
		
		ObjectMapper mapper = new ObjectMapper();
		Book book = null;
		try {
			book = mapper.readValue(messageBody, Book.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return book;
	}
}
