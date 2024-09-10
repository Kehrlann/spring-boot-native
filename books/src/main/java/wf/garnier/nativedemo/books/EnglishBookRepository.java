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
		books.put(
				"the-calculating-stars",
				new Book(
						"The Calculating Stars",
						"Marie Robinette Kowal",
						"Science fiction",
						"""
						On March 3, 1952, a meteorite strikes the Eastern Seaboard. Most government officials, including
						President Thomas Dewey, are killed in the disaster; Secretary of Agriculture Charles F. Brannan
						becomes president. Elma York, a mathematician and former WASP pilot, and her husband Nathaniel,
						a scientist who worked on the Manhattan Project and later NACA, survive the catastrophe. Many
						members of Elma’s family are killed by the Meteor and resulting tsunamis.

						Elma calculates that climate change from the disaster will make the Earth uninhabitable within
						decades. A miniature Ice Age will be followed by a sharp increase in temperature due to the
						greenhouse effect, ending in boiling oceans and the extinction of life on Earth. Elma and
						Nathaniel present the issue to President Brannan; the UN and USA agree to begin a space colony
						program. The UN forms the International Aerospace Coalition (IAC). Nathaniel becomes the IAC’s
						lead engineer. Elma applies to the space program, but no women are selected. She begins working
						as a computer.
						""",
						LocalDate.of(2018, Month.JULY, 3),
						"the-calculating-stars"
				)
		);
		books.put(
				"jonathan-strange-and-mr-norrell",
				new Book(
						"Jonathan Strange & Mister Norrell",
						"Susanna Clarke",
						"Alternative history",
						"""
						Jonathan Strange & Mr Norrell is the debut novel by British writer Susanna Clarke. Published in
						2004, it is an alternative history set in 19th-century England around the time of the Napoleonic
						Wars. Its premise is that magic once existed in England and has returned with two men: Gilbert
						Norrell and Jonathan Strange. Centred on the relationship between these two men, the novel
						investigates the nature of "Englishness" and the boundaries between reason and unreason,
						Anglo-Saxon and Anglo-Dane, and Northern and Southern English cultural tropes/stereotypes. It
						has been described as a fantasy novel, an alternative history, and a historical novel. It
						inverts the Industrial Revolution conception of the North–South divide in England: in this book
						the North is romantic and magical, rather than rational and concrete.
						""",
						LocalDate.of(2004, Month.SEPTEMBER, 8),
						"jonathan-strange-and-mr-norrell"
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
