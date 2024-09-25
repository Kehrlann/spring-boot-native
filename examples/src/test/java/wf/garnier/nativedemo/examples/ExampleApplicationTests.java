package wf.garnier.nativedemo.examples;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ImportRuntimeHints(TestRuntimeHints.class)
class ExampleApplicationTests {

	@Autowired
	MockMvc mvc;

	@Test
	void index() throws Exception {
		mvc.perform(get("/")).andExpect(status().isOk());
	}

	@Test
	void templating() throws Exception {
		mvc.perform(get("/templating")).andExpect(status().isOk());
	}

	@Test
	void writeJson() throws Exception {
		mvc.perform(get("/json/write")).andExpect(status().isOk());
	}

	@Test
	void api() throws Exception {
		mvc.perform(get("/json/api")).andExpect(status().isOk());
	}

}
