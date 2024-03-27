package wf.garnier.nativedemo.color;

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
public class ColorControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Test
	void getColor() throws Exception {
		//@formatter:off
		mockMvc.perform(get("/color"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(content().string("red"));

		mockMvc.perform(get("/color"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(content().string("green"));

		mockMvc.perform(get("/color"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(content().string("blue"));
		//@formatter:on
	}

}
