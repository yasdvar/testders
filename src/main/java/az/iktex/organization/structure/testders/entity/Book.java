package az.iktex.organization.structure.testders.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
@Getter
@Check(name = "chk_book_price",constraints = "price>=0")
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title",nullable = false,length = 150)
    private String title;

    @Column(name = "price",nullable = false,precision = 8,scale = 2)
    private BigDecimal price;

    @Column(name = "page_count",nullable = false)
    private Integer pageCount;
    // ALL ,orphanRemoval: kitab silinəndə onun author_book sətirləri silinsin; müəlliflər qalır, çünki AuthorBook-un @ManyToOne tərəfində cascade yoxdur.
    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<AuthorBook> authorBooks = new ArrayList<>();
}
