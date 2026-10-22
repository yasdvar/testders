package az.iktex.organization.structure.testders.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "departments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name",nullable = false,unique = true,length = 50)
    private String name;

    @Column(name = "location",length = 100)
    private String location;

    @Column(name = "phone",length = 20)
    private String phone;
    // ALL ,orphanRemoval: departament silinəndə işçilər də silinsin, siyahıdan çıxarılan işçi isə bazadan silinsin.
    @OneToMany(mappedBy = "department",fetch = FetchType.LAZY,orphanRemoval = true,cascade = CascadeType.ALL)
    private List<Employee> employees = new ArrayList<>();

    public  void addEmployee(Employee e) {
        employees.add(e);
        e.setDepartment(this);
    }
    public  void removeEmployee(Employee e) {
        employees.remove(e);
        e.setDepartment(null);
    }
}
