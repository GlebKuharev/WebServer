package com.epam.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.epam.pojo.Book;

public class XMLHelper {

	public static String marshall(Object obj) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			StringWriter writer = new StringWriter();
			jaxbMarshaller.marshal(obj, writer);
			return writer.toString();
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return "";

	}

	public static Book unmarshall(String messageBody) {
		try {
			InputStream stream = new ByteArrayInputStream(messageBody.getBytes());
			JAXBContext jaxbContext = JAXBContext.newInstance(Book.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			return (Book) jaxbUnmarshaller.unmarshal(stream);
		  } catch (JAXBException e) {
			e.printStackTrace();
		  }
		return null;
	}
}
