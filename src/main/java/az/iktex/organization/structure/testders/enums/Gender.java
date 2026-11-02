package az.iktex.organization.structure.testders.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Gender {
    MALE (1),
    FEMALE (2);

    private final Integer dbCode;
     Gender(Integer dbCode) {
        this.dbCode = dbCode;
    }

    public static Gender findByDbCode(Integer dbCode){
         Gender gender= Arrays.stream(Gender.values()).filter(g -> g.dbCode.equals(dbCode)).findFirst().orElseThrow(()->new IllegalArgumentException("No such gender exists with id " + dbCode));
        return gender;
    }
}
