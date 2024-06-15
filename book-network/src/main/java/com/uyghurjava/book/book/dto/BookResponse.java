package com.uyghurjava.book.book.dto;

import lombok.Builder;

@Builder
public record BookResponse (
        Integer id,
        String title,
        String authorName,
        String isbn,
        String synopsis,
        boolean isShareable,
        boolean isArchived,
        String owner,
        byte[] bookCover,
        double averageRate
) {
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        BookResponse book = (BookResponse) o;
//        return Objects.equals(isbn, book.isbn) &&
//                isShareable == book.isShareable &&
//                isArchived == book.isArchived &&
//                Double.compare(book.averageRate, averageRate) == 0 &&
//                id.equals(book.id) &&
//                title.equals(book.title) &&
//                authorName.equals(book.authorName) &&
//                synopsis.equals(book.synopsis) &&
//                owner.equals(book.owner) &&
//                Arrays.equals(bookCover, book.bookCover);
//    }
//
//    @Override
//    public int hashCode() {
//        int result = Objects.hash(isbn);
//        result = 31 * result + Arrays.hashCode(bookCover);
//        return result;
//    }
//
//    @Override
//    public String toString() {
//        return "BookResponse{" +
//                "BookCouver=" + Arrays.toString(bookCover) +
//                ", isbn=" + isbn +
//                '}';
//    }
}
