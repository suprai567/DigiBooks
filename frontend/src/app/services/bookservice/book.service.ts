import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import Book, { BookCategory } from 'src/app/entity/Book';
import { TokenStorageService } from '../token-storage/token-storage.service';
const API_URL = 'http://localhost:8082/digitalbooks';
@Injectable({
  providedIn: 'root'
})
export class BookService {
  book:Book= new Book('DigitalBook1book1_url', 'DigitalBook1', BookCategory.ADVENTURE, 1, '', '', 'XYZ Publisher', new Date(), 'Digitalbook1 content', true);
  public book1: any;
  public user: any;
  constructor(public client: HttpClient, private tokenStorageService: TokenStorageService, private router: Router) {
    this.user = this.tokenStorageService.getUser();
   }

  saveBook(title: string, logo: string, category: BookCategory, price: number, authorUserName: string, authorName: string, publisher: string, publishedDate: Date, content: string, active: Boolean){
    const authorId = this.user.id;
    this.book = new Book(logo, title, category, price, authorUserName, authorName, publisher, publishedDate, content, active);
    return this.client.post(API_URL + "/author/" + authorId + "/books", this.book);
  }

  searchBooks(title: String, category: String, author: String, price: number, publisher: String){
    return this.client.get(API_URL + "/books/search?title=" + title + "&category=" + category + 
    "&author=" + author + "&price=" + price + "&publisher=" + publisher);
  }

  getAllAuthorBooks() {
    const authorId = this.user.id;
    return this.client.get(API_URL + "/author/" + authorId + "/allbooks");
  }

  redirectTologin(){
    this.tokenStorageService.signOut();
    this.router.navigate(['/login']).then(() => {
      window.location.reload();
    });
  }
  
}
