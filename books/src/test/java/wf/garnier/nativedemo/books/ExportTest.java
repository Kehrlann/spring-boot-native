package wf.garnier.nativedemo.books;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@SpringBootTest
@AutoConfigureMockMvc
class ExportTest {

	@Autowired
	MockMvc mockMvc;

	@Test
	void export() throws Exception {
		var contentLength = mockMvc.perform(get("/books/export"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(content().contentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
			.andReturn()
			.getResponse()
			.getContentLength();

		assertThat(contentLength).isGreaterThan(3000);
	}

}
