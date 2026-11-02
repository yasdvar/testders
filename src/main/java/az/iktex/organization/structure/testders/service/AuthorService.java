package az.iktex.organization.structure.testders.service;

import az.iktex.organization.structure.testders.entity.Author;
import az.iktex.organization.structure.testders.mapper.AuthorMapper;
import az.iktex.organization.structure.testders.mapper.BookMapper;
import az.iktex.organization.structure.testders.repository.AuthorRepository;
import az.iktex.organization.structure.testders.repository.BookRepository;
import az.iktex.organization.structure.testders.request.AuthorRequest;
import az.iktex.organization.structure.testders.response.AuthorResponse;
import az.iktex.organization.structure.testders.response.BookResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final AuthorMapper authorMapper;
    private final BookMapper bookMapper;

    @Transactional
    public AuthorResponse create(AuthorRequest request) {
        if (authorRepository.existsByNameAndSurname(request.name(), request.surname())) {
            throw new IllegalStateException("Author with the same name and surname already exists");
        }
        Author saved = authorRepository.save(authorMapper.toEntity(request));
        return authorMapper.toResponse(saved);
    }

    public AuthorResponse getById(Long id) {
        return authorMapper.toResponse(findAuthor(id));
    }

    // country verilsə filter, verilməsə hamısı
    public List<AuthorResponse> getAll(String country) {
        if (country == null || country.isBlank()) {
            return authorMapper.toResponseList(authorRepository.findAll());
        }
        return authorMapper.toResponseList(authorRepository.findAllByCountry(country));
    }

    public List<AuthorResponse> getWithoutBooks() {
        return authorMapper.toResponseList(authorRepository.findAllWithoutBooks());
    }

    public List<BookResponse> getBooks(Long authorId) {
        findAuthor(authorId);
        return bookMapper.toResponseList(bookRepository.findAllByAuthorId(authorId));
    }

    @Transactional
    public AuthorResponse update(Long id, AuthorRequest request) {
        Author author = findAuthor(id);
        if (authorRepository.existsByNameAndSurnameAndIdNot(request.name(), request.surname(), id)) {
            throw new IllegalStateException("Author with the same name and surname already exists");
        }
        authorMapper.updateEntity(request, author); // save() yoxdur: dirty checking
        return authorMapper.toResponse(author);
    }

    @Transactional
    public void delete(Long id) {
        Author author = findAuthor(id);
        // cascade ALL yalnız author_book sətirlərinə keçir; AuthorBook.book tərəfində cascade yoxdur, kitablar qalır
        authorRepository.delete(author);
    }

    private Author findAuthor(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found: " + id));
    }
}
