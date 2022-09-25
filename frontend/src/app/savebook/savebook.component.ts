import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import Book, { BookCategory } from 'src/app/entity/Book';
import { BookService } from 'src/app/services/bookservice/book.service';
import { AppComponent } from '../app.component';

@Component({
  selector: 'app-savebook',
  templateUrl: './savebook.component.html',
  styleUrls: ['./savebook.component.scss']
})
export class SavebookComponent implements OnInit {
  book:Book= new Book('DigitalBook1_url', 'DigitalBook1', BookCategory.ADVENTURE, 1, '', '', 'XYZ Publisher', new Date(), 'Digitalbook1 content', true);
  form: any = {
    title: this.book.title,
    logo: this.book.logo,
    category: this.book.category,
    price: this.book.price,
    authorUserName: this.book.authorUserName,
    authorName: this.book.authorName,
    publisher: this.book.publisher,
    publishedDate: null,
    content: this.book.content,
    active: this.book.active
  };
  categoryList: BookCategory[] = [];
  bookCategory = BookCategory;
  successMessage: any = "";
  errorMessage: any = "";
  isSuccessful = false;

  constructor(public bookService: BookService) { 
    this.categoryList.push(this.bookCategory.ACTION);
    this.categoryList.push(this.bookCategory.ADVENTURE);
    this.categoryList.push(this.bookCategory.COMEDY);
    this.categoryList.push(this.bookCategory.THRILLER);
    this.categoryList.push(this.bookCategory.ROMANTIC);
    this.categoryList.push(this.bookCategory.FICTION);
    AppComponent.isInitialHome=false;
  }

  ngOnInit(): void {
  }

  save(): void{
    const { title, logo, category, price, authorUserName, authorName, publisher, publishedDate, content, active } = this.form;
    const observable = this.bookService.saveBook(title, logo, category, price, authorUserName, authorName, publisher, publishedDate, content, active);
    observable.subscribe((response)=>{
      if(Number.isFinite(Number(response))){
        this.successMessage = "Book "+title+" is saved successfully";
        this.errorMessage = "";
      }
      else{
        this.errorMessage = JSON.stringify(response);
        this.successMessage = "";
      }
    },
    (error)=>{
      if(error.status == 400){
        this.bookService.redirectTologin();
      }
      if(error.status == 409){
        this.errorMessage = "Book title "+title+" is already used. Try using different title to save the book";
      }
      else{
        this.errorMessage = "Error occurred while saving the book. Please verify the details and save the book";
      }
      this.successMessage = "";
    })
  }
  
}
