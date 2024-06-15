package com.uyghurjava.book.book.service;

import com.uyghurjava.book.book.dto.BookRequest;
import com.uyghurjava.book.book.dto.BookResponse;
import com.uyghurjava.book.book.dto.BorrowedBooksResponse;
import com.uyghurjava.book.common.page_response.PageResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

public interface BookService {
    Integer createBook(BookRequest bookRequest, Authentication authenticatedUser);

    BookResponse retrieveBookById(Integer bookId);

    PageResponse<BookResponse> retrieveAllBooks(int page, int size, Authentication authenticatedUser);

    PageResponse<BookResponse> retrieveAllBooksByOwner(int page, int size, Authentication authenticatedUser);

    PageResponse<BorrowedBooksResponse> retrieveAllBorrowedBooksByUser(int page, int size, Authentication authenticatedUser);

    PageResponse<BorrowedBooksResponse> retrieveAllReturnedBooksByUser(int page, int size, Authentication authenticatedUser);

    Integer updateBookShareableStatus(Integer bookId, Authentication authenticatedUser);

    Integer updateBookArchivedStatus(Integer bookId, Authentication authenticatedUser);

    Integer borrowBook(Integer bookId, Authentication authenticatedUser);

    Integer returnBorrowedBook(Integer bookId, Authentication authenticatedUser);

    Integer approveReturnBorrowedBook(Integer bookId, Authentication authenticatedUser);

    void uploadBookCoverImage(Integer bookId, MultipartFile file, Authentication authenticatedUser);
}
