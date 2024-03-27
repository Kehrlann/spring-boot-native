package wf.garnier.nativedemo.hello;

import org.springframework.stereotype.Service;

@Service
public class GreetingService {

	private final String template = "Hello %s!";

	public String greet(String name) {
		return template.formatted(name);
	}

}
