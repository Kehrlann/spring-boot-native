package wf.garnier.nativedemo.hello;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class HelloConfiguration {

	@Bean
	@ConditionalOnProperty(value = "greeting.lang", havingValue = "en", matchIfMissing = true)
	public GreetingService greetingService() {
		return new GreetingService();
	}

	@Bean
	@ConditionalOnProperty(value = "greeting.lang", havingValue = "fr", matchIfMissing = false)
	public GreetingService frenchGreetingService() {
		return new GreetingService("Bonjour %s");
	}

}
