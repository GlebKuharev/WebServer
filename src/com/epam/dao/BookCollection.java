package com.epam.dao;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.epam.pojo.Book;

@XmlRootElement(name = "booklist")
@XmlAccessorType(XmlAccessType.NONE)
public class BookCollection {

    @XmlElement(name = "book")
    private List<Book> bookList;

    private static final BookCollection instance = new BookCollection();
    
	private BookCollection() {
		bookList = new LinkedList<>();
	}

    public static BookCollection getInstance(){
        return instance;
    }
    
    public BookCollection(List<Book> bookList) {
	this.bookList = bookList;
    }

    public List<Book> getBookList() {
	return bookList;
    }

    public void setBooks(List<Book> bookList) {
	this.bookList = bookList;
    }
    
    public void addBook(Book book) {
    	this.bookList.add(book);
    }
    
    public void deleteBook(Book book) {
    	this.bookList.remove(book);
    }
}