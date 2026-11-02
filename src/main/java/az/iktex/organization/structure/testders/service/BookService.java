package az.iktex.organization.structure.testders.service;

import az.iktex.organization.structure.testders.entity.Author;
import az.iktex.organization.structure.testders.entity.AuthorBook;
import az.iktex.organization.structure.testders.entity.Book;
import az.iktex.organization.structure.testders.mapper.BookMapper;
import az.iktex.organization.structure.testders.repository.AuthorBookRepository;
import az.iktex.organization.structure.testders.repository.AuthorRepository;
import az.iktex.organization.structure.testders.repository.BookRepository;
import az.iktex.organization.structure.testders.request.BookRequest;
import az.iktex.organization.structure.testders.response.BookResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final AuthorBookRepository authorBookRepository;
    private final BookMapper bookMapper;

    @Transactional
    public BookResponse create(BookRequest request) {
        if (bookRepository.existsByTitle(request.title())) {
            throw new IllegalStateException("Book title already exists");
        }
        Book saved = bookRepository.save(bookMapper.toEntity(request));
        return bookMapper.toResponse(saved);
    }

    // Müəlliflər ilə birlikdə (detail)
    public BookResponse getById(Long id) {
        Book book = bookRepository.findByIdWithAuthors(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + id));
        return bookMapper.toDetailResponse(book);
    }

    // title verilsə axtarış (ContainingIgnoreCase), verilməsə hamısı
    public List<BookResponse> getAll(String title) {
        if (title == null || title.isBlank()) {
            return bookMapper.toResponseList(bookRepository.findAll());
        }
        return bookMapper.toResponseList(bookRepository.findAllByTitleContainingIgnoreCase(title));
    }

    public List<BookResponse> getCheaperThan(BigDecimal price) {
        return bookMapper.toResponseList(bookRepository.findAllByPriceLessThanEqualOrderByPriceAsc(price));
    }

    @Transactional
    public BookResponse update(Long id, BookRequest request) {
        Book book = findBook(id);
        if (bookRepository.existsByTitleAndIdNot(request.title(), id)) {
            throw new IllegalStateException("Book title already exists");
        }
        bookMapper.updateEntity(request, book); // save() yoxdur: dirty checking
        return bookMapper.toResponse(book);
    }

    @Transactional
    public void delete(Long id) {
        Book book = findBook(id);
        // cascade ALL yalnız author_book sətirlərini silir; müəlliflər qalır
        bookRepository.delete(book);
    }

    @Transactional
    public BookResponse addAuthor(Long bookId, Long authorId) {
        Book book = findBook(bookId);
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author not found: " + authorId));
        if (authorBookRepository.existsByAuthorIdAndBookId(authorId, bookId)) {
            throw new IllegalStateException("Author is already linked to this book");
        }
        AuthorBook authorBook = new AuthorBook();
        authorBook.setAuthor(author);
        authorBook.setBook(book);
        authorBookRepository.save(authorBook);
        return getById(bookId);
    }

    @Transactional
    public void removeAuthor(Long bookId, Long authorId) {
        int deleted = authorBookRepository.deleteByAuthorIdAndBookId(authorId, bookId);
        if (deleted == 0) {
            throw new EntityNotFoundException("Author is not linked to this book");
        }
    }

    private Book findBook(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + id));
    }
}
