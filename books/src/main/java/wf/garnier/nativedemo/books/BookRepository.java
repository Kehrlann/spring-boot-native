package wf.garnier.nativedemo.books;

import java.util.Collection;

public interface BookRepository {

	Book findBookBySlug(String slug);

	Collection<Book> findAll();

}
