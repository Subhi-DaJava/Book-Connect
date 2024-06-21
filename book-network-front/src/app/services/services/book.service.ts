/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { approveReturnedBook } from '../fn/book/approve-returned-book';
import { ApproveReturnedBook$Params } from '../fn/book/approve-returned-book';
import { BookResponse } from '../models/book-response';
import { borrowBook } from '../fn/book/borrow-book';
import { BorrowBook$Params } from '../fn/book/borrow-book';
import { createBook } from '../fn/book/create-book';
import { CreateBook$Params } from '../fn/book/create-book';
import { PageResponseBookResponse } from '../models/page-response-book-response';
import { PageResponseBorrowedBooksResponse } from '../models/page-response-borrowed-books-response';
import { retrieveAllBooks } from '../fn/book/retrieve-all-books';
import { RetrieveAllBooks$Params } from '../fn/book/retrieve-all-books';
import { retrieveAllBooksByOwner } from '../fn/book/retrieve-all-books-by-owner';
import { RetrieveAllBooksByOwner$Params } from '../fn/book/retrieve-all-books-by-owner';
import { retrieveAllBorrowedBooksByUser } from '../fn/book/retrieve-all-borrowed-books-by-user';
import { RetrieveAllBorrowedBooksByUser$Params } from '../fn/book/retrieve-all-borrowed-books-by-user';
import { retrieveAllReturnedBooksByUser } from '../fn/book/retrieve-all-returned-books-by-user';
import { RetrieveAllReturnedBooksByUser$Params } from '../fn/book/retrieve-all-returned-books-by-user';
import { retrieveBookById } from '../fn/book/retrieve-book-by-id';
import { RetrieveBookById$Params } from '../fn/book/retrieve-book-by-id';
import { returnBook } from '../fn/book/return-book';
import { ReturnBook$Params } from '../fn/book/return-book';
import { updateBookArchivedStatus } from '../fn/book/update-book-archived-status';
import { UpdateBookArchivedStatus$Params } from '../fn/book/update-book-archived-status';
import { updateBookShareableStatus } from '../fn/book/update-book-shareable-status';
import { UpdateBookShareableStatus$Params } from '../fn/book/update-book-shareable-status';
import { uploadBookCoverImage } from '../fn/book/upload-book-cover-image';
import { UploadBookCoverImage$Params } from '../fn/book/upload-book-cover-image';


/**
 * Book Controller
 */
