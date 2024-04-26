package wf.garnier.nativedemo.books;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BookController {

	// TODO:
	// - @Profile beans
	// - @Conditional beans
	// - @Value for title
	// - Reflection:
	// 		- Jackson direct serialization
	// 		- Jackson mapping in the RestClient
	// - security

	// Ideas:
	// - page with list of books
	// 		- runtime hints for books
	// 		- remove /book-simple/
	// - Books API
	//		- add some logging with objectmapper (?)
	//		- use an ObjectMapper builder
	// - @Profile
	//		- BookRepository FR/EN with `@Profile("french")`
	// - @ConditionalOn...
	//		- ... pull some Spring AI?
	//		- ...
	//		- mention that the same goes for boot-auto-configuration
	// - @Value
	//		- title

	// TODO: null-safety

	private final BookRepository bookRepo;

    public BookController(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    @GetMapping("/book")
	public String book(Model model) {
		model.addAttribute("books", bookRepo.findAll());
		return "book-list";
	}

	@GetMapping("/book/{slug}")
	public String book(@PathVariable String slug, Model model) {
		model.addAttribute("book", bookRepo.findBookBySlug(slug));
		return "book";
	}

	@GetMapping("/api/book/{title}")
	@ResponseBody
	public BookApiResponse bookApi(@PathVariable String title) {
		return new BookApiResponse(books.get(title));
	}

	public record Book(String title, String synopsis, Map<String, String> additionalInfo) {
	}

	public record BookApiResponse(String title, String synopsis, Map<String, String> additionalInformation) {
		public BookApiResponse(Book book) {
			this(book.title(), book.synopsis(), book.additionalInfo());
		}
	}

	private final Map<String, Book> books = Map.of("the-hobbit", new Book("The Hobbit, or There and Back Again", """
			The Hobbit, or There and Back Again is a children's fantasy novel by the English author
			J. R. R. Tolkien. It was published in 1937 to wide critical acclaim, being nominated for the
			Carnegie Medal and awarded a prize from the New York Herald Tribune for best juvenile
			fiction. The book is recognized as a classic in children's literature and is one of the
			best-selling books of all time, with over 100 million copies sold.

			The Hobbit is set in Middle-earth and follows home-loving Bilbo Baggins, the hobbit of the
			title, who joins the wizard Gandalf and the thirteen dwarves of Thorin's Company, on a quest
			to reclaim the dwarves' home and treasure from the dragon Smaug. Bilbo's journey takes him
			from his peaceful rural surroundings into more sinister territory.
			""", new TreeMap<>() {
		{
			put("Author", "J R R Tolkien");
			put("Genre", "Fantasy");
			put("Publication date", "21 September 1937");
		}
	}), "the-fifth-season", new Book("The Fifth Season", """
			The Fifth Season takes place on a planet with a single supercontinent called the Stillness.
			Every few centuries, its inhabitants endure what they call a "Fifth Season" of catastrophic
			climate change.
			""", new HashMap<>() {
		{
			put("Author", "N. K. Jemisin");
			put("Genre", "Science fantasy");
			put("Series", "The Broken Earth trilogy");
			put("Publisher", "Orbit");
			put("Publication date", "August 4, 2015");
		}
	}));

}
