package com.digitalbook.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.digitalbook.RoleRepository;
import com.digitalbook.UserRepository;
import com.digitalbook.entity.Book;
import com.digitalbook.entity.BookAuthor;
import com.digitalbook.entity.BookCategory;
import com.digitalbook.entity.ERole;
import com.digitalbook.entity.Role;
import com.digitalbook.entity.User;
import com.digitalbook.request.SignupRequest;
import com.digitalbook.service.impl.DigitalBookServiceImpl;
import com.digitalbook.service.impl.UserServiceImpl;

/**
 * 
 * @author supriya
 * This is BookControllerTest which is used for testing BookController methods and register method in AuthController
 *
 */

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class BookControllerTest {
	@Mock
	DigitalBookServiceImpl bookService;
	
	@Mock
	UserServiceImpl userService;
	
	@Mock
	UserRepository userRepository;

	@Mock
	RoleRepository roleRepository;
	
	@Mock
	PasswordEncoder encoder;
	
	@InjectMocks
	BookController controller;
	
	@InjectMocks
	AuthController authController;
	
	Role getAuthorRole() {
		Role role = new Role();
		role.setId(1);
		role.setName(ERole.ROLE_AUTHOR);
		return role;
	}
	
	Role getReaderRole() {
		Role role = new Role();
		role.setId(1);
		role.setName(ERole.ROLE_READER);
		return role;
	}
	
	BookAuthor getBookAuthor() {
		BookAuthor bookAuthor = new BookAuthor();
		bookAuthor.setBook(getBook());
		bookAuthor.setEmailId(getAuthor().getEmailId());
		return bookAuthor;
	}
	

	
	User getAuthor() {
		User author = new User();
		author.setId(1);
		author.setName("Author");
		author.setEmailId("author@gmail.com");
		author.setUserName("author");
		author.setPassword("123456");
		Set<Role> roles = new HashSet<>();
		roles.add(getAuthorRole());
		author.setRoles(roles);
		return author;
	}
	
	User getReader() {
		User reader = new User();
		reader.setId(1);
		reader.setName("Reader");
		reader.setEmailId("reader@gmail.com");
		reader.setUserName("reader");
		reader.setPassword("123456");
		Set<Role> roles = new HashSet<>();
		roles.add(getReaderRole());
		reader.setRoles(roles);
		return reader;
	}
	
	Book getBook() {
		Book book = new Book();
		book.setId(1);
		book.setLogo("Digitalbook1.url");
		book.setTitle("Digitalbook1");
		book.setCategory(BookCategory.ADVENTURE);
		book.setPrice(new BigDecimal(1.0));
		User author = getAuthor();
		book.setAuthorName(author.getName());
		book.setAuthorUserName(author.getUserName());
		book.setPublisher("XYZ Publisher");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate publishedDate = LocalDate.parse("01/09/2022", formatter);
		book.setPublishedDate(publishedDate );
		book.setContent("Digitalbook1 content");
		book.setActive(true);
		return book;
	}
	
	SignupRequest getSignupRequest() {
		Set<String> role = new HashSet<>();
		role.add("author");
		SignupRequest signupRequest = new SignupRequest();
		signupRequest.setName("Author");
		signupRequest.setUserName("author");
		signupRequest.setEmailId("author@gmail.com");
		signupRequest.setPassword("123456");
		signupRequest.setRole(role);
		return signupRequest;
	}

	@Test
	void testSaveBook() {
		User author = getAuthor();
		Book book = getBook();
		Book book1 = null;
		int authorId = 1;
		String title = "DigitalBook1";
		when(userService.getUser(authorId, ERole.ROLE_AUTHOR)).thenReturn(author);
		when(bookService.getBook(title)).thenReturn(book1);
		when(bookService.saveBook(book)).thenReturn(book);
		ResponseEntity<Integer> savedbookId = controller.saveBook(1, book);
		assertEquals(1, savedbookId.getBody());
	}
	
	@Test
	void testSaveBook1() {
		User author = getAuthor();
		Book book = getBook();
		Book book1 = null;
		int authorId = 1;
		String title = "DigitalBook1";
		when(userService.getUser(authorId, ERole.ROLE_AUTHOR)).thenReturn(author);
		when(bookService.getBook(title)).thenReturn(book1);
		when(bookService.saveBook(book)).thenReturn(book);
		ResponseEntity<Integer> savedbookId = controller.saveBook(1, book);
		assertEquals(1, savedbookId.getBody());
	}
	
	//@Test
	void testSaveBook2() {
		Book book = getBook();
		Book book1 = getBook();
		String title = "DigitalBook1";
		when(bookService.getBook(title)).thenReturn(book1);
		ResponseEntity<Integer> savedbookId = controller.saveBook(1, book);
		assertEquals(HttpStatus.CONFLICT, savedbookId.getStatusCode());
	}

	@Test
	void testSearchBooks() {
		Book book = getBook();
		List<Book> listOfBooks = new ArrayList<>();
		listOfBooks.add(book);
		String title = "DigitalBook1";
		String category="Adventure";
		String author="Author";
		BigDecimal price= new BigDecimal(1.0);
		String publisher="XYZ Publisher";
		when(bookService.searchBooks(title, category, author, price, publisher)).thenReturn(listOfBooks);
		ResponseEntity<List<Book>> actual= controller.searchBooks(title, category, author, price, publisher);
		assertEquals(listOfBooks, actual.getBody());
	}
	
	@Test
	void testGetAllAuthorBooks() {
		User author = getAuthor();
		Book book = getBook();
		List<Book> listOfBooks = new ArrayList<>();
		listOfBooks.add(book);
		Integer authorId=1;
		when(userService.getUser(authorId, ERole.ROLE_AUTHOR)).thenReturn(author);
		when(bookService.getAllAuthorBooks(author.getUserName())).thenReturn(listOfBooks);
		ResponseEntity<List<Book>> actual= controller.getAllAuthorBooks(authorId);
		assertEquals(listOfBooks, actual.getBody());
	}
	
	@Test
	void testGetAuthorBook() {
		User author = getAuthor();
		Book book = getBook();
		Integer bookId=1;
		Integer authorId=1;
		when(userService.getUser(authorId, ERole.ROLE_AUTHOR)).thenReturn(author);
		when(bookService.getAuthorBook(bookId, author.getUserName())).thenReturn(book);
		ResponseEntity<Book> actual= controller.getAuthorBook(authorId, bookId);
		assertEquals(book, actual.getBody());
	}
	
	@Test
	void testGetAuthorBook1() {
		User author = getAuthor();
		Book book = null;
		Integer bookId=2;
		Integer authorId=1;
		when(userService.getUser(authorId, ERole.ROLE_AUTHOR)).thenReturn(author);
		when(bookService.getAuthorBook(bookId, author.getUserName())).thenReturn(book);
		ResponseEntity<Book> actual= controller.getAuthorBook(authorId, bookId);
		assertEquals(HttpStatus.NO_CONTENT, actual.getStatusCode());
	}
	
	
	@Test
	void testRegisterUser(){
		SignupRequest signUpRequest = getSignupRequest();
		String encodedPassword = "$2a$10$oBCsNlxtJ70Q.usYdcFFse6YUBVm.VYk4fcAMGxsXecLuazju9poq";
		Optional<Role> readerRole = Optional.of(getReaderRole());
		Optional<Role> authorRole = Optional.of(getAuthorRole());
		User user = getAuthor();
		when(userRepository.existsByUserName(signUpRequest .getUserName())).thenReturn(false);
		when(userRepository.existsByEmailId(signUpRequest.getEmailId())).thenReturn(false);
		when(encoder.encode(signUpRequest.getPassword())).thenReturn(encodedPassword);
		when(roleRepository.findByName(ERole.ROLE_READER)).thenReturn(readerRole);
		when(roleRepository.findByName(ERole.ROLE_AUTHOR)).thenReturn(authorRole);
		when(userRepository.save(user)).thenReturn(user);
		ResponseEntity<?> actual = authController.registerUser(signUpRequest);
		assertEquals(HttpStatus.OK, actual.getStatusCode());
	}
	
	@Test
	void testRegisterUser1(){
		SignupRequest signUpRequest = getSignupRequest();
		String encodedPassword = "$2a$10$oBCsNlxtJ70Q.usYdcFFse6YUBVm.VYk4fcAMGxsXecLuazju9poq";
		Optional<Role> readerRole = Optional.of(getReaderRole());
		User user = getAuthor();
		Exception e = new RuntimeException("Error: Role is not found.");
		when(userRepository.existsByUserName(signUpRequest .getUserName())).thenReturn(false);
		when(userRepository.existsByEmailId(signUpRequest.getEmailId())).thenReturn(false);
		when(encoder.encode(signUpRequest.getPassword())).thenReturn(encodedPassword);
		when(roleRepository.findByName(ERole.ROLE_READER)).thenReturn(readerRole);
		when(roleRepository.findByName(ERole.ROLE_AUTHOR)).thenThrow(e);
		when(userRepository.save(user)).thenReturn(user);
		assertThrows(RuntimeException.class,()->{
			authController.registerUser(signUpRequest);
		});
		Set<String> role = new HashSet<>();
		role.add("reader");
		signUpRequest.setRole(role);
		when(roleRepository.findByName(ERole.ROLE_READER)).thenThrow(e);
		assertThrows(RuntimeException.class,()->{
			authController.registerUser(signUpRequest);
		});
	}
	
	@Test
	void testRegisterUser3(){
		SignupRequest signUpRequest = getSignupRequest();
		String encodedPassword = "$2a$10$oBCsNlxtJ70Q.usYdcFFse6YUBVm.VYk4fcAMGxsXecLuazju9poq";
		Optional<Role> readerRole = Optional.of(getReaderRole());
		Optional<Role> authorRole = Optional.of(getAuthorRole());
		User user = getAuthor();
		Exception e = new RuntimeException("Error: Role is not found.");
		when(userRepository.existsByUserName(signUpRequest .getUserName())).thenReturn(false);
		when(userRepository.existsByEmailId(signUpRequest.getEmailId())).thenReturn(false);
		when(encoder.encode(signUpRequest.getPassword())).thenReturn(encodedPassword);
		when(roleRepository.findByName(ERole.ROLE_READER)).thenReturn(readerRole);
		when(roleRepository.findByName(ERole.ROLE_AUTHOR)).thenReturn(authorRole);
		when(userRepository.save(user)).thenReturn(user);
		
		Set<String> role = new HashSet<>();
		role.add("reader");
		signUpRequest.setRole(role);
		when(roleRepository.findByName(ERole.ROLE_AUTHOR)).thenReturn(authorRole);
		when(roleRepository.findByName(ERole.ROLE_READER)).thenReturn(readerRole);
		ResponseEntity<?> actual = authController.registerUser(signUpRequest);
		assertEquals(HttpStatus.OK, actual.getStatusCode());
		signUpRequest.setRole(null);
		actual = authController.registerUser(signUpRequest);
		assertEquals(HttpStatus.OK, actual.getStatusCode());
		when(roleRepository.findByName(ERole.ROLE_READER)).thenThrow(e);
		assertThrows(RuntimeException.class,()->{
			authController.registerUser(signUpRequest);
		});
		when(userRepository.existsByUserName(signUpRequest .getUserName())).thenReturn(true);
		actual = authController.registerUser(signUpRequest);
		assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
		when(userRepository.existsByUserName(signUpRequest .getUserName())).thenReturn(false);
		when(userRepository.existsByEmailId(signUpRequest.getEmailId())).thenReturn(true);
		actual = authController.registerUser(signUpRequest);
		assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
	}
	
}