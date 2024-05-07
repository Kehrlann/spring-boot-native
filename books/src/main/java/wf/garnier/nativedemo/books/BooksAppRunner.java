package wf.garnier.nativedemo.books;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
class BooksAppRunner implements ApplicationRunner {

	private final TitleEnhancer titleEnhancer;

	private final BookRepository bookRepository;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public BooksAppRunner(TitleEnhancer titleEnhancer, BookRepository bookRepository) {
		this.titleEnhancer = titleEnhancer;
		this.bookRepository = bookRepository;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		logger.info("📚 BookRepository: " + bookRepository.getClass().getSimpleName());
		logger.info("↗️ TitleEnhancer: " + titleEnhancer.getClass().getSimpleName());
	}

}
