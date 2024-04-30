package wf.garnier.nativedemo.books;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class HugoAwardsService {

	private final RestClient.Builder restClientBuilder;

	private int port = 8000;

	public record AwardWinningBook(String title, String author, Integer year) {
	}

	private RestClient restClient;

	public HugoAwardsService(RestClient.Builder restClientBuilder) {
		this.restClientBuilder = restClientBuilder;
		restClient = this.restClientBuilder.baseUrl("http://localhost:" + port).build();
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

	public void setUpstreamServicePort(int port) {
		this.port = port;
		this.restClient = restClientBuilder.baseUrl("http://localhost:" + port).build();
	}

	private record ApiResponse(Integer count, List<Entry> results) {
		private record Entry(String title, String author, Integer year) {
		}
	}

}
