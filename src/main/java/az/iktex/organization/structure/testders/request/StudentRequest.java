package az.iktex.organization.structure.testders.request;

import az.iktex.organization.structure.testders.enums.Gender;
import az.iktex.organization.structure.testders.response.PassportResponse;

public record StudentRequest (String name, String surname, Integer age){
}
