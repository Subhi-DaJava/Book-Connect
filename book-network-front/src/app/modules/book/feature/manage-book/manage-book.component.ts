import {Component, inject, OnInit} from '@angular/core';
import {NgForOf, NgIf} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {BookRequest} from "../../../../services/models/book-request";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {BookService} from "../../../../services/services/book.service";
import {BookResponse} from "../../../../services/models/book-response";

@Component({
  selector: 'app-manage-book',
  standalone: true,
  imports: [
    NgIf,
    NgForOf,
    FormsModule,
    RouterLink
  ],
  templateUrl: './manage-book.component.html',
  styleUrls: ['./manage-book.component.scss']
})
export class ManageBookComponent implements OnInit {

  bookService = inject(BookService);
  router = inject(Router);
  activatedRoute = inject(ActivatedRoute);

  ngOnInit(): void {
    const bookId = this.activatedRoute.snapshot.params['bookId'];
    if (bookId) {
      this.bookService.retrieveBookById({
        'bookId': bookId
      }).subscribe({
        next: (response: BookResponse) => {
          this.bookRequest = {
            id: response.id,
            title: response.title as string,
            authorName: response.authorName as string,
            isbn: response.isbn as string,
            synopsis: response.synopsis as string,
            shareable: response.isShareable
          }
          if(response.bookCover) {
            this.selectedPicture = 'data:image/jpg;png;base64,' + response.bookCover;
          }
        }
      });
    }
  }

  errorMessages: Array<string> = [];
  selectedPicture: string | undefined;
  selectedBookCover: any;

  bookRequest: BookRequest = {authorName: "", isbn: "", synopsis: "", title: ""};

  onFileSelectPicture(event: any) {
    this.selectedBookCover = event.target.files[0];
    console.log(this.selectedBookCover);
    if (this.selectedBookCover) {
      const reader = new FileReader();
      reader.onload = () => {
        this.selectedPicture = reader.result as string;
      };
      reader.readAsDataURL(this.selectedBookCover);
    }
  }

  saveBook() {
    this.bookService.createBook({
      body: this.bookRequest,
    }).subscribe({
      next: (bookId) => {
        this.bookService.uploadBookCoverImage({
          'bookId': bookId,
          body: {
            file: this.selectedBookCover
          }
        }).subscribe({
          next: () => {
            this.router.navigate(['/books/my-books']);
          }
        });
      },
      error: (error) => {
        this.errorMessages = error.error.validationErrors || ['An error occurred:', error.error.error];
      }
    });
  }


}
