package com.digitalbook.service;

import java.math.BigDecimal;
import java.util.List;

import com.digitalbook.entity.Book;

/**
 * 
 * @author supriya
 * This is BookService interface which used for defining book details methods
 *
 */

public interface DigitalBookService {

	public Book saveBook(Book book);

	public List<Book> searchBooks(String title, String category, String author, BigDecimal price, String publisher);

	public Book getBook(Integer bookId);

}
