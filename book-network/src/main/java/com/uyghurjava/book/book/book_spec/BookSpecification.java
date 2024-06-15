package com.uyghurjava.book.book.book_spec;

import com.uyghurjava.book.book.Book;
import org.springframework.data.jpa.domain.Specification;

/**
 * This class is used to create specifications for the Book entity.
 * Specifications are used to create complex queries with the help of JPA Criteria API.
 */
public class BookSpecification {

    public static Specification<Book> withOwnerId(Integer ownerId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("owner").get("id"), ownerId);
    }

    private BookSpecification() {
        throw new IllegalStateException("BookSpecification class should not be instantiated!");
    }
}
