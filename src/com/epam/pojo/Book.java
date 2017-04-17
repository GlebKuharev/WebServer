package com.epam.pojo;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Book {
	
	private int id;
	private String author;
	private String title;
	private int releaseDate;
	
	public Book() {	
		super();
	}
	
	public Book(int id, String author, String title, int releaseDate) {
		super();
		this.setId(id);
		this.author = author;
		this.title = title;
		this.releaseDate = releaseDate;
	}
	
	public String getAuthor() {
		return author;
	}
	
	@XmlElement
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getTitle() {
		return title;
	}
	
	@XmlElement
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getReleaseDate() {
		return releaseDate;
	}
	
	@XmlElement
	public void setReleaseDate(int releaseDate) {
		this.releaseDate = releaseDate;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", author=" + author + ", title=" + title + ", releaseDate=" + releaseDate + "]";
	}

	public int getId() {
		return id;
	}
	
	@XmlAttribute
	public void setId(int id) {
		this.id = id;
	}
	
	

}
