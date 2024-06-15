package com.uyghurjava.book.book.mapper;

import com.uyghurjava.book.book.Book;
import com.uyghurjava.book.book.dto.BookRequest;
import com.uyghurjava.book.book.dto.BookResponse;
import com.uyghurjava.book.book.dto.BorrowedBooksResponse;
import com.uyghurjava.book.file.FileUtils;
import com.uyghurjava.book.history.BookTransactionHistory;
import org.springframework.stereotype.Service;

@Service
public class BookMapper {

    public Book toBook(BookRequest bookRequest) {
        return Book.builder()
                .id(bookRequest.id())
                .bookTitle(bookRequest.title())
                .authorName(bookRequest.authorName())
                .isbn(bookRequest.isbn())
                .synopsis(bookRequest.synopsis())
                .shareable(bookRequest.shareable())
                .archived(false)
                .build();
    }

    public BookResponse toBookResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getBookTitle())
                .authorName(book.getAuthorName())
                .isbn(book.getIsbn())
                .synopsis(book.getSynopsis())
                .isShareable(book.isShareable())
                .isArchived(book.isArchived())
                .owner(book.getOwner().getFullName())
                .averageRate(book.getAverageRate())
                .bookCover(FileUtils.readFileFromLocation(book.getBookCover()))
                .build();
    }

    public BorrowedBooksResponse toBorrowedBooksResponse(BookTransactionHistory history) {
        return BorrowedBooksResponse.builder()
                .id(history.getBook().getId())
                .title(history.getBook().getBookTitle())
                .authorName(history.getBook().getAuthorName())
                .isbn(history.getBook().getIsbn())
                .rate(history.getBook().getAverageRate())
                .returned(history.isReturned())
                .returnApproved(history.isReturnApproved())
                .build();
    }
}
