package wf.garnier.nativedemo.examples;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.ui.Model;
import org.springframework.web.client.RestClient;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ImportRuntimeHints(TestRuntimeHints.class)
class TestConfigurationTests {

	@Autowired
	JsonController jsonController;

	@Autowired
	SwapBeanConfiguration config;

	Model mockModel = mock(Model.class);

	@Test
	void simpleMock() throws JsonProcessingException {
		var data = """
				{"title":"The Fifth Season","author":"N.K. Jemisin"}""";
		jsonController.writeJson(mockModel);
		verify(mockModel).addAttribute("method", "objectMapper.writeValueAsString(...)");
		verify(mockModel).addAttribute("data", data);
	}

	@Test
	void complicatedMock() {
		var response = new JsonController.RemoteApiData(2,
				List.of(new JsonController.BookApiResponse("Speaker for the Dead", "Orson Scott Card"),
						new JsonController.BookApiResponse("Hyperion", "Dan Simmons")));
		when(config.mockRestClient.get().uri("/hugo.json").retrieve().body(eq(JsonController.RemoteApiData.class)))
			.thenReturn(response);
		assertThat(jsonController.readJson(mockModel)).isEqualTo("json");
		verify(mockModel).addAttribute("method", "restClient.get()");
		verify(mockModel).addAttribute("data", response.toString());
		verifyNoMoreInteractions(mockModel);
	}

	@TestConfiguration
	static class SwapBeanConfiguration {

		public RestClient mockRestClient = mock(RestClient.class, RETURNS_DEEP_STUBS);

		@Bean
		JsonController jsonController() {
			// Can only mock interfaces, not concrete types
			// So we can't mock JsonController directly
			var mockBuilder = mock(RestClient.Builder.class);
			when(mockBuilder.baseUrl("https://api.example.com")).thenReturn(mockBuilder);
			when(mockBuilder.build()).thenReturn(mockRestClient);
			return new JsonController(mockBuilder, "https://api.example.com");
		}

	}

}
