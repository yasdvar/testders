package az.iktex.organization.structure.testders.repository;

import az.iktex.organization.structure.testders.entity.User;
import az.iktex.organization.structure.testders.enums.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email,Long id);
    Optional<User> findByEmail(String email);
    List<User> findAllByGender(Gender gender);
    List<User> findAllByPassportIsNull();

    @Query("select u from User u left join fetch u.passport where u.id = :id")
    Optional<User> findByIdWithPassport(@Param("id") Long id);

    @Query("select u from User u left join fetch u.passport")
    List<User> findAllWithPassport();
}
