package az.iktex.organization.structure.testders.repository;

import az.iktex.organization.structure.testders.entity.Passport;
import az.iktex.organization.structure.testders.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PassportRepository extends JpaRepository<Passport, Long> {
    boolean existsBySerialNumber(String serialNumber);
    boolean existsByUserId(Long userId);
    Optional<Passport> findByUserId(Long userId);


    @Query("select p from Passport p join fetch p.user where p.serialNumber=:serialNumber")
    Optional<Passport> findBySerialNumber(@Param("serialNumber") String serialNumber);


    @Query("SELECT count(p) from Passport p where p.country =:country")
    long countByCountry(@Param("country")String country);

}
