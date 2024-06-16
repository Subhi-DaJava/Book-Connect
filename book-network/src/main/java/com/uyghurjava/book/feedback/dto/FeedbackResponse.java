package com.uyghurjava.book.feedback.dto;

import lombok.Builder;

@Builder
public record FeedbackResponse(
        Double note,
        String comment,
        boolean ownFeedback
) {
}
