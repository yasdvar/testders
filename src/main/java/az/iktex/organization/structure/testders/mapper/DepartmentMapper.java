package az.iktex.organization.structure.testders.mapper;

import az.iktex.organization.structure.testders.entity.Department;
import az.iktex.organization.structure.testders.entity.Employee;
import az.iktex.organization.structure.testders.request.DepartmentRequest;
import az.iktex.organization.structure.testders.response.DepartmentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring",uses = EmployeeMapper.class)
public interface DepartmentMapper {

    @Mapping(target ="id",ignore = true )
    @Mapping(target ="employees",ignore = true )
    Department toEntity(DepartmentRequest departmentRequest);

    @Mapping(target = "employees",ignore = true)
    DepartmentResponse toResponse(Department department);


    @Named("withEmployees")
    DepartmentResponse toDetailResponse(Department department);


    List<DepartmentResponse> toResponseList(List<Department> departments);

    @Mapping(target ="id",ignore = true )
    @Mapping(target ="employees",ignore = true )
    void updateEntity(DepartmentRequest departmentRequest, @MappingTarget Department department);

}
