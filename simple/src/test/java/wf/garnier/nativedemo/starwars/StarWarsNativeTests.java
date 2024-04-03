package wf.garnier.nativedemo.starwars;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledInNativeImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class StarWarsNativeTests {

	@Autowired
	MockMvc mockMvc;

	@Test
	@EnabledInNativeImage
	void listsPeople() throws Exception {
		mockMvc.perform(get("/starwars/characters"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(jsonPath("$.characters[0].name").value("C-3PO"))
			.andExpect(jsonPath("$.characters[0].movieCount").value(6));
	}

	@Test
	@EnabledInNativeImage
	void remoteDescription() throws Exception {
		mockMvc.perform(get("/starwars/remote")).andExpect(status().is2xxSuccessful());
	}

}
