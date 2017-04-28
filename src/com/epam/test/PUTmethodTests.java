package com.epam.test;

import static io.restassured.RestAssured.given;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.epam.pojo.Book;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

public class PUTmethodTests extends ConfigurationTest {
  
	@Test
    public void ServerRespondsToPUTWith400WhenUpdatingAnExistingEntryViaJSON() {

		Book book = new Book(1, "NewAuthor", "NewBook", 1587);

    	given().
    		header("Content-Type", "application/json").
    		contentType(ContentType.JSON).
    		body(book).
    	when().
    		put().
    	then().
    		assertThat().
    			statusCode(400);
    }
	
	@Parameters("id")
	@Test
	public void ServerRespondsToPUTWith201WhenCreatingNewEntryViaJSON(@Optional("5") int id) {

		Book book = new Book(id, "NewAuthor", "NewBook", 1587);
		
		given().
			header("Content-Type", "application/json").
			contentType(ContentType.JSON).
			body(book).
		when().
			put().
		then().
			assertThat().
				statusCode(201);
	}
	
	@Parameters("id")
    @Test (dependsOnMethods = "ServerRespondsToPUTWith201WhenCreatingNewEntryViaJSON")
    public void newEntryIsAddedAfterNewEntryViaJSONusingPUT (@Optional("5") int id) {

    	JsonPath jsonPath =
	    given().
			header("Accept", "application/json").
		when().
			get("/book").
		then().
			extract().body().jsonPath();
    	
    	List<String> ids = jsonPath.get("bookList.id");
    	Assert.assertTrue(ids.contains(id));
    }
    
    @Test (enabled = true)
    public void ServerRespondsToPUTWith400WhenUpdatingAnExistingEntryViaXML() {

		Book book = new Book(3, "AnotherNewAuthor", "AnotherNewBook", 2050);

    	given().
    		header("Content-Type", "application/xml").
    		contentType(ContentType.XML).
    		body(book).
    	when().
    		put().
    	then().
    		assertThat().
    			statusCode(400);
    }
    
    @Test ()
	public void ServerRespondsToPUTWith201WhenCreatingNewEntryViaXML() {
    	
    	Book book = new Book(6, "AnotherNewAuthor", "AnotherNewBook", 2050);
    	
		given().
			header("Content-Type", "application/xml").
			contentType(ContentType.XML).
			body(book).
		when().
			put().
		then().
			assertThat().
				statusCode(201);   	
    }
    
    @Test (enabled = false)
    public void ServerRespondsToPUTWith400WhenRequestIsEmpty() {
    	
    	given().
		when().
			put().
		then().
			assertThat().
				statusCode(400);  
    }
}
