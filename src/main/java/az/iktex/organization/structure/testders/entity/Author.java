package az.iktex.organization.structure.testders.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name",nullable = false,length = 50)
    private String name;

    @Column(name = "surname",nullable = false,length = 50)
    private String surname;

    @Column(name = "country",length = 50)
    private String country;
    // ALL,orphanRemoval: müəllif silinəndə onun author_book sətirləri silinsin; kitablar qalır, çünki AuthorBook-un @ManyToOne tərəfində cascade yoxdur.
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<AuthorBook> authorBooks = new ArrayList<>();
}
