import {Component, inject, OnInit} from '@angular/core';
import {BookService, FeedBackService} from "../../../../services/services";
import {PageResponseBorrowedBooksResponse} from "../../../../services/models/page-response-borrowed-books-response";
import {BorrowedBooksResponse} from "../../../../services/models/borrowed-books-response";
import {NgForOf, NgIf} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {FeedbackRequest} from "../../../../services/models/feedback-request";
import {RatingComponent} from "../../components/rating/rating.component";

@Component({
  selector: 'app-borrowed-book-list',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    FormsModule,
    RatingComponent
  ],
  templateUrl: './borrowed-book-list.component.html',
  styleUrl: './borrowed-book-list.component.scss'
})
export class BorrowedBookListComponent implements OnInit {
  page = 0;
  size = 5;
  selectedBook: BorrowedBooksResponse | undefined = undefined;
  feedbackRequest: FeedbackRequest = { rating: 0, feedback: '', bookId: 0 };
  borrowedBooks: PageResponseBorrowedBooksResponse = {};

  bookService = inject(BookService);
  feedbackService = inject(FeedBackService);

  returnedBorrowedBook(book: BorrowedBooksResponse) {
    this.selectedBook = book;
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

  returnBook(feedback: boolean) {
    this.bookService.returnBook({
      'bookId': this.selectedBook?.id as number
    }).subscribe({
      next:() => {
        if(feedback) {
          this.giveFeedback();
        }
        this.selectedBook = undefined;
        this.findAllBorrowedBooks();
      }
    });
  }

  private giveFeedback() {
    this.feedbackRequest.bookId = this.selectedBook?.id as number;
    this.feedbackService.createFeedBack({
      body: this.feedbackRequest
    }).subscribe({
      next: () => {
        this.feedbackRequest = { bookId: 0, feedback: '', rating: 0 };
      }
    });
  }
}

