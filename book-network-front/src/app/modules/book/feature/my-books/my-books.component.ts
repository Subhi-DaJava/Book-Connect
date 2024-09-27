import {Component, inject, OnInit} from '@angular/core';
import {BookDetailsComponent} from "../../components/book-details/book-details.component";
import {NgForOf, NgIf} from "@angular/common";
import {PageResponseBookResponse} from "../../../../services/models/page-response-book-response";
import {BookService} from "../../../../services/services/book.service";
import {Router, RouterLink} from "@angular/router";
import {BookResponse} from "../../../../services/models/book-response";

@Component({
  selector: 'app-my-books',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    BookDetailsComponent,
    RouterLink
  ],
  templateUrl: './my-books.component.html',
  styleUrl: './my-books.component.scss'
})
export class MyBooksComponent implements OnInit {
  page: number = 0;
  size: number = 3;
  booksResponse: PageResponseBookResponse = {} ;

  bookService = inject(BookService);
  router = inject(Router)

  ngOnInit(): void {
    this.fetchAllBooks();
  }

  private fetchAllBooks() {
    this.bookService.retrieveAllBooksByOwner({
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

  archiveBook(book: BookResponse) {
    this.bookService.updateBookArchivedStatus({
      'bookId': book.id as number
    }).subscribe({
      next: () => {
        book.isArchived = !book.isArchived;
      }
     });
  }

  shareBook(book: BookResponse) {
    this.bookService.updateBookShareableStatus({
      'bookId': book.id as number
    }).subscribe({
      next: () => {
        book.isShareable = !book.isShareable;
      }
    });
  }

  editBook(book: BookResponse) {
    this.router.navigate(['books', 'manage', book.id]);
  }
}
