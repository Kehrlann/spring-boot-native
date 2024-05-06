package wf.garnier.nativedemo.books;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

class BookControllerTest {

	@SpringBootTest
	@AutoConfigureMockMvc
	@Nested
	class Defaults {

		@Autowired
		MockMvc mockMvc;

		@Test
		void theHobbit() throws Exception {
			mockMvc.perform(get("/book/the-hobbit"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(xpath("//li[@data-role=\"author\"]/span[1]").string("Author: "))
				.andExpect(xpath("//li[@data-role=\"author\"]/span[2]").string("J R R Tolkien"))
				.andExpect(xpath("//li[@data-role=\"genre\"]/span[1]").string("Genre: "))
				.andExpect(xpath("//li[@data-role=\"genre\"]/span[2]").string("Fantasy"))
				.andExpect(xpath("//li[@data-role=\"publication-date\"]/span[1]").string("Publication date: "))
				.andExpect(xpath("//li[@data-role=\"publication-date\"]/span[2]").string("1937-09-21"));
		}

		@Test
		void theFifthSeason() throws Exception {
			mockMvc.perform(get("/book/the-fifth-season"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(xpath("//li[@data-role=\"additional-information\"]").nodeCount(2));
		}

		@Test
		void bookList() throws Exception {
			var body = mockMvc.perform(get("/books"))
				.andExpect(status().is2xxSuccessful())
				.andReturn()
				.getResponse()
				.getContentAsString();
			assertThat(body).contains("<h1>My Book Collection</h1>");
			assertThat(body).contains("The Hobbit, or There and Back Again");
			assertThat(body).contains("The Fifth Season");
		}

		@Test
		void titleEnhancement() throws Exception {
			var body = mockMvc.perform(get("/book/the-hobbit"))
				.andExpect(status().is2xxSuccessful())
				.andReturn()
				.getResponse()
				.getContentAsString();
			assertThat(body).contains("<h1>&quot;The Hobbit, or There and Back Again&quot;</h1>");
		}

		@Test
		void bookApi() throws Exception {
			mockMvc.perform(get("/api/book/the-fifth-season"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(jsonPath("$.title").value("The Fifth Season"))
				.andExpect(jsonPath("$.author").value("N. K. Jemisin"))
				.andExpect(jsonPath("$.genre").value("Science fantasy"))
				.andExpect(jsonPath("$.publicationDate").value("2015-08-04"))
				.andExpect(jsonPath("$.additionalInformation.Series").value("The Broken Earth trilogy"))
				.andExpect(jsonPath("$.additionalInformation.Publisher").value("Orbit"));
		}

	}

	@Nested
	@SpringBootTest
	@AutoConfigureMockMvc
	@TestPropertySource(properties = { "books.lang=fr", "book.title=Ma Collection de Livres" }) // <----
	@ActiveProfiles("fun") // <----
	class FrenchAndFun {

		@Autowired
		MockMvc mockMvc;

		@Test
		void bookList() throws Exception {
			var body = mockMvc.perform(get("/books"))
				.andExpect(status().is2xxSuccessful())
				.andReturn()
				.getResponse()
				.getContentAsString();
			assertThat(body).contains("<h1>Ma Collection de Livres</h1>");
			assertThat(body).contains("Ravage");
			assertThat(body).contains("La Planète des Singes");
		}

		@Test
		void titleEnhancement() throws Exception {
			var body = mockMvc.perform(get("/book/la-planete-des-singes"))
				.andExpect(status().is2xxSuccessful())
				.andReturn()
				.getResponse()
				.getContentAsString();
			assertThat(body).contains("<h1>🐵 La Planète des Singes</h1>");
		}

	}

}
