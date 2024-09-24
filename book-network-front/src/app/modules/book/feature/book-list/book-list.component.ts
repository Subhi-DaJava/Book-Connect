import {Component, inject, OnInit} from '@angular/core';
import {BookService} from "../../../../services/services/book.service";
import {Router} from "@angular/router";
import {PageResponseBookResponse} from "../../../../services/models/page-response-book-response";
import {JsonPipe, NgForOf, NgIf} from "@angular/common";
import {BookDetailsComponent} from "../../components/book-details/book-details.component";
import {BookResponse} from "../../../../services/models/book-response";

@Component({
  selector: 'app-book-list',
  standalone: true,
  imports: [
    NgForOf,
    BookDetailsComponent,
    NgIf,
    JsonPipe
  ],
  templateUrl: './book-list.component.html',
  styleUrl: './book-list.component.scss'
})
export class BookListComponent implements OnInit {
  page: number = 0;
  size: number = 3;
  booksResponse: PageResponseBookResponse = {} ;
  level: string = 'Success';

  bookService = inject(BookService);
  router = inject(Router)
  message: string = '';
  errorMessage: string = '';

  ngOnInit(): void {
    this.fetchAllBooks();
  }

  private fetchAllBooks() {
    this.errorMessage = '';
    this.message = '';
    this.bookService.retrieveAllBooks({
      page: this.page,
      size: this.size
    }).subscribe({
      next: (response) => {
        this.booksResponse = response;
      },
      error: (error) => {
        console.error(error);
      }
    });
  }

  goToPage(page: number) {
    this.page = page;
    this.fetchAllBooks();
  }

  goToPreviousPage() {
    this.page--;
    this.fetchAllBooks();
  }

  goToFirstPage() {
    this.page = 0;
    this.fetchAllBooks();
  }

  goToNextPage() {
    this.page++;
    this.fetchAllBooks();
  }

  goToLastPage() {
    this.page = this.booksResponse.totalPages as number - 1;
    this.fetchAllBooks();
  }

  get isLastPage(): boolean {
    return  this.page === this.booksResponse.totalPages as number - 1;
  }

  borrowBook(book: BookResponse) {
    this.message = '';
    this.errorMessage = '';
    this.level = 'Success';
    this.bookService.borrowBook({
      'bookId': book.id as number // Cast the book id to number, may be undefined
    }).subscribe({
      next: () => {
        this.message = 'Book borrowed successfully and added to your list';
      },
      error: (error) => {
        this.errorMessage = error.error.error;  // retrieve 'error' field
        this.level = 'Error';
        this.message = error.error.error; // using only the message field
      }
    });
  }
}
