package wf.garnier.nativedemo.books;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;

public record Book(String title, String author, String genre, String synopsis, LocalDate publicationDate,
				   String slug, Map<String, String> additionalInformation) {

	public Book(String title, String author, String genre, String synopsis, LocalDate publicationDate, String slug) {
		this(title, author, genre, synopsis, publicationDate, slug, Collections.emptyMap());
	}
}
