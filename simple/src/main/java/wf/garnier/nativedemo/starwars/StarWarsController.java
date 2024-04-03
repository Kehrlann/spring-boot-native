package wf.garnier.nativedemo.starwars;

import java.util.Comparator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("/starwars")
public class StarWarsController {

	private int swapiPort = 8000;

	private RestClient restClient = RestClient.create("http://localhost:%s/".formatted(swapiPort));

	private ObjectMapper objectMapper = new ObjectMapper();

	public void setSwapiPort(Integer swapiPort) {
		this.swapiPort = swapiPort;
		this.restClient = RestClient.create("http://localhost:%s/".formatted(swapiPort));
	}

	@GetMapping("/characters")
	public StarWarsCharactersResponse characters() {
		var r = new ParameterizedTypeReference<SwapiResponse<SwapiCharacter>>() {
		};
		var characters = restClient.get()
			.uri("/api/people/")
			.retrieve()
			.body(r)
			.results()
			.stream()
			.map(c -> new StarWarsCharacter(c.name, c.films.size()))
			.sorted(Comparator.comparing(StarWarsCharacter::movieCount)
				.reversed()
				.thenComparing(StarWarsCharacter::name))
			.toList();
		return new StarWarsCharactersResponse(characters);
	}

	@GetMapping("/remote")
	public String remote() throws JsonProcessingException {
		return objectMapper
			.writeValueAsString(new RemoteServerDescription("http://localhost:%s/".formatted(swapiPort)));
	}

	public record StarWarsCharactersResponse(List<StarWarsCharacter> characters) {
	}

	public record StarWarsCharacter(String name, Integer movieCount) {
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	//@formatter:off
	public record SwapiCharacter(
			String birthYear,
			String eyeColor,
			String gender,
			String hairColor,
			String homeworld,
			String name,
			String skinColor,
			List<String> starShips,
			List<String> films,
			List<String> species,
			List<String> vehicles
	) { }
	//@formatter:on

	public record SwapiResponse<T>(List<T> results) {
	}

	public record RemoteServerDescription(String host, String name, String emoji) {

		public RemoteServerDescription(String host) {
			this(host, "swapi", "🌕⭐️🪐👾🧑‍🚀");
		}
	}

}
