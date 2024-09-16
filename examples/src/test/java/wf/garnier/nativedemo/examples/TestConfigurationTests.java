package wf.garnier.nativedemo.examples;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.NginxContainer;
import org.testcontainers.images.builder.Transferable;
import org.testcontainers.utility.DockerImageName;

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
		assertThat(jsonController.readJson(mockModel)).isEqualTo("json");
		verify(mockModel).addAttribute("method", "restClient.get()");
		verify(mockModel).addAttribute("data", response.toString());
		verifyNoMoreInteractions(mockModel);
	}

	@TestConfiguration
	static class SwapBeanConfiguration {

		@Bean
		public NginxContainer<?> nginxContainer() {
			return new NginxContainer<>(DockerImageName.parse("nginx:1.25.5-alpine"))
				.withCopyToContainer(Transferable.of("""
						{
						  "count": 2,
						  "results": [
						    {
						      "title": "Speaker for the Dead",
						      "author": "Orson Scott Card",
						      "year":  1986
						    },
						    {
						      "title": "Hyperion",
						      "author": "Dan Simmons",
						      "year":  1991
						    }
						  ]
						}
						"""), "/usr/share/nginx/html/hugo.json");
		}

		@Bean
		JsonController jsonController(RestClient.Builder builder, NginxContainer<?> container) {
			return new JsonController(builder, "http://localhost:%s".formatted(container.getFirstMappedPort()));
		}

	}

}
