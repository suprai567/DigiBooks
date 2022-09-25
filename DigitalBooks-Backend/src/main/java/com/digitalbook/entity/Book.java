package com.digitalbook.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 
 * @author supriya
 * Book entity is used for declaring the details of book and validation of book details
 *
 */

@Data
@Entity
@Table(	name = "book", 
uniqueConstraints = { 
	@UniqueConstraint(columnNames = "title")
})
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank(message = "logo cannot be blank#######")
	private String logo;
	
	@NotBlank(message = "title cannot be blank#######")
	private String title;
	
	@NotNull(message = "category cannot be blank#######")
	@Enumerated(EnumType.STRING)
	private BookCategory category;
	
	@NotNull(message = "price cannot be null#######")
	@DecimalMin(value = "1.0", message = "price cannot be less than 1")
	@Digits(integer = 3, fraction = 2)
	private BigDecimal price;
	
	private String authorUserName;
	
	private String authorName;
	
	@NotBlank(message = "publisher cannot be blank#######")
	private String publisher;
	
	@NotNull(message = "publishedDate cannot be blank#######")
	@DateTimeFormat(style = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate publishedDate;
	
	@NotBlank(message = "content cannot be blank#######")
	private String content;
	
	@NotNull(message = "active cannot be null#######")
	private Boolean active;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BookCategory getCategory() {
		return category;
	}

	public void setCategory(BookCategory category) {
		this.category = category;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getAuthorUserName() {
		return authorUserName;
	}

	public void setAuthorUserName(String authorUserName) {
		this.authorUserName = authorUserName;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public LocalDate getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(LocalDate publishedDate) {
		this.publishedDate = publishedDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
	
}
