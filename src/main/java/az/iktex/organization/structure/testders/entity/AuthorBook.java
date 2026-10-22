package az.iktex.organization.structure.testders.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/*
Ustunluk: authorBook elave bir entity kimi idare oluna bilir meselen istesek
sutunda elave ede bilerik
Catismazliq:artiq kod 2@OneToMany ve 2@ManyToOne elaqeleri ve elaqeni qurmaq ucun ayrica
authorbook yaradib sonra qurmaq lazimdi
 */

@Entity
@Table(name = "author_book",
uniqueConstraints = @UniqueConstraint(name ="uk_author_book",
        columnNames = {"author_id","book_id"}))

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    // Cascade yoxdur: author_book sətri silinəndə müəllif silinməməlidir, çünki onun başqa kitabları ola bilər.
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(
            name = "author_id",
            foreignKey = @ForeignKey(name ="fk_ab_author"),nullable = false
    )
    private Author author;
    // Cascade yoxdur: author_book sətri silinəndə kitab silinməməlidir, çünki onun başqa müəllifləri ola bilər.
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(
            name = "book_id",
            foreignKey = @ForeignKey(name ="fk_ab_book"),nullable = false
    )
    private Book  book;


}
