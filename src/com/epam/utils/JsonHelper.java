package com.epam.utils;

import java.io.Writer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHelper {

	public static void toJson (Object obj, Writer out) {
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

	}
}
