package wf.garnier.nativedemo.starwars;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RegisterReflectionForBinding({ StarWarsController.SwapiCharacter.class, StarWarsController.SwapiResponse.class,
		StarWarsConfiguration.WelcomeMessage.class })
class StarWarsConfiguration {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Bean
	public ApplicationListener<ServletWebServerInitializedEvent> listener() {
		return event -> {
			try {
				var message = new WelcomeMessage(event.getWebServer().getPort());
				System.out.println(objectMapper.writeValueAsString(message));
			}
			catch (JsonProcessingException e) {
				throw new RuntimeException(e);
			}
		};
	}

	record WelcomeMessage(String message, String hint, int port, String emoji) {

		public WelcomeMessage(int port) {
			this("The webserver is running", "Better go catch it, lol", port, "🏃🏃🏃🏃🏃🏃🏃🏃");
		}
	}

}
