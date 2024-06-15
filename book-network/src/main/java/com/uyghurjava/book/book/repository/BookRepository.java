package com.uyghurjava.book.book.repository;

import com.uyghurjava.book.book.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer>, JpaSpecificationExecutor<Book> {

    // JPQL query to get all books that are not archived and shareable, and not owned by the user, excluding the user's own books
    @Query("SELECT b FROM Book b WHERE b.archived = FALSE AND b.shareable = TRUE AND b.owner.id != :userId")
    Page<Book> findAllDisplayedBooks(Integer userId, Pageable pageable);

    Optional<Book> findByIsbn(String isbn);
}
