package wf.garnier.nativedemo.books;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class HugoAwardsService {

	private final RestClient.Builder restClientBuilder;

	private String upstreamUrl;

	public record AwardWinningBook(String title, String author, Integer year) {
	}

	public record Configuration(String baseUrl, String someEmoji) {
	}

	private RestClient restClient;

	public HugoAwardsService(RestClient.Builder restClientBuilder) {
		this.restClientBuilder = restClientBuilder;
		upstreamUrl = "http://localhost:8000";
		restClient = this.restClientBuilder.baseUrl(upstreamUrl).build();
	}

	public Collection<AwardWinningBook> findAll() {
		return restClient.get()
			.uri("/hugo.json")
			.retrieve()
			.body(ApiResponse.class)
			.results()
			.stream()
			.map(b -> new AwardWinningBook(b.title(), b.author, b.year))
			.toList();
	}

	public Configuration getConfiguration() {
		return new Configuration(upstreamUrl, "🚀📚");
	}

	public void setUpstreamServicePort(int port) {
		this.upstreamUrl = "http://localhost:" + port;
		this.restClient = restClientBuilder.baseUrl(upstreamUrl).build();
	}

	public record ApiResponse(Integer count, List<Entry> results) {
		public record Entry(String title, String author, Integer year) {
		}
	}

}
