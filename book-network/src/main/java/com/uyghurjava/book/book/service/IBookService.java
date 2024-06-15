package com.uyghurjava.book.book.service;

import com.uyghurjava.book.book.Book;
import com.uyghurjava.book.book.dto.BookRequest;
import com.uyghurjava.book.book.dto.BookResponse;
import com.uyghurjava.book.book.dto.BorrowedBooksResponse;
import com.uyghurjava.book.book.mapper.BookMapper;
import com.uyghurjava.book.book.repository.BookRepository;
import com.uyghurjava.book.common.page_response.PageResponse;
import com.uyghurjava.book.exception.OperationNotPermittedException;
import com.uyghurjava.book.file.FileStorageService;
import com.uyghurjava.book.history.BookTransactionHistory;
import com.uyghurjava.book.history.repository.BookTransactionHistoryRepository;
import com.uyghurjava.book.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static com.uyghurjava.book.book.book_spec.BookSpecification.withOwnerId;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class IBookService implements BookService {
    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final BookTransactionHistoryRepository bookTransactionHistoryRepository;
    private final FileStorageService fileStorageService;

    private static final String BOOK_CREATED_AT = "createdAt";

    @Override
    public Integer createBook(BookRequest bookRequest, Authentication authenticatedUser) {
        // User implements UserDetails interface & Principal interface, so we can cast it to User
        User user = (User) authenticatedUser.getPrincipal();
        Optional<Book> checkExistingBook = bookRepository.findByIsbn(bookRequest.isbn());
        if(checkExistingBook.isPresent()) {
            log.error("Book with ISBN: {} already exists", bookRequest.isbn());
            throw new OperationNotPermittedException("Book with given ISBN already exists, create book operation is not permitted");
        }
        Book book = bookMapper.toBook(bookRequest);

        book.setOwner(user);
        Book bookSaved = bookRepository.save(book);

        log.info("Book created: {}, from method createBook of BookService", bookSaved.getId());

        return bookSaved.getId();
    }

    @Override
    public BookResponse retrieveBookById(Integer bookId) {
        BookResponse book = bookRepository.findById(bookId)
                .map(bookMapper::toBookResponse)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with given Id={%s}".formatted(bookId)));
        log.info("Retrieving book by id: {}, from method retrieveBookById of BookService", bookId);
        return book;
    }

    @Override
    public PageResponse<BookResponse> retrieveAllBooks(int page, int size, Authentication authenticatedUser) {
        // User implements UserDetails interface & Principal interface, so we can cast it to User
        User user = (User) authenticatedUser.getPrincipal();

        Pageable pageable = PageRequest.of(page, size, Sort.by(BOOK_CREATED_AT).descending()); // sort by createdAt in descending order, newest first
        Page<Book> books = bookRepository.findAllDisplayedBooks(user.getId(), pageable);
        List<BookResponse> bookResponses = books
                .stream()
                .map(bookMapper::toBookResponse)
                .toList();

        log.info("Retrieving all books, from method retrieveAllBooks of BookService");
        // return a PageResponse object, which contains the list of BookResponse objects and other pagination information
        return new PageResponse<>(
                bookResponses,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast()
        );
    }

    @Override
    public PageResponse<BookResponse> retrieveAllBooksByOwner(int page, int size, Authentication authenticatedUser) {
        // User implements UserDetails interface & Principal interface, so we can cast it to User
        User connectedUserAndBookOwner = (User) authenticatedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by(BOOK_CREATED_AT).descending()); // sort by createdAt in descending order, newest first

        // get all books by owner, Specification<Book> its method withOwnerId(Integer ownerId) is used to filter books by owner
        Page<Book> books = bookRepository.findAll(withOwnerId(connectedUserAndBookOwner.getId()), pageable);

        List<BookResponse> bookResponses = books
                .stream()
                .map(bookMapper::toBookResponse)
                .toList();

        log.info("Retrieving all books by owner, from method retrieveAllBooksByOwner of BookService");
        return new PageResponse<>(
                bookResponses,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast()
        );
    }

    @Override
    public PageResponse<BorrowedBooksResponse> retrieveAllBorrowedBooksByUser(int page, int size, Authentication authenticatedUser) {
        // User implements UserDetails interface & Principal interface, so we can cast it to User
        User connectedUser = (User) authenticatedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by(BOOK_CREATED_AT).descending()); // sort by createdAt in descending order, newest first

        Page<BookTransactionHistory> allBorrowedBooks = bookTransactionHistoryRepository.findAllBorrowedBooksByUser(connectedUser.getId(), pageable);

        List<BorrowedBooksResponse> bookResponses = allBorrowedBooks
                .stream()
                .map(bookMapper::toBorrowedBooksResponse)
                .toList();

        log.info("Retrieving all borrowed books by user, from method retrieveAllBorrowedBooksByUser of BookService");

        return new PageResponse<>(
                bookResponses,
                allBorrowedBooks.getNumber(),
                allBorrowedBooks.getSize(),
                allBorrowedBooks.getTotalElements(),
                allBorrowedBooks.getTotalPages(),
                allBorrowedBooks.isFirst(),
                allBorrowedBooks.isLast()
        );
    }

    @Override
    public PageResponse<BorrowedBooksResponse> retrieveAllReturnedBooksByUser(int page, int size, Authentication authenticatedUser) {
        // User implements UserDetails interface & Principal interface, so we can cast it to User
        User connectedUser = (User) authenticatedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by(BOOK_CREATED_AT).descending()); // sort by createdAt in descending order, newest first

        Page<BookTransactionHistory> allReturnedBooks = bookTransactionHistoryRepository.findAllReturnedBooksByUser(connectedUser.getId(), pageable);

        List<BorrowedBooksResponse> bookResponses = allReturnedBooks
                .stream()
                .map(bookMapper::toBorrowedBooksResponse)
                .toList();

        log.info("Retrieving all returned books by user, from method retrieveAllReturnedBooksByUser of BookService");
        return new PageResponse<>(
                bookResponses,
                allReturnedBooks.getNumber(),
                allReturnedBooks.getSize(),
                allReturnedBooks.getTotalElements(),
                allReturnedBooks.getTotalPages(),
                allReturnedBooks.isFirst(),
                allReturnedBooks.isLast()
        );
    }

    @Override
    public Integer updateBookShareableStatus(Integer bookId, Authentication authenticatedUser) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with given Id={%s}".formatted(bookId)));
        User connectedUser = (User) authenticatedUser.getPrincipal();

        if (!book.getOwner().getId().equals(connectedUser.getId())) {
            log.error("User with id: {} is not the owner of the book with id: {}", connectedUser.getId(), bookId);
            throw new OperationNotPermittedException("User is not the owner of the book, shareable book operation is not permitted");
        }

        book.setShareable(!book.isShareable());
        bookRepository.save(book);
        log.info("Book shareable status updated: {}, from method updateBookShareableStatus of BookService", book.isShareable());
        return bookId;
    }

    @Override
    public Integer updateBookArchivedStatus(Integer bookId, Authentication authenticatedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with given Id={%s}".formatted(bookId)));
        User connectedUser = (User) authenticatedUser.getPrincipal();

        if (!book.getOwner().getId().equals(connectedUser.getId())) {
            log.error("User with id: {} is not the owner of the book with id: {}", connectedUser.getId(), bookId);
            throw new OperationNotPermittedException("User is not the owner of the book, archived book operation is not permitted");
        }

        book.setArchived(!book.isArchived());
        bookRepository.save(book);
        log.info("Book archived status updated: {}, from method updateBookArchivedStatus of BookService", book.isShareable());
        return bookId;
    }

    @Override
    public Integer borrowBook(Integer bookId, Authentication authenticatedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with given Id={%s}".formatted(bookId)));

        if(book.isArchived() || !book.isShareable()) {
            log.error("Book with id: {} is not shareable or archived", bookId);
            throw new OperationNotPermittedException("The Requested Book is not shareable or archived, borrow book operation is not permitted");
        }

        User connectedUser = (User) authenticatedUser.getPrincipal();

        if(book.getOwner().getId().equals(connectedUser.getId())) {
            log.error("User with id: {} is the owner of the book with id: {}", connectedUser.getId(), bookId);
            throw new OperationNotPermittedException("User is the owner of the book, the requested book could not be borrowed by the owner, operation is not permitted");
        }

        final boolean isAlreadyBorrowed = bookTransactionHistoryRepository.isAlreadyBorrowedByUser(bookId, connectedUser.getId());
        if(isAlreadyBorrowed) {
            log.error("User with id: {} already borrowed the book with id: {}", connectedUser.getId(), bookId);
            throw new OperationNotPermittedException("User already borrowed the book, borrow book operation is not permitted");
        }

        BookTransactionHistory bookTransactionHistory = BookTransactionHistory.builder()
                .book(book)
                .user(connectedUser)
                .returned(false)
                .returnApproved(false)
                .build();
        Integer transactionId = bookTransactionHistoryRepository.save(bookTransactionHistory).getId();
        log.info("Book borrowed: {}, from method borrowBook of BookService", bookId);

        return transactionId;
    }

    @Override
    public Integer returnBorrowedBook(Integer bookId, Authentication authenticatedUser) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with given Id={%s}".formatted(bookId)));

        if (book.isArchived() || !book.isShareable()) {
            log.error("Book with id: {} is not shareable or archived", bookId);
            throw new OperationNotPermittedException("The Requested Book is not shareable or archived, return borrowed book operation is not permitted");
        }

        User connectedUser = (User) authenticatedUser.getPrincipal();

        if(book.getOwner().getId().equals(connectedUser.getId())) {
            log.error("User with id: {} is the owner of the book with id: {}", connectedUser.getId(), bookId);
            throw new OperationNotPermittedException("User is the owner of the book, the requested book could not be returned or borrowed by the owner, operation is not permitted");
        }

        BookTransactionHistory bookTransactionHistory =
                bookTransactionHistoryRepository.findByBookIdAndUserId(bookId, connectedUser.getId())
                        .orElseThrow(() -> new OperationNotPermittedException("User did not borrow the book, return borrowed book operation is not permitted"));

        log.info("Book returned: {}, from method returnBorrowedBook of BookService", bookId);

        bookTransactionHistory.setReturned(true);
        Integer transactionId = bookTransactionHistoryRepository.save(bookTransactionHistory).getId();
        log.info("Book returned: {}, from method returnBorrowedBook of BookService", bookId);

        return transactionId;
    }

    @Override
    public Integer approveReturnBorrowedBook(Integer bookId, Authentication authenticatedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with given Id={%s}".formatted(bookId)));

        if (book.isArchived() || !book.isShareable()) {
            log.error("Book with id: {} is not shareable or archived", bookId);
            throw new OperationNotPermittedException("The Requested Book is not shareable or archived, return borrowed book operation is not permitted");
        }

        User connectedUser = (User) authenticatedUser.getPrincipal();

        if(!book.getOwner().getId().equals(connectedUser.getId())) {
            log.error("User with id: {} is not the owner of the book with id: {}", connectedUser.getId(), bookId);
            throw new OperationNotPermittedException("User is not the owner of the book, the requested book could not be approved, operation is not permitted");
        }

        BookTransactionHistory bookTransactionHistory =
                bookTransactionHistoryRepository.findByBookIdAndOwnerId(bookId, connectedUser.getId())
                        .orElseThrow(() -> new OperationNotPermittedException("User can not approve the book, borrowed book is not returned yet, operation is not permitted"));

        bookTransactionHistory.setReturnApproved(true);
        Integer transactionId = bookTransactionHistoryRepository.save(bookTransactionHistory).getId();
        log.info("Book return approved: {}, from method approveReturnBorrowedBook of BookService", bookId);

        return transactionId;
    }

    @Override
    public void uploadBookCoverImage(Integer bookId, MultipartFile file, Authentication authenticatedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with given Id={%s}".formatted(bookId)));

        User connectedUser = (User) authenticatedUser.getPrincipal();

        var bookCoverImage = fileStorageService.saveFile(file, connectedUser.getId());
        book.setBookCover(bookCoverImage);
        bookRepository.save(book);
    }



}
