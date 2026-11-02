package az.iktex.organization.structure.testders.controller;

import az.iktex.organization.structure.testders.request.BookRequest;
import az.iktex.organization.structure.testders.response.BookResponse;
import az.iktex.organization.structure.testders.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponse> create(@RequestBody BookRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> getAll(@RequestParam(required = false) String title) {
        return ResponseEntity.ok(bookService.getAll(title));
    }

    @GetMapping("/cheaper-than")
    public ResponseEntity<List<BookResponse>> getCheaperThan(@RequestParam BigDecimal price) {
        return ResponseEntity.ok(bookService.getCheaperThan(price));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> update(@PathVariable Long id, @RequestBody BookRequest request) {
        return ResponseEntity.ok(bookService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{bookId}/authors/{authorId}")
    public ResponseEntity<BookResponse> addAuthor(@PathVariable Long bookId, @PathVariable Long authorId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.addAuthor(bookId, authorId));
    }

    @DeleteMapping("/{bookId}/authors/{authorId}")
    public ResponseEntity<Void> removeAuthor(@PathVariable Long bookId, @PathVariable Long authorId) {
        bookService.removeAuthor(bookId, authorId);
        return ResponseEntity.noContent().build();
    }
}
