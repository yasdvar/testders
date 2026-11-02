package az.iktex.organization.structure.testders.mapper;

import az.iktex.organization.structure.testders.entity.AuthorBook;
import az.iktex.organization.structure.testders.entity.Book;
import az.iktex.organization.structure.testders.request.BookRequest;
import az.iktex.organization.structure.testders.response.AuthorResponse;
import az.iktex.organization.structure.testders.response.BookResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authorBooks", ignore = true)
    Book toEntity(BookRequest request);


    @Mapping(target = "authors", ignore = true)
    BookResponse toResponse(Book book);

    @Named("withAuthors")
    @Mapping(source = "authorBooks", target = "authors")
    BookResponse toDetailResponse(Book book);

    @Mapping(target = ".", source = "author")
    AuthorResponse toAuthorResponse(AuthorBook authorBook);

    List<BookResponse> toResponseList(List<Book> books);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authorBooks", ignore = true)
    void updateEntity(BookRequest request, @MappingTarget Book book);
}
