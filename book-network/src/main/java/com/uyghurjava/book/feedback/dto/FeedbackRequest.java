package com.uyghurjava.book.feedback.dto;

import jakarta.validation.constraints.*;

public record FeedbackRequest(

        @NotNull(message = "Book ID is required")
        Integer bookId,

        @Positive(message = "Rating must be positive")
        @Min(value = 0, message = "Rating must be between 0 and 5")
        @Max(value = 5, message = "Rating must be between 0 and 5")
        Double rating,

//        @NotNull(message = "Feedback content is required")
//        @NotEmpty(message = "Feedback content is required")
//        @NotBlank(message = "Feedback content is required")
        String feedback
) {
}
