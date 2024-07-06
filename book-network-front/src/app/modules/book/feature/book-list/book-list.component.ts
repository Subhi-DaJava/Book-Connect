import {Component, inject, OnInit} from '@angular/core';
import {BookService} from "../../../../services/services/book.service";
import {Router} from "@angular/router";
import {PageResponseBookResponse} from "../../../../services/models/page-response-book-response";
import {NgForOf} from "@angular/common";
import {BookDetailsComponent} from "../../components/book-details/book-details.component";

@Component({
  selector: 'app-book-list',
  standalone: true,
  imports: [
    NgForOf,
    BookDetailsComponent
  ],
  templateUrl: './book-list.component.html',
  styleUrl: './book-list.component.scss'
})
export class BookListComponent implements OnInit {
  page: number = 0;
  size: number = 8;
  booksResponse: PageResponseBookResponse = {};


   bookService = inject(BookService);
   router = inject(Router)


  ngOnInit(): void {
    this.fetchAllBooks();
  }

  private fetchAllBooks() {
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
}
