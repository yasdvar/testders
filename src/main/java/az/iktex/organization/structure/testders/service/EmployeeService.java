package az.iktex.organization.structure.testders.service;

import az.iktex.organization.structure.testders.entity.Department;
import az.iktex.organization.structure.testders.entity.Employee;
import az.iktex.organization.structure.testders.mapper.EmployeeMapper;
import az.iktex.organization.structure.testders.repository.DepartmentRepository;
import az.iktex.organization.structure.testders.repository.EmployeeRepository;
import az.iktex.organization.structure.testders.request.EmployeeRequest;
import az.iktex.organization.structure.testders.response.EmployeeResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper employeeMapper;

    @Transactional
    public EmployeeResponse create(EmployeeRequest request) {
        Department department = findDepartment(request.departmentId());
        if (employeeRepository.existsByNameAndSurnameAndDepartmentId(
                request.name(), request.surname(), request.departmentId())) {
            throw new IllegalStateException("Employee with the same name and surname already exists in this department");
        }
        Employee employee = employeeMapper.toEntity(request);
        department.addEmployee(employee); // iki tərəfi sinxron saxlayır
        Employee saved = employeeRepository.save(employee);
        return employeeMapper.toResponse(saved);
    }

    public EmployeeResponse getById(Long id) {
        Employee employee = employeeRepository.findByIdWithDepartment(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found: " + id));
        return employeeMapper.toResponse(employee);
    }

    public List<EmployeeResponse> getByDepartment(Long departmentId) {
        findDepartment(departmentId);
        return employeeMapper.toResponseList(employeeRepository.findAllByDepartmentId(departmentId));
    }

    public List<EmployeeResponse> getBySalaryRange(BigDecimal min, BigDecimal max) {
        if (min.compareTo(max) > 0) {
            throw new IllegalArgumentException("min must not be greater than max");
        }
        return employeeMapper.toResponseList(employeeRepository.findAllBySalaryBetween(min, max));
    }

    public Double getAverageSalary(Long departmentId) {
        findDepartment(departmentId);
        Double average = employeeRepository.findAverageSalaryByDepartmentId(departmentId);
        return average == null ? 0.0 : average; // işçi yoxdursa avg null qaytarır
    }

    @Transactional
    public EmployeeResponse update(Long id, EmployeeRequest request) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found: " + id));
        if (!employee.getDepartment().getId().equals(request.departmentId())) {
            employee.setDepartment(findDepartment(request.departmentId())); // departament dəyişə bilər
        }
        employeeMapper.updateEntity(request, employee); // save() yoxdur: dirty checking
        return employeeMapper.toResponse(employee);
    }

    @Transactional
    public void delete(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found: " + id));
        employeeRepository.delete(employee); // cascade yoxdur: departament qalır
    }

    private Department findDepartment(Long departmentId) {
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new EntityNotFoundException("Department not found: " + departmentId));
    }
}
