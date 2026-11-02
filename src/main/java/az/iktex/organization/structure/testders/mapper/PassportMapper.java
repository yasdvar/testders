package az.iktex.organization.structure.testders.mapper;

import az.iktex.organization.structure.testders.entity.Passport;
import az.iktex.organization.structure.testders.request.PassportRequest;
import az.iktex.organization.structure.testders.response.PassportResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PassportMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Passport toEntity(PassportRequest passportRequest);



    PassportResponse toResponse(Passport passport);

    List<PassportResponse> toResponseList(List<Passport> passports);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateEntity(PassportRequest passportRequest, @MappingTarget Passport passport);


}
