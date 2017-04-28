package com.epam.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.epam.pojo.Book;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;


public class DELETEmethodTests extends ConfigurationTest{
	
	@Parameters("id")
    @Test 
    public void ServerRespondstoDELETEWith200WhenDeletingExistingEntry(int id) {
		
    	given().
    		param("id", id).
    	when().
    		delete("/book").
    	then().
    		assertThat().
    			statusCode(200);
	}
    	
    @Parameters("id")
    @Test (dependsOnMethods = "ServerRespondstoDELETEWith200WhenDeletingExistingEntry")
    public void deletedEntryDisappearsAfterDeletion(int id) {

    	JsonPath jsonPath =
	    given().
			header("Accept", "application/json").
		when().
			get("/book").
		then().
			extract().body().jsonPath();
    	
	  List<Book> books = jsonPath.getList("bookList", Book.class); 

	  boolean isDeleted = true;
	  for (Book book: books)
		  if (book.getId() == id) {
			  isDeleted = false;
			  break;
		  }
	  Assert.assertTrue(isDeleted);
    }
    
    @Test
    public void ServerRespondstoDELETEWith400WhenDeletingNonExistentEntry() {

	    given().
			param("id", 10).
		when().
			delete("/book").
		then().
			assertThat().
				statusCode(400);
    }
    
    @Test (enabled = false)
    public void ServerRespondstoDELETEWith400WhenPassingIncorrectParameters() {

	    given().
			param("idd", 10).
		when().
			delete("/book").
		then().
			assertThat().
				statusCode(400);
    }
}