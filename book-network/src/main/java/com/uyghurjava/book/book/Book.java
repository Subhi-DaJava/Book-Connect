package com.uyghurjava.book.book;

import com.uyghurjava.book.common.ParentEntity;
import com.uyghurjava.book.feedback.Feedback;
import com.uyghurjava.book.history.BookTransactionHistory;
import com.uyghurjava.book.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Book extends ParentEntity {

    private String bookTitle;
    private String authorName;

    @Column(unique = true, nullable = false)
    @Pattern(regexp = "\\d{3}-\\d-\\d{3}-\\d{5}-\\d", message = "ISBN format should be like '978-0-596-52068-7'")
    private String isbn;
    private String synopsis;
    private String bookCover; // file's path
    private boolean archived;
    private boolean shareable;

    @ManyToOne
    @JoinColumn(name = "onwer_id")
    private User owner;

    @OneToMany(mappedBy = "book")
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "book")
    private List<BookTransactionHistory> booksHistories;

    @Transient
    public double getAverageRate() {
        if(feedbacks == null || feedbacks.isEmpty()) {
            return 0;
        }

        var average = feedbacks
                .stream()
                .mapToDouble(Feedback::getNote)
                .average()
                .orElse(0.0);

        // round to 0 decimal places, 3.4 = 3 and 3.5 = 4
        return Math.round(average * 10.0) / 10.0;
    }
}
