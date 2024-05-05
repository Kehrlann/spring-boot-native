package wf.garnier.nativedemo.books;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

class EnglishBookRepository implements BookRepository {

	private final Map<String, Book> books = new HashMap<>();

	public EnglishBookRepository() {
		//@formatter:off
		books.put(
				"the-hobbit",
				new Book(
						"The Hobbit, or There and Back Again",
						"J R R Tolkien",
						"Fantasy",
						"""
						The Hobbit, or There and Back Again is a children's fantasy novel by the English author
						J. R. R. Tolkien. It was published in 1937 to wide critical acclaim, being nominated for the
						Carnegie Medal and awarded a prize from the New York Herald Tribune for best juvenile
						fiction. The book is recognized as a classic in children's literature and is one of the
						best-selling books of all time, with over 100 million copies sold.
		
						The Hobbit is set in Middle-earth and follows home-loving Bilbo Baggins, the hobbit of the
						title, who joins the wizard Gandalf and the thirteen dwarves of Thorin's Company, on a quest
						to reclaim the dwarves' home and treasure from the dragon Smaug. Bilbo's journey takes him
						from his peaceful rural surroundings into more sinister territory.
						""",
						LocalDate.of(1937, Month.SEPTEMBER, 21),
						"the-hobbit"
						)
		);
		books.put(
				"the-fifth-season",
				new Book(
						"The Fifth Season",
						"N. K. Jemisin",
						"Science fantasy",
						"""
						The Fifth Season takes place on a planet with a single supercontinent called the Stillness.
						Every few centuries, its inhabitants endure what they call a "Fifth Season" of catastrophic
						climate change.
						""",
						LocalDate.of(2015, Month.AUGUST, 4),
						"the-fifth-season",
						new HashMap<>() {
							{
								put("Series", "The Broken Earth trilogy");
								put("Publisher", "Orbit");
							}
						}
				)
		);
		//@formatter:on
	}

	@Override
	public Book findBookBySlug(String slug) {
		return books.get(slug);
	}

	@Override
	public Collection<Book> findAll() {
		return books.values();
	}

}
