package wf.garnier.nativedemo.books;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
			mockMvc.perform(get("/book"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(content().string(Matchers.containsString("The Hobbit, or There and Back Again")))
				.andExpect(content().string(Matchers.containsString("The Fifth Season")));
		}

		@Test
		void titleEnhancement() throws Exception {
			mockMvc.perform(get("/book/the-hobbit"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(content().string(Matchers.containsString("<h1>&quot;The Hobbit, or There and Back Again&quot;</h1>")));
		}

		@Test
		void bookApi() throws Exception {
			mockMvc.perform(get("/api/book/the-hobbit"))
					.andExpect(status().is2xxSuccessful())
					.andExpect(jsonPath("$.title").value("The Hobbit, or There and Back Again"))
					.andExpect(jsonPath("$.additionalInformation.Author").value("J R R Tolkien"))
					.andExpect(jsonPath("$.additionalInformation.Genre").value("Fantasy"))
					.andExpect(jsonPath("$.additionalInformation[\"Publication date\"]").value("21 September 1937"));
		}

	}

	@SpringBootTest
	@AutoConfigureMockMvc
	@TestPropertySource(properties = "books.lang=fr")
	@ActiveProfiles("fun")
	@Nested
	class FrenchAndFun {

		@Autowired
		MockMvc mockMvc;

		@Test
		void bookList() throws Exception {
			mockMvc.perform(get("/book"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(content().string(Matchers.containsString("Ravage")))
				.andExpect(content().string(Matchers.containsString("La Planète des Singes")));
		}


		@Test
		void titleEnhancement() throws Exception {
			mockMvc.perform(get("/book/la-planete-des-singes"))
					.andExpect(status().is2xxSuccessful())
					.andExpect(content().string(Matchers.containsString("<h1>🐵 La Planète des Singes</h1>")));
		}

	}

}
