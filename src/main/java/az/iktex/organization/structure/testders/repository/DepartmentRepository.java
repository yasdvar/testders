package az.iktex.organization.structure.testders.repository;

import az.iktex.organization.structure.testders.entity.Department;
import az.iktex.organization.structure.testders.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Long id);
    Optional<Department> findByName(String name);



    @Query("SELECT d from Department d left join fetch d.employees where d.id=:id")
    Optional<Department> findByIdWithEmployees(@Param("id") Long id);

    @Query("SELECT d from Department d where d.employees is empty ")
    List<Department>findAllWithoutEmployees();

    @Query("SELECT count(e)>0 from Employee e where e.department.id=:id")
    boolean hasEmployees(@Param("id") Long id);


}
