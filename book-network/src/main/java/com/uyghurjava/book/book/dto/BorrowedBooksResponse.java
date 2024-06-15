package com.uyghurjava.book.book.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BorrowedBooksResponse {

   private Integer id;
   private String title;
   private String authorName;
   private String isbn;
   private double rate;
   private boolean returned;
   private boolean returnApproved;

}
