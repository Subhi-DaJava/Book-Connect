package com.uyghurjava.book.feedback.service;

import com.uyghurjava.book.book.Book;
import com.uyghurjava.book.book.repository.BookRepository;
import com.uyghurjava.book.common.page_response.PageResponse;
import com.uyghurjava.book.exception.OperationNotPermittedException;
import com.uyghurjava.book.feedback.Feedback;
import com.uyghurjava.book.feedback.dto.FeedbackRequest;
import com.uyghurjava.book.feedback.dto.FeedbackResponse;
import com.uyghurjava.book.feedback.mapper.FeedbackMapper;
import com.uyghurjava.book.feedback.repository.FeedBackRepository;
import com.uyghurjava.book.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class IFeedbackService implements FeedbackService {
    private final BookRepository bookRepository;
    private final FeedbackMapper feedbackMapper;
    private final FeedBackRepository feedBackRepository;

    @Override
    public Integer createFeedback(FeedbackRequest feedBackRequest, Authentication authenticatedUser) {
        Book book = bookRepository.findById(feedBackRequest.bookId())
                .orElseThrow(() -> new EntityNotFoundException("Book not found with ID:{%d} ".formatted(feedBackRequest.bookId())));

        if(book.isArchived() || !book.isShareable())
            throw new OperationNotPermittedException("Feedback is not allowed for this archived or non-shareable book");

        User userAuthenticated = (User) authenticatedUser.getPrincipal();
        if(userAuthenticated.getId().equals(book.getOwner().getId()))
            throw new OperationNotPermittedException("Feedback is not allowed for your own book");

        // Save feedback to database
        Feedback feedBack = feedbackMapper.toFeedBack(feedBackRequest);

        Feedback savedFeedback = feedBackRepository.save(feedBack);

        log.info("Feedback created with ID: {}, from createFeedback method of IFeedbackService", savedFeedback.getId());
        return savedFeedback.getId();
    }

    @Override
    public PageResponse<FeedbackResponse> getAllFeedbacksByBookId(
            Integer bookId,
            int page, int size,
            Authentication authenticatedUser) {

        Pageable pageable = PageRequest.of(page, size);

        User userAuthenticated = (User) authenticatedUser.getPrincipal();

        Page<Feedback> feedbacks = feedBackRepository.findAllByBookId(bookId, pageable);

        List<FeedbackResponse> feedbackResponses = feedbacks.stream()
                .map(feedback -> feedbackMapper.toFeedbackResponse(feedback, userAuthenticated.getId()))
                .toList();

        return new PageResponse<>(
                feedbackResponses,
                feedbacks.getNumber(),
                feedbacks.getSize(),
                feedbacks.getTotalElements(),
                feedbacks.getTotalPages(),
                feedbacks.isFirst(),
                feedbacks.isLast()
        );
    }
}
