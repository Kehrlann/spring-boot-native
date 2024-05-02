package wf.garnier.nativedemo.books;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityTests {

	@Autowired
	MockMvc mockMvc;

	@Test
	void bookAnonymous() throws Exception {
		mockMvc.perform(get("/book")).andExpect(status().is2xxSuccessful());
	}

	@Test
	void adminAnonymous() throws Exception {
		mockMvc.perform(get("/admin"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("http://localhost/login"));
	}

	@Test
	@WithMockUser(username = "daniel")
	void adminLibrarian() throws Exception {
		mockMvc.perform(get("/admin")).andExpect(status().is2xxSuccessful());
	}

	@Test
	@WithMockUser(username = "noone")
	void adminNonLibrarian() throws Exception {
		mockMvc.perform(get("/admin")).andExpect(status().is4xxClientError());
	}

}
