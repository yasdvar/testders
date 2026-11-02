package az.iktex.organization.structure.testders.repository;

import az.iktex.organization.structure.testders.entity.AuthorBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AuthorBookRepository extends JpaRepository<AuthorBook, Long> {
    boolean existsByAuthorIdAndBookId(Long authorId, Long bookId);
    Optional<AuthorBook> findByAuthorIdAndBookId(Long authorId, Long bookId);
    long countByBookId(Long bookId);

    @Modifying(clearAutomatically = true)
    @Query("DELETE from AuthorBook ab where ab.book.id=:bookId and ab.author.id=:authorId")
    int deleteByAuthorIdAndBookId(@Param("authorId") Long authorId, @Param("bookId") Long bookId);


    @Query("SELECT ab from AuthorBook ab left join fetch ab.author where ab.book.id=:id")
    List<AuthorBook> findAllByBookIdWithAuthor(@Param("id") Long id);
}
