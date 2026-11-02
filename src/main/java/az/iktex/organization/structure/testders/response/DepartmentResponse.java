package az.iktex.organization.structure.testders.response;

import az.iktex.organization.structure.testders.entity.Employee;

import java.util.List;

public record DepartmentResponse(Long id, String name, String  location, String phone,List<EmployeeResponse> employees) {
}
