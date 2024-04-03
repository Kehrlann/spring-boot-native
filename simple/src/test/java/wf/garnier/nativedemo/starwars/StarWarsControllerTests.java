package wf.garnier.nativedemo.starwars;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledInNativeImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.GenericContainer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(StarWarsControllerTests.SwapiContainerConfiguration.class)
@DisabledInNativeImage
@DisabledInAotMode
class StarWarsControllerTests {

	@Autowired
	@Qualifier("swapiContainer")
	GenericContainer<?> swapiContainer;

	@Autowired
	MockMvc mockMvc;

	@Autowired
	StarWarsController starWarsController;

	@BeforeEach
	void setUp() {
		// TODO: use dynamic property source instead
		starWarsController.setSwapiPort(swapiContainer.getFirstMappedPort());
	}

	@Test
	void listsPeople() throws Exception {
		mockMvc.perform(get("/starwars/characters"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(jsonPath("$.characters[0].name").value("C-3PO"))
			.andExpect(jsonPath("$.characters[0].movieCount").value(6));
	}

	@Test
	void remoteDescription() throws Exception {
		mockMvc.perform(get("/starwars/remote")).andExpect(status().is2xxSuccessful());
	}

	@TestConfiguration
	static class SwapiContainerConfiguration {

		@Bean("swapiContainer")
		public GenericContainer<?> swapiContainer() {
			var command = """
					sed -i "s/'PAGINATE_BY': 10/'PAGINATE_BY': 500/" swapi/settings.py
					./run.sh
					""";
			return new GenericContainer<>("juriy/swapi:1.0.0").withExposedPorts(8000).withCommand("sh", "-c", command);
		}

	}

}
