package wf.garnier.nativedemo.books;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledInNativeImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.images.builder.Transferable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisabledInNativeImage
@DisabledInAotMode
class HugoAwardsTests {

	@Autowired
	@Qualifier("hugoAwardsContainer")
	GenericContainer<?> hugoAwardsContainer;

	@Autowired
	MockMvc mockMvc;

	@Autowired
	HugoAwardsService hugoAwardsService;

	@BeforeEach
	void setUp() {
		hugoAwardsService.setUpstreamServicePort(hugoAwardsContainer.getFirstMappedPort());
	}

	@Test
	void listsPeople() throws Exception {
		var body = mockMvc.perform(get("/hugo"))
			.andExpect(status().is2xxSuccessful())
			.andReturn()
			.getResponse()
			.getContentAsString();
		assertThat(body).contains("The Calculating Stars - Mary Robinette Kowal (2019)");
		assertThat(body).contains("The Stone Sky - N. K. Jemisin (2018)");
	}

	@TestConfiguration
	static class HugoAwardsContainerConfiguration {

		@Bean("hugoAwardsContainer")
		public GenericContainer<?> hugoAwardsContainer() {
			Transferable transferable = Transferable.of("""
					{
					  "count": 2,
					  "results": [
					    {
					      "title": "The Calculating Stars",
					      "author": "Mary Robinette Kowal",
					      "year": 2019
					    },
					    {
					      "title": "The Stone Sky",
					      "author": "N. K. Jemisin",
					      "year": 2018
					    }
					  ]
					}
					""");
			return new GenericContainer<>("nginx:1.25.5-alpine").withExposedPorts(80)
				.withCopyToContainer(transferable, "/usr/share/nginx/html/hugo.json");
		}

	}

}
