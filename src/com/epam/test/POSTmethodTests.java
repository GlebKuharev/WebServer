package com.epam.test;

import static io.restassured.RestAssured.given;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.epam.pojo.Book;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

public class POSTmethodTests extends ConfigurationTest {
  
	@Test
    public void ServerRespondsToPOSTWith400WhenAddingNewEntryViaJSON() {

		Book book = new Book(5, "NewAuthor", "NewBook", 1587);

    	given().
    		header("Content-Type", "application/json").
    		contentType(ContentType.JSON).
    		body(book).
    	when().
    		post().
    	then().
    		assertThat().
    			statusCode(400);
    }
	
	@Test
	public void oneCanUpdateExistingEntryViaJsonUsingPOST() {
		
		Book book = new Book(3, "NewAuthor", "NewBook", 1587);
		
		given().
			header("Content-Type", "application/json").
			contentType(ContentType.JSON).
			body(book).
		when().
			post().
		then().
			assertThat().
				statusCode(200);
	
    	JsonPath jsonPath =
	    given().
			header("Accept", "application/json").
		when().
			get("/book").
		then().
			extract().body().jsonPath();
    	
    	List<Book> books = jsonPath.getList("bookList", Book.class); 
    	
  	  	boolean isPresent = false;
  	  	for (Book b: books)
  		  if (b.equals(book)) {
  			  isPresent= true;
  			  break;
  		  }
  	  	Assert.assertTrue(isPresent);
    }
    
    @Test (enabled = true)
    public void ServerRespondsToPOSTWith400WhenAddingNewEntryViaXML() {

		Book book = new Book(6, "AnotherNewAuthor", "AnotherNewBook", 2050);

    	given().
    		header("Content-Type", "application/xml").
    		contentType(ContentType.XML).
    		body(book).
    	when().
    		post().
    	then().
    		assertThat().
    			statusCode(400);
    }
    
    @Test ()
	public void ServerRespondsToPOSTWith200WhenUpdatingExistingEntryViaXML() {
    	
    	Book book = new Book(2, "AnotherNewAuthor", "AnotherNewBook", 2050);
    	
		given().
			header("Content-Type", "application/xml").
			contentType(ContentType.XML).
			body(book).
		when().
			post().
		then().
			assertThat().
				statusCode(200);   	
    }
    
    @Test (enabled = false)
    public void ServerRespondsToPOSTWith400WhenRequestIsEmpty() {
    	
    	given().
		when().
			post().
		then().
			assertThat().
				statusCode(400);  
    }
}
