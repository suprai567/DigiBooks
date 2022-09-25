package com.digitalbook.entity;

import org.springframework.stereotype.Component;
import lombok.Data;

/**
 * 
 * @author supriya
 * BookAuthor entity is used for sending book details and email id for email service
 *
 */

@Data
@Component
public class BookAuthor {
	private Book book;
	private String emailId;
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	
}
