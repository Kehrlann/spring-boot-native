package wf.garnier.nativedemo.examples;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClient;

@Controller
class JsonController {

	private final ObjectMapper objectMapper = new ObjectMapper();

	private final RestClient restClient;

	JsonController(RestClient.Builder restClientBuilder, @Value("${api.server}") String apiServer) {
		this.restClient = restClientBuilder.baseUrl(apiServer).build();
	}

	@GetMapping("/json/read")
	public String readJson(Model model) {
		//@formatter:off
		var response = restClient
				.get()
				.uri("/hugo.json")
				.retrieve()
				.body(RemoteApiData.class);
		//@formatter:on
		model.addAttribute("method", "restClient.get()");
		model.addAttribute("data", response.toString());
		return "json";
	}

	@GetMapping("/json/write")
	public String writeJson(Model model) throws JsonProcessingException {
		var bookData = objectMapper.writeValueAsString(new BookToSerialize("The Fifth Season", "N.K. Jemisin"));
		model.addAttribute("method", "objectMapper.writeValueAsString(...)");
		model.addAttribute("data", bookData);
		return "json";
	}

	@ResponseBody
	@GetMapping("/json/api")
	public List<BookApiResponse> api() {
		return List.of(new BookApiResponse("Ravage", "René Barjavel"),
				new BookApiResponse("La Planète des Singes", "Pierre Boulle"));
	}

	/**
	 * We get some JSON from a remote API, and map it to this data structure.
	 *
	 * It's equivalent to calling "objectMapper.readValue(...)"
	 */
	record RemoteApiData(int count, List<BookApiResponse> results) {
		record BookDeserializationResult(String title, String author, int year) {
		}

	}

	/**
	 * We write JSON of this data using "objectMapper.writeValueAsString(...)"
	 */
	record BookToSerialize(String title, String author) {
	}

	/**
	 * We send this as the response body of one of our controller methods.
	 */
	record BookApiResponse(String title, String author) {
	}

}
