package com.epam.utils;

import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class XMLHelper {

	public static String marshall(Object obj, Writer out) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			StringWriter writer = new StringWriter();
			jaxbMarshaller.marshal(obj, writer);
			jaxbMarshaller.marshal(obj, System.out);
			
			return writer.toString();
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return "";

	}
}
