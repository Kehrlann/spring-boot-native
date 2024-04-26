package wf.garnier.nativedemo.books;

import java.util.Collection;
import java.util.List;

public interface BookRepository {

	Book findBookBySlug(String slug);

	Collection<Book> findAll();

}
