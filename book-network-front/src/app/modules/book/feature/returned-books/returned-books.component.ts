import {Component, inject, OnInit} from '@angular/core';
import {NgForOf, NgIf} from "@angular/common";
import {BorrowedBooksResponse} from "../../../../services/models/borrowed-books-response";
import {PageResponseBorrowedBooksResponse} from "../../../../services/models/page-response-borrowed-books-response";
import {BookService} from "../../../../services/services/book.service";

@Component({
  selector: 'app-return-books',
  standalone: true,
  imports: [
    NgForOf,
    NgIf
  ],
  templateUrl: './returned-books.component.html',
  styleUrl: './returned-books.component.scss'
})
export class ReturnedBooksComponent implements OnInit {
  page = 0;
  size = 5;

  returnedBooks: PageResponseBorrowedBooksResponse = {};

  bookService = inject(BookService);

  message: string = '';
  level: string = 'Success';

  ngOnInit(): void {
    this.findAllReturnedBooks();
  }

  private findAllReturnedBooks() {
    this.bookService.retrieveAllReturnedBooksByUser({
      page: this.page,
      size: this.size
    }).subscribe({
      next: (response: PageResponseBorrowedBooksResponse) => {
        this.returnedBooks = response;
      }
    });
  }

  goToPage(page: number) {
    this.page = page;
    this.findAllReturnedBooks();
  }

  goToPreviousPage() {
    this.page--;
    this.findAllReturnedBooks();
  }

  goToFirstPage() {
    this.page = 0;
    this.findAllReturnedBooks();
  }

  goToNextPage() {
    this.page++;
    this.findAllReturnedBooks();
  }

  goToLastPage() {
    this.page = this.returnedBooks.totalPages as number - 1;
    this.findAllReturnedBooks();
  }

  get isLastPage(): boolean {
    return  this.page === this.returnedBooks.totalPages as number - 1;
  }

  approveBookReturn(book: BorrowedBooksResponse) {
    if (!book.returned) {
      this.level = 'Error';
      this.message = 'The Book is not returned yet!';
      return;
    }
    this.bookService.approveReturnedBook({
      'bookId': book.id as number
    }).subscribe({
      next: () => {
        this.level = 'Success';
        this.message = 'Book returned has been successfully approved !';
        this.findAllReturnedBooks();
      }
    });

  }
}
