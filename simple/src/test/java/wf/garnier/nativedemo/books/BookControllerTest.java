package wf.garnier.nativedemo.books;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Test
	void theHobbit() throws Exception {
		mockMvc.perform(get("/book/the-hobbit"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(xpath("//li[@data-role=\"additional-information\"][1]/span[1]").string("Author: "))
			.andExpect(xpath("//li[@data-role=\"additional-information\"][1]/span[2]").string("J R R Tolkien"))
			.andExpect(xpath("//li[@data-role=\"additional-information\"][2]/span[1]").string("Genre: "))
			.andExpect(xpath("//li[@data-role=\"additional-information\"][2]/span[2]").string("Fantasy"))
			.andExpect(xpath("//li[@data-role=\"additional-information\"][3]/span[1]").string("Publication date: "))
			.andExpect(xpath("//li[@data-role=\"additional-information\"][3]/span[2]").string("21 September 1937"));
	}

	@Test
	void theFifthSeason() throws Exception {
		mockMvc.perform(get("/book/the-fifth-season"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(xpath("//li[@data-role=\"additional-information\"]").nodeCount(5));
	}

}
