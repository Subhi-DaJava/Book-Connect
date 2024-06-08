package com.uyghurjava.book.book;

import com.uyghurjava.book.common.ParentEntity;
import com.uyghurjava.book.feedback.FeedBack;
import com.uyghurjava.book.history.BookTransactionHistory;
import com.uyghurjava.book.user.User;
import jakarta.persistence.*;
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
    private String isbn;
    private String synopsis;
    private String bookCover;
    private boolean isArchived;
    private boolean isShareable;

    @ManyToOne
    @JoinColumn(name = "onwer_id")
    private User owner;

    @OneToMany(mappedBy = "book")
    private List<FeedBack> feedBacks;

    @OneToMany(mappedBy = "book")
    private List<BookTransactionHistory> booksHistories;

}
