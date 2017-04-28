package com.epam.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasXPath;
import static org.hamcrest.Matchers.isEmptyOrNullString;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class GETmethodTests extends ConfigurationTest {
	
    @Test
    public void getJsonBooklistTest() {
    	
    	given().
    		header("Accept", "application/json").
    	when().
    		get("/book").
    	then().
    		assertThat().
    			statusCode(200).
    			body(containsString("bookList")).
    			headers("Content-Type", "application/json").
    			contentType(ContentType.JSON);
    }
    
    @Test
    public void getXMLBooklistTest() {

    	given().
    		header("Accept", "application/xml").
    	when().
    		get("/book").
    	then().assertThat().
    		statusCode(200).
    		body(hasXPath("/booklist")).
    		contentType(ContentType.XML);
    }
    
    @Test
    public void getWrongResourceTest() {
    	
    	given().
    		header("Accept", "application/json").
    		header("Accept", "application/xml").
    	when().
    		get("/strawberry").
    	then().assertThat().
    		body(isEmptyOrNullString()).
    		statusCode(404);
    }
}