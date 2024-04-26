package wf.garnier.nativedemo.books;

import java.time.LocalDate;

public record Book(String title, String author, String genre, String synopsis, LocalDate publicationDate) {

}
