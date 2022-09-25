package com.digitalbook.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbook.entity.Book;
import com.digitalbook.entity.BookAuthor;
import com.digitalbook.entity.ERole;
import com.digitalbook.entity.User;
import com.digitalbook.security.jwt.AuthTokenFilter;
import com.digitalbook.service.impl.DigitalBookServiceImpl;
import com.digitalbook.service.impl.UserServiceImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author supriya This is BookController which run methods for book api
 *         searchBooks method is used for fetching all books which match
 *         conditions for title, category, author, price and publisher saveBook
 *         method is used for saving book with author id getAllAuthorBooks
 *         method is used for fetching all author books with author id
 *         getAuthorBook method is used for fetching book with author id and
 *         book id buyBook method is used for purchasing book with book id and
 *         reader id findAllPurchasedBooks method is used for fetching all
 *         purchased books with reader id findPurchasedBookByPaymentId method is
 *         used for fetching purchased book with payment id and reader id
 *         getRefund method is used for refund with reader id and book id
 *         editBook method is used for editing book details with author id
 * 
 * 
 *
 */

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/digitalbooks")
public class BookController extends BaseController {

	@Autowired
	DigitalBookServiceImpl bookService;

	@Autowired
	UserServiceImpl userService;

	@GetMapping("/books/search")
	@PreAuthorize("hasRole('READER')")
	public ResponseEntity<List<Book>> searchBooks(@RequestParam String title, @RequestParam String category,
			@RequestParam String author, @RequestParam BigDecimal price, @RequestParam String publisher) {
		ResponseEntity<List<Book>> response;
		List<Book> listOfBooks = bookService.searchBooks(title, category, author, price, publisher);
		response = new ResponseEntity<>(listOfBooks, HttpStatus.OK);
		return response;
	}

	@PostMapping("/author/{authorId}/books")
	@PreAuthorize("hasRole('AUTHOR')")
	public ResponseEntity<Integer> saveBook(@PathVariable("authorId") int authorId, @Valid @RequestBody Book book) {
		ResponseEntity<Integer> response;
		BookAuthor bookAuthor = new BookAuthor();
		Book book1 = bookService.getBook(book.getTitle());
		if (book1 == null) {
			int bookId = 0;
			User user = userService.getUser(authorId, ERole.ROLE_AUTHOR);
			book.setAuthorName(user.getName());
			book.setAuthorUserName(user.getUserName());
			book = bookService.saveBook(book);
			bookId = book.getId();
			response = new ResponseEntity<>(bookId, HttpStatus.CREATED);
			bookAuthor.setBook(book);
			bookAuthor.setEmailId(user.getEmailId());
		} else {
			response = new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		return response;
	}

	@GetMapping("/author/{authorId}/allbooks")
	@PreAuthorize("hasRole('AUTHOR')")
	public ResponseEntity<List<Book>> getAllAuthorBooks(@PathVariable("authorId") int authorId) {
		ResponseEntity<List<Book>> response;
		User user = userService.getUser(authorId, ERole.ROLE_AUTHOR);
		List<Book> listOfBooks = bookService.getAllAuthorBooks(user.getUserName());
		response = new ResponseEntity<>(listOfBooks, HttpStatus.OK);
		return response;
	}

	@GetMapping("/author/{authorId}/book/{bookId}")
	@PreAuthorize("hasRole('AUTHOR')")
	public ResponseEntity<Book> getAuthorBook(@PathVariable("authorId") int authorId,
			@PathVariable("bookId") Integer bookId) {
		ResponseEntity<Book> response;
		User user = userService.getUser(authorId, ERole.ROLE_AUTHOR);
		Book authorBook = bookService.getAuthorBook(bookId, user.getUserName());
		if (authorBook == null) {
			response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			response = new ResponseEntity<>(authorBook, HttpStatus.OK);
		}
		return response;
	}

}
