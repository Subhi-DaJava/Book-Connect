package com.uyghurjava.book.feedback.service;

import com.uyghurjava.book.common.page_response.PageResponse;
import com.uyghurjava.book.feedback.dto.FeedbackRequest;
import com.uyghurjava.book.feedback.dto.FeedbackResponse;
import org.springframework.security.core.Authentication;

public interface FeedbackService {
    Integer createFeedback(FeedbackRequest feedBackRequest, Authentication authenticatedUser);

    PageResponse<FeedbackResponse> getAllFeedbacksByBookId(Integer bookId, int page, int size, Authentication authenticatedUser);
}