@Injectable({ providedIn: 'root' })
export class BookService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `retrieveAllBooks()` */
  static readonly RetrieveAllBooksPath = '/books';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `retrieveAllBooks()` instead.
   *
   * This method doesn't expect any request body.
   */
  retrieveAllBooks$Response(params?: RetrieveAllBooks$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseBookResponse>> {
    return retrieveAllBooks(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `retrieveAllBooks$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  retrieveAllBooks(params?: RetrieveAllBooks$Params, context?: HttpContext): Observable<PageResponseBookResponse> {
    return this.retrieveAllBooks$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseBookResponse>): PageResponseBookResponse => r.body)
    );
  }

  /** Path part for operation `createBook()` */
  static readonly CreateBookPath = '/books';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createBook()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createBook$Response(params: CreateBook$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return createBook(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `createBook$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createBook(params: CreateBook$Params, context?: HttpContext): Observable<number> {
    return this.createBook$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `uploadBookCoverImage()` */
  static readonly UploadBookCoverImagePath = '/books/cover/{bookId}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `uploadBookCoverImage()` instead.
   *
   * This method sends `multipart/form-data` and handles request body of type `multipart/form-data`.
   */
  uploadBookCoverImage$Response(params: UploadBookCoverImage$Params, context?: HttpContext): Observable<StrictHttpResponse<{
}>> {
    return uploadBookCoverImage(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `uploadBookCoverImage$Response()` instead.
   *
   * This method sends `multipart/form-data` and handles request body of type `multipart/form-data`.
   */
  uploadBookCoverImage(params: UploadBookCoverImage$Params, context?: HttpContext): Observable<{
}> {
    return this.uploadBookCoverImage$Response(params, context).pipe(
      map((r: StrictHttpResponse<{
}>): {
} => r.body)
    );
  }

  /** Path part for operation `borrowBook()` */
  static readonly BorrowBookPath = '/books/borrow/{bookId}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `borrowBook()` instead.
   *
   * This method doesn't expect any request body.
   */
  borrowBook$Response(params: BorrowBook$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return borrowBook(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `borrowBook$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  borrowBook(params: BorrowBook$Params, context?: HttpContext): Observable<number> {
    return this.borrowBook$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `updateBookShareableStatus()` */
  static readonly UpdateBookShareableStatusPath = '/books/shareable/{bookId}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `updateBookShareableStatus()` instead.
   *
   * This method doesn't expect any request body.
   */
  updateBookShareableStatus$Response(params: UpdateBookShareableStatus$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return updateBookShareableStatus(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `updateBookShareableStatus$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  updateBookShareableStatus(params: UpdateBookShareableStatus$Params, context?: HttpContext): Observable<number> {
    return this.updateBookShareableStatus$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `returnBook()` */
  static readonly ReturnBookPath = '/books/borrow/toReturnBorrowedBook/{bookId}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `returnBook()` instead.
   *
   * This method doesn't expect any request body.
   */
  returnBook$Response(params: ReturnBook$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return returnBook(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `returnBook$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  returnBook(params: ReturnBook$Params, context?: HttpContext): Observable<number> {
    return this.returnBook$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `approveReturnedBook()` */
  static readonly ApproveReturnedBookPath = '/books/borrow/toReturnBorrowedBook/approve/{bookId}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `approveReturnedBook()` instead.
   *
   * This method doesn't expect any request body.
   */
  approveReturnedBook$Response(params: ApproveReturnedBook$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return approveReturnedBook(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `approveReturnedBook$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  approveReturnedBook(params: ApproveReturnedBook$Params, context?: HttpContext): Observable<number> {
    return this.approveReturnedBook$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `updateBookArchivedStatus()` */
  static readonly UpdateBookArchivedStatusPath = '/books/archived/{bookId}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `updateBookArchivedStatus()` instead.
   *
   * This method doesn't expect any request body.
   */
  updateBookArchivedStatus$Response(params: UpdateBookArchivedStatus$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return updateBookArchivedStatus(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `updateBookArchivedStatus$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  updateBookArchivedStatus(params: UpdateBookArchivedStatus$Params, context?: HttpContext): Observable<number> {
    return this.updateBookArchivedStatus$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `retrieveBookById()` */
  static readonly RetrieveBookByIdPath = '/books/{bookId}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `retrieveBookById()` instead.
   *
   * This method doesn't expect any request body.
   */
  retrieveBookById$Response(params: RetrieveBookById$Params, context?: HttpContext): Observable<StrictHttpResponse<BookResponse>> {
    return retrieveBookById(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `retrieveBookById$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  retrieveBookById(params: RetrieveBookById$Params, context?: HttpContext): Observable<BookResponse> {
    return this.retrieveBookById$Response(params, context).pipe(
      map((r: StrictHttpResponse<BookResponse>): BookResponse => r.body)
    );
  }

  /** Path part for operation `retrieveAllReturnedBooksByUser()` */
  static readonly RetrieveAllReturnedBooksByUserPath = '/books/returnedBooks';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `retrieveAllReturnedBooksByUser()` instead.
   *
   * This method doesn't expect any request body.
   */
  retrieveAllReturnedBooksByUser$Response(params?: RetrieveAllReturnedBooksByUser$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseBorrowedBooksResponse>> {
    return retrieveAllReturnedBooksByUser(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `retrieveAllReturnedBooksByUser$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  retrieveAllReturnedBooksByUser(params?: RetrieveAllReturnedBooksByUser$Params, context?: HttpContext): Observable<PageResponseBorrowedBooksResponse> {
    return this.retrieveAllReturnedBooksByUser$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseBorrowedBooksResponse>): PageResponseBorrowedBooksResponse => r.body)
    );
  }

  /** Path part for operation `retrieveAllBooksByOwner()` */
  static readonly RetrieveAllBooksByOwnerPath = '/books/owner';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `retrieveAllBooksByOwner()` instead.
   *
   * This method doesn't expect any request body.
   */
  retrieveAllBooksByOwner$Response(params?: RetrieveAllBooksByOwner$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseBookResponse>> {
    return retrieveAllBooksByOwner(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `retrieveAllBooksByOwner$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  retrieveAllBooksByOwner(params?: RetrieveAllBooksByOwner$Params, context?: HttpContext): Observable<PageResponseBookResponse> {
    return this.retrieveAllBooksByOwner$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseBookResponse>): PageResponseBookResponse => r.body)
    );
  }

  /** Path part for operation `retrieveAllBorrowedBooksByUser()` */
  static readonly RetrieveAllBorrowedBooksByUserPath = '/books/borrowed';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `retrieveAllBorrowedBooksByUser()` instead.
   *
   * This method doesn't expect any request body.
   */
  retrieveAllBorrowedBooksByUser$Response(params?: RetrieveAllBorrowedBooksByUser$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseBorrowedBooksResponse>> {
    return retrieveAllBorrowedBooksByUser(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `retrieveAllBorrowedBooksByUser$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  retrieveAllBorrowedBooksByUser(params?: RetrieveAllBorrowedBooksByUser$Params, context?: HttpContext): Observable<PageResponseBorrowedBooksResponse> {
    return this.retrieveAllBorrowedBooksByUser$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseBorrowedBooksResponse>): PageResponseBorrowedBooksResponse => r.body)
    );
  }

}
