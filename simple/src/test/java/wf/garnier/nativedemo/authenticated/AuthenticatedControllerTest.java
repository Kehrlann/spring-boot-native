package wf.garnier.nativedemo.authenticated;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticatedControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Test
	void helloAnonymous() throws Exception {
		mockMvc.perform(get("/authenticated/hello"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("http://localhost/login"));
	}

	@Test
	@WithMockUser(username = "test-user", roles = { "user", "admin" })
	void helloAdmin() throws Exception {
		mockMvc.perform(get("/authenticated/hello"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(content().string("Hello test-user"));
	}

	@Test
	@WithMockUser(username = "test-user", roles = { "user" })
	void helloNonAdmin() throws Exception {
		mockMvc.perform(get("/authenticated/hello")).andExpect(status().is4xxClientError());
	}

	@Test
	void aTeamAnonymous() throws Exception {
		mockMvc.perform(get("/authenticated/a-team"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("http://localhost/login"));
	}

	@Test
	@WithMockUser(username = "test-user")
	void aTeamAdmin() throws Exception {
		mockMvc.perform(get("/authenticated/a-team")).andExpect(status().is4xxClientError());
	}

	@Test
	@WithMockUser(username = "daniel")
	void aTeamNonAdmin() throws Exception {
		mockMvc.perform(get("/authenticated/a-team"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(content().string("You are part of the A team!"));
	}

}
