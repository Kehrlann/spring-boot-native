package wf.garnier.nativedemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HelloControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Test
	void greets() throws Exception {
		mockMvc.perform(get("/hello?name=daniel"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(content().string("Hello daniel!"));
	}

}
