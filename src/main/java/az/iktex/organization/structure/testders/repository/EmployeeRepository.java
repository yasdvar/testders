package az.iktex.organization.structure.testders.repository;

import az.iktex.organization.structure.testders.entity.Employee;
import az.iktex.organization.structure.testders.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.io.DataInputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findAllByDepartmentId(Long id);
    List<Employee> findAllBySalaryBetween(BigDecimal salaryStart, BigDecimal salaryEnd);
    boolean existsByDepartmentId(Long id);
    boolean existsByNameAndSurnameAndDepartmentId(String name, String surname, Long id);


    @Query("SELECT e from Employee e join fetch e.department where e.id=:id")
    Optional<Employee> findByIdWithDepartment(@Param("id") Long id);


    @Query("select avg(e.salary)from Employee e where e.department.id=:departmentId ")
    Double findAverageSalaryByDepartmentId(@Param("departmentId") Long id);
}
