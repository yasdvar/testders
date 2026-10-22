package az.iktex.organization.structure.testders.entity;

import az.iktex.organization.structure.testders.entity.converter.GenderConverter;
import az.iktex.organization.structure.testders.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="name",nullable = false,length = 50)
    private String name;


    @Column(name="surname",nullable = false,length = 50)
    private String surname;


    @Column(name = "email",nullable = false,length = 100,unique = true)
    private String email;

    @Convert(converter =  GenderConverter.class)
    @Column(name="gender_id",nullable = false)
    private Gender gender;
    /* User silinende passportda silinmelidir
    */
    @OneToOne(mappedBy = "user",fetch = FetchType.LAZY,orphanRemoval = true,cascade = CascadeType.ALL)
    private Passport passport;

    public void  setPassport(Passport passport) {
        if(this.passport!=null) {
            this.passport.setUser(null);

        }
        this.passport = passport;
        if(passport!=null) {
            passport.setUser(this);
        }
    }
}
