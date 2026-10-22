package az.iktex.organization.structure.testders.enums;

import lombok.Getter;

@Getter
public enum Gender {
    MALE (1),
    FEMALE (2);
    private final Integer id;
     Gender(Integer id) {
        this.id = id;
    }
    public static Gender getGender(Integer id) {
        for (Gender gender : Gender.values()) {
            if (gender.id.equals(id)) {
                return gender;
            }
        }
    throw new IllegalArgumentException("No such gender exists with id " + id);
     }
}
