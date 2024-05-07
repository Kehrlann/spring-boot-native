package wf.garnier.nativedemo.books;

import java.time.LocalDate;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BookController {

	private final BookRepository bookRepo;

	private final String pageTitle;

	private final HugoAwardsService hugoAwardsService;

	private final ObjectMapper objectMapper;

	private final ExportService exportService;

	public BookController(BookRepository bookRepo, HugoAwardsService hugoAwardsService,
                          Jackson2ObjectMapperBuilder objectMapperBuilder,
                          @Value("${book.title:My Book Collection}") String pageTitle, ExportService exportService) {
		this.bookRepo = bookRepo;
		this.hugoAwardsService = hugoAwardsService;
		this.objectMapper = objectMapperBuilder.build();
		this.pageTitle = pageTitle;
        this.exportService = exportService;
    }

	@GetMapping
	public String index() {
		return "redirect:/books";
	}

	@GetMapping("/books")
	public String book(Model model) {
		model.addAttribute("pageTitle", pageTitle);
		model.addAttribute("books", bookRepo.findAll());
		return "book-list";
	}

	@GetMapping(value = "/books/export", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	@ResponseBody
	public byte[] exportBookList() {
		return exportService.export(bookRepo.findAll());
	}

	@GetMapping("/hugo")
	public String hugo(@RequestParam(value = "debug", required = false) String debug, Model model)
			throws JsonProcessingException {
		if (StringUtils.hasText(debug)) {
			var logLine = objectMapper.writeValueAsString(hugoAwardsService.getConfiguration());
			System.out.println("~~ debug ~~" + logLine); // Please use a logger :D
		}
		model.addAttribute("books", hugoAwardsService.findAll());
		return "hugo";
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

	@GetMapping("/admin")
	@PreAuthorize("@librarianService.isLibrarian(authentication)")
	public String admin(Model model) {
		model.addAttribute("books", bookRepo.findAll());
		return "admin";
	}

	public record BookApiResponse(String title, String author, String genre, String synopsis, LocalDate publicationDate,
			String slug, Map<String, String> additionalInformation) {
		public BookApiResponse(Book book) {
			this(book.title(), book.author(), book.genre(), book.synopsis(), book.publicationDate(), book.slug(),
					book.additionalInformation());
		}
	}

}
