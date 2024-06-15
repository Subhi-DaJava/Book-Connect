package com.uyghurjava.book.book.controller;

import com.uyghurjava.book.book.dto.BookRequest;
import com.uyghurjava.book.book.dto.BookResponse;
import com.uyghurjava.book.book.dto.BorrowedBooksResponse;
import com.uyghurjava.book.common.page_response.PageResponse;
import com.uyghurjava.book.book.service.BookService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequestMapping("books")
@Tag(name = "Book", description = "Book Controller")
@RequiredArgsConstructor
@RestController
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<Integer> createBook(
            @Valid @RequestBody BookRequest bookRequest,
            Authentication authenticatedUser) { //  how to get the authenticated user
        log.info("Creating a new book: {}, from method createBook of BookController", bookRequest);

        return ResponseEntity.ok(bookService.createBook(bookRequest, authenticatedUser));
    }

    @GetMapping("{bookId}")
    public ResponseEntity<BookResponse> retrieveBookById(
            @PathVariable("bookId") Integer bookId) {
        log.info("Retrieving book by id: {}, from method retrieveBookById of BookController", bookId);

        return ResponseEntity.ok(bookService.retrieveBookById(bookId));
    }

    /**
     *  Retrieve all books with pagination support (page, size) and authenticated user
     * @param page
     * @param size
     * @param authenticatedUser
     * @return
     */

    @GetMapping
    public ResponseEntity<PageResponse<BookResponse>> retrieveAllBooks(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page, // 0-based index page
            @RequestParam(value = "size", defaultValue = "10", required = false) int size, // number of items per page
            Authentication authenticatedUser) {

        PageResponse<BookResponse> allBooks = bookService.retrieveAllBooks(page, size, authenticatedUser);
        log.info("Retrieving all books: {}, from method retrieveAllBooks of BookController", allBooks);
        return ResponseEntity.ok(allBooks);
    }


    @GetMapping("/owner")
    public ResponseEntity<PageResponse<BookResponse>> retrieveAllBooksByOwner(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page, // 0-based index page
            @RequestParam(value = "size", defaultValue = "10", required = false) int size, // number of items per page
            Authentication authenticatedUser) {
        log.info("Retrieving all books by owner, from method retrieveAllBooksByOwner of BookController");

        return ResponseEntity.ok(bookService.retrieveAllBooksByOwner(page, size, authenticatedUser));
    }

    @GetMapping("/borrowed")
    public ResponseEntity<PageResponse<BorrowedBooksResponse>> retrieveAllBorrowedBooksByUser(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page, // 0-based index page
            @RequestParam(value = "size", defaultValue = "10", required = false) int size, // number of items per page
            Authentication authenticatedUser) {
        log.info("Retrieving all borrowed books by user, from method retrieveAllBorrowedBooksByUser of BookController");

        return ResponseEntity.ok(bookService.retrieveAllBorrowedBooksByUser(page, size, authenticatedUser));
    }

    @GetMapping("/returnedBooks")
    public ResponseEntity<PageResponse<BorrowedBooksResponse>> retrieveAllReturnedBooksByUser(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page, // 0-based index page
            @RequestParam(value = "size", defaultValue = "10", required = false) int size, // number of items per page
            Authentication authenticatedUser) {
        log.info("Retrieving all returned books by user, from method retrieveAllReturnedBooksByUser of BookController");

        return ResponseEntity.ok(bookService.retrieveAllReturnedBooksByUser(page, size, authenticatedUser));
    }

    @PatchMapping("/shareable/{bookId}")
    public ResponseEntity<Integer> updateBookShareableStatus(
            @PathVariable("bookId") Integer bookId,
            Authentication authenticatedUser) {
        log.info("Updating book shareable status, from method updateBookShareable of BookController");

        return ResponseEntity.ok(bookService.updateBookShareableStatus(bookId, authenticatedUser));
    }

    @PatchMapping("/archived/{bookId}")
    public ResponseEntity<Integer> updateBookArchivedStatus(
            @PathVariable("bookId") Integer bookId,
            Authentication authenticatedUser) {
        log.info("Updating book archived status, from method updateArchivedBook of BookController");

        return ResponseEntity.ok(bookService.updateBookArchivedStatus(bookId, authenticatedUser));
    }

    @PostMapping("/borrow/{bookId}")
    public ResponseEntity<Integer> borrowBook(
            @PathVariable("bookId") Integer bookId,
            Authentication authenticatedUser) {
        log.info("Borrowing book, from method borrowBook of BookController");

        return ResponseEntity.ok(bookService.borrowBook(bookId, authenticatedUser));
    }

    @PatchMapping("/borrow/toReturnBorrowedBook/{bookId}")
    public ResponseEntity<Integer> returnBook(
            @PathVariable("bookId") Integer bookId,
            Authentication authenticatedUser) {
        log.info("Returning borrowed book, from method returnBook of BookController");

        return ResponseEntity.ok(bookService.returnBorrowedBook(bookId, authenticatedUser));
    }

    @PatchMapping("/borrow/toReturnBorrowedBook/approve/{bookId}")
    public ResponseEntity<Integer> approveReturnedBook(
            @PathVariable("bookId") Integer bookId,
            Authentication authenticatedUser) {
        log.info("Approving return of borrowed book, from method approveReturnBook of BookController");

        return ResponseEntity.ok(bookService.approveReturnBorrowedBook(bookId, authenticatedUser));
    }

    @PostMapping(value = "/cover/{bookId}", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadBookCoverImage (
            @PathVariable("bookId") Integer bookId,
            @RequestPart("file") MultipartFile file,
            @Parameter()
            Authentication authenticatedUser) {

       bookService.uploadBookCoverImage(bookId, file, authenticatedUser);

        return ResponseEntity.accepted().build();
    }

}
