package wf.garnier.nativedemo.books;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.context.annotation.Profile;

@Configuration
@ImportRuntimeHints(BookRuntimeHints.class)
class BookConfiguration {

	@Bean
	@ConditionalOnProperty(value = "books.lang", havingValue = "en", matchIfMissing = true)
	BookRepository defaultBookRepository() {
		return new EnglishBookRepository();
	}

	@Bean
	@ConditionalOnProperty(value = "books.lang", havingValue = "fr")
	BookRepository frenchBookRepository() {
		return new FrenchBookRepository();
	}

	@Bean(name = "titleEnhancer")
	@Profile("!fun")
	TitleEnhancer defaultTitleEnhancer() {
		return new SeriousTitleEnhancer();
	}

	@Bean(name = "titleEnhancer")
	@Profile("fun")
	TitleEnhancer emojiTitleEnhancer() {
		return new EmojiTitleEnhancer();
	}

}
