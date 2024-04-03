package wf.garnier.nativedemo.hello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	private final GreetingService greetingService;

	private final String bonjour;

	public HelloController(GreetingService greetingService, @Value("${greeting_bonjour:Bonjour!}") String bonjour) {
		this.greetingService = greetingService;
		this.bonjour = bonjour;
	}

	@GetMapping("/hello")
	public String hello(String name) {
		return greetingService.greet(name);
	}

	@GetMapping("/bonjour")
	public String bonjour() {
		return bonjour;
	}

}
