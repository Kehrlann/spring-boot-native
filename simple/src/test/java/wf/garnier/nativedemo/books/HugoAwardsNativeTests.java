package wf.garnier.nativedemo.books;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(OutputCaptureExtension.class)
class HugoAwardsNativeTests {

	@Autowired
	MockMvc mockMvc;

	private final String baseUrl = "http://localhost:8000";

	@Test
	@EnabledInNativeImage
	void listBooks() throws Exception {
		var body = mockMvc.perform(get(baseUrl + "/hugo"))
			.andExpect(status().is2xxSuccessful())
			.andReturn()
			.getResponse()
			.getContentAsString();
		assertThat(body).contains("The Calculating Stars - Mary Robinette Kowal (2019)");
		assertThat(body).contains("The Stone Sky - N. K. Jemisin (2018)");
	}

	@Test
	@EnabledInNativeImage
	void debug(CapturedOutput output) throws Exception {
		mockMvc.perform(get(baseUrl + "/hugo?debug=true")).andExpect(status().is2xxSuccessful());
		assertThat(output.getOut().lines()).filteredOn(l -> l.startsWith("~~ debug ~~"))
			.hasSize(1)
			.first()
			.asString()
			.contains("\"baseUrl\":\"" + baseUrl + "\"");
	}

	@Test
	@EnabledInNativeImage
	void noDebug(CapturedOutput output) throws Exception {
		mockMvc.perform(get(baseUrl + "/hugo")).andExpect(status().is2xxSuccessful());
		assertThat(output.getOut().lines()).filteredOn(l -> l.startsWith("~~ debug ~~")).isEmpty();
	}

}
