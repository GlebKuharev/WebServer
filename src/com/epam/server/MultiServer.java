package com.epam.server;

import java.net.*;

import com.epam.dao.BookCollection;
import com.epam.pojo.Book;

import java.io.*;

public class MultiServer {
    public static void main(String[] args) throws IOException {

    if (args.length != 1) {
        System.err.println("Usage: java KKMultiServer <port number>");
        System.exit(1);
    }

        int portNumber = Integer.parseInt(args[0]);
        boolean listening = true;   
        
        BookCollection.getInstance().addBook(new Book(1, "Ray Bradbury", "Dandellion Wine", 1957));
        BookCollection.getInstance().addBook(new Book(2, "George Orwell", "1984", 1949));
        BookCollection.getInstance().addBook(new Book(3, "Ayn Rand", "Atlas Shrugged", 1957));
        
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) { 
            System.out.println("Server started at port: " + portNumber);
        	while (listening) {
	            new MultiServerThread(serverSocket.accept()).start();
	        }
	    } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
}
