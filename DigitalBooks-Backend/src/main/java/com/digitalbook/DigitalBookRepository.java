package com.digitalbook;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalbook.entity.Book;
import com.digitalbook.entity.BookCategory;

/**
 * 
 * @author supriya
 * This is BookRepository which is used for saving book details and fetching book details from db
 *
 */

public interface DigitalBookRepository extends JpaRepository<Book, Integer>{

	Optional<Book> findByTitle(String title);
	
	Optional<List<Book>> findByCategory(BookCategory category);
	
	Optional<List<Book>> findByAuthorUserName(String authorUserName);
	
	Optional<List<Book>> findByPrice(BigDecimal price);
	
	Optional<List<Book>> findByPublisher(String publisher);
	
}
