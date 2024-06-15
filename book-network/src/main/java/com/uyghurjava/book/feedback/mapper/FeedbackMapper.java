package com.uyghurjava.book.feedback.mapper;

import com.uyghurjava.book.book.Book;
import com.uyghurjava.book.feedback.Feedback;
import com.uyghurjava.book.feedback.dto.FeedbackRequest;
import com.uyghurjava.book.feedback.dto.FeedbackResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FeedbackMapper {

    public Feedback toFeedBack(FeedbackRequest feedBackRequest) {
        log.info("Mapping FeedbackRequest to FeedBack, from toFeedBack method of FeedbackMapper");
        return Feedback.builder()
                .book(Book.builder()
                        .id(feedBackRequest.bookId())
                        //.archived()
                        .archived(false)
                        .shareable(false)
                        .build())
                .note(feedBackRequest.rating())
                .comment(feedBackRequest.feedback())
                .build();
    }

    public FeedbackResponse toFeedbackResponse(Feedback feedback, Integer userId) {
        return FeedbackResponse.builder()
                .comment(feedback.getComment())
                .note(feedback.getNote())
                .ownFeedback(feedback.getCreatedBy().equals(userId))
                .build();
    }
}
