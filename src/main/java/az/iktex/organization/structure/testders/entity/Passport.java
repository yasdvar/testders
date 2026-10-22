package az.iktex.organization.structure.testders.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "passports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "serial_number",nullable = false,unique = true,length = 20)
    private String serialNumber;

    @Column(name = "issue_date",nullable = false)
    private LocalDate issueDate;
    @Column(name = "country",nullable = false ,length = 50)
    private String country;

//cascade yoxdur pasport silinende user silinmemelidir
    @OneToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            unique = true,
            foreignKey = @ForeignKey(name = "fk_passport_user"
            ))
    private User user;
    /*
    Burada user_id sadece bir foreign keydir biz unique vermesek onda bele bir halla rastlasa
    bilerikki bir userin iki passportu olsun.Bu hal hem meqsedeuygun deyil hemde
    OnetoONe elaqesini pozub OnetoMany halina salir
    */



}
