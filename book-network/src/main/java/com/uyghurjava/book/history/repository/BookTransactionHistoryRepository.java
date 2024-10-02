package com.uyghurjava.book.history.repository;

import com.uyghurjava.book.history.BookTransactionHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookTransactionHistoryRepository extends JpaRepository<BookTransactionHistory, Integer> {

    @Query("SELECT transactionHistories FROM BookTransactionHistory transactionHistories WHERE transactionHistories.user.id = :userId")
    Page<BookTransactionHistory> findAllBorrowedBooksByUser(Integer userId, Pageable pageable);

    @Query(
            """
                SELECT transactionHistories
                FROM BookTransactionHistory transactionHistories
                WHERE transactionHistories.createdBy = :userId
                AND transactionHistories.returned = true
             """)
    Page<BookTransactionHistory> findAllReturnedBooksByUser(Integer userId, Pageable pageable);

    // Check if the user already borrows the book
    @Query("""
            SELECT
            (count(*) > 0) AS isBorrowed
            FROM BookTransactionHistory transactionHistories
            WHERE transactionHistories.user.id = :userId
            AND transactionHistories.book.id = :bookId
            AND transactionHistories.returnApproved = false
            """)
    boolean isAlreadyBorrowedByUser(Integer bookId, Integer userId);


    @Query("""
        SELECT transactionHistories FROM BookTransactionHistory transactionHistories
        WHERE transactionHistories.book.id = :bookId
        AND transactionHistories.user.id = :userId
        AND transactionHistories.returned = false
        AND transactionHistories.returnApproved = false
        """)
    Optional<BookTransactionHistory> findByBookIdAndUserId(Integer bookId, Integer userId);

    // Check if the user already borrows the book, the book should be returned first
    @Query("""
        SELECT transactionHistories FROM BookTransactionHistory transactionHistories
        WHERE transactionHistories.book.id = :bookId
        AND transactionHistories.book.owner.id = :userId
        AND transactionHistories.returned = true
        AND transactionHistories.returnApproved = false
        """)
    Optional<BookTransactionHistory> findByBookIdAndOwnerId(Integer bookId, Integer userId);
}
