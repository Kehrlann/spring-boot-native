package wf.garnier.nativedemo.books;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BookController {

	// TODO:
	// - @Value for title
	// - Reflection:
	// - Jackson direct serialization
	// - Jackson mapping in the RestClient
	// - security

	// Ideas:
	// - Books API
	// - add some logging with objectmapper (?)
	// - use an ObjectMapper builder
	// - @Conditional: same goes for Boot-auto configuration

	// TODO: null-safety

	private final BookRepository bookRepo;

	private final String pageTitle;

	public BookController(BookRepository bookRepo, @Value("${book.title:My Book Collection}") String pageTitle) {
		this.bookRepo = bookRepo;
		this.pageTitle = pageTitle;
	}

	@GetMapping("/book")
	public String book(Model model) {
		model.addAttribute("pageTitle", pageTitle);
		model.addAttribute("books", bookRepo.findAll());
		return "book-list";
	}

	@GetMapping("/book/{slug}")
	public String book(@PathVariable String slug, Model model) {
		model.addAttribute("book", bookRepo.findBookBySlug(slug));
		return "book";
	}

	@GetMapping("/api/book/{slug}")
	@ResponseBody
	public BookApiResponse bookApi(@PathVariable String slug) {
		return new BookApiResponse(bookRepo.findBookBySlug(slug));
	}

	public record BookApiResponse(String title, String author, String genre, String synopsis, LocalDate publicationDate,
			String slug, Map<String, String> additionalInformation) {
		public BookApiResponse(Book book) {
			this(book.title(), book.author(), book.genre(), book.synopsis(), book.publicationDate(), book.slug(),
					book.additionalInformation());
		}
	}

}
