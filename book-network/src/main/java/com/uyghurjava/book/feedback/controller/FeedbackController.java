package com.uyghurjava.book.feedback.controller;

import com.uyghurjava.book.common.page_response.PageResponse;
import com.uyghurjava.book.feedback.dto.FeedbackRequest;
import com.uyghurjava.book.feedback.dto.FeedbackResponse;
import com.uyghurjava.book.feedback.service.FeedbackService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feedback")
@Tag(name = "FeedBack", description = "FeedBack API")
@Slf4j
public class FeedbackController {
    private final FeedbackService feedBackService;

    @PostMapping
    public ResponseEntity<Integer> createFeedBack(
            @Valid @RequestBody FeedbackRequest feedBackRequest,
            Authentication authenticatedUser) {

        return ResponseEntity.ok(feedBackService.createFeedback(feedBackRequest, authenticatedUser));
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<PageResponse<FeedbackResponse>> getFeedbacksByBook (
            @PathVariable Integer bookId,
            @RequestParam(defaultValue = "0", name = "page", required = false) int page,
            @RequestParam(defaultValue = "10", name = "size", required = false) int size,
            Authentication authenticatedUser ) {
        log.info("Getting feedbacks by book ID: {}, from getFeedbacksByBook of FeedbackController", bookId);
        return ResponseEntity.ok(feedBackService.getAllFeedbacksByBookId(bookId, page, size, authenticatedUser));
    }
}
