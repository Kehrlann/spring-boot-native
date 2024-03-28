package wf.garnier.nativedemo.books;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

@Configuration
@ImportRuntimeHints(BookRuntimeHints.class)
class BookConfiguration {

}
