package wf.garnier.nativedemo.color;

import java.util.Arrays;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("random")
public class RandomColorControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Test
	void getColor() {
		IntFunction<String> getColorFromServer = (i) -> {
			try {
				return mockMvc.perform(get("/color"))
					.andExpect(status().is2xxSuccessful())
					.andReturn()
					.getResponse()
					.getContentAsString();
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
		var colors = IntStream.range(0, 3).mapToObj(getColorFromServer).toList();

		assertThat(colors).containsAnyElementsOf(Arrays.asList(ColorPicker.colors));
	}

}
