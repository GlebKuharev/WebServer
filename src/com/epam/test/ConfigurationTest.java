package com.epam.test;

import static io.restassured.RestAssured.*;

import org.testng.annotations.BeforeSuite;

public class ConfigurationTest {
	
    @BeforeSuite
    public void setUpRestAssured() {
        baseURI = "http://localhost";
        port = 4444;
    }
}
