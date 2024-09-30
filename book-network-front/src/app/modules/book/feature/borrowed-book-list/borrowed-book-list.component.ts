import {Component, inject, OnInit} from '@angular/core';
import {BookService} from "../../../../services/services";
import {PageResponseBorrowedBooksResponse} from "../../../../services/models/page-response-borrowed-books-response";
import {BorrowedBooksResponse} from "../../../../services/models/borrowed-books-response";
import {NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-borrowed-book-list',
  standalone: true,
  imports: [
    NgForOf,
    NgIf
  ],
  templateUrl: './borrowed-book-list.component.html',
  styleUrl: './borrowed-book-list.component.scss'
})
export class BorrowedBookListComponent implements OnInit {
  page = 0;
  size = 5;

  bookService = inject(BookService);

  borrowedBooks: PageResponseBorrowedBooksResponse = {}

  returnedBorrowedBook(book: BorrowedBooksResponse) {

  }

  ngOnInit(): void {
    this.findAllBorrowedBooks();
  }

private findAllBorrowedBooks() {
    this.bookService.retrieveAllBorrowedBooksByUser({
      page: this.page,
      size: this.size
    }).subscribe({
      next: (response: PageResponseBorrowedBooksResponse) => {
        this.borrowedBooks = response;
      }
    });
  }

  goToPage(page: number) {
    this.page = page;
    this.findAllBorrowedBooks();
  }

  goToPreviousPage() {
    this.page--;
    this.findAllBorrowedBooks();
  }

  goToFirstPage() {
    this.page = 0;
    this.findAllBorrowedBooks();
  }

  goToNextPage() {
    this.page++;
    this.findAllBorrowedBooks();
  }

  goToLastPage() {
    this.page = this.borrowedBooks.totalPages as number - 1;
    this.findAllBorrowedBooks();
  }

  get isLastPage(): boolean {
    return  this.page === this.borrowedBooks.totalPages as number - 1;
  }
}

