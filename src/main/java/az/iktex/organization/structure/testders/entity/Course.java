package az.iktex.organization.structure.testders.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name ="title",nullable = false,unique = true,length = 100)
    private String title;

    @Column(name = "credit",nullable = false)
    private Integer credit;

    @Column(name = "teacher",nullable = false,length = 50)
    private String teacher;

    //bir telebe bir nece kurs yazila biler yeni eger kurs silinende telebede silinseydi
    //o telebenin basqa kurslardanda silinmesi hali arzuolunan deyil

    @ManyToMany(mappedBy ="courses",fetch = FetchType.LAZY)
    private Set<Student> students=new HashSet<>();
}
