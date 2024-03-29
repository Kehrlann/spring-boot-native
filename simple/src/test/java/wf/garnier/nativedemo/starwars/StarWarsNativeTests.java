package wf.garnier.nativedemo.starwars;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledInNativeImage;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(OutputCaptureExtension.class)
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
	void welcomeMessage(CapturedOutput output) {
		assertThat(output.getOut()).contains("🏃");
	}

}
