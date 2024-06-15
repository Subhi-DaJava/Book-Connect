package com.uyghurjava.book.feedback.repository;

import com.uyghurjava.book.feedback.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FeedBackRepository extends JpaRepository<Feedback, Integer> {
    // ORDER BY f.createdAt DESC
    @Query(
        """
        SELECT f
            FROM Feedback f
            WHERE f.book.id = :bookId
        """)
    Page<Feedback> findAllByBookId(Integer bookId, Pageable pageable);
}
