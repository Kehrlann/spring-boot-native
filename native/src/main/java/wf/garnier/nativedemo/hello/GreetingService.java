package wf.garnier.nativedemo.hello;

import org.springframework.beans.factory.annotation.Value;

public class GreetingService {

	private final String template;

	public GreetingService() {
		this("Hello %s!");
	}

	public GreetingService(String template) {
		this.template = template;
	}

	public String greet(String name) {
		return template.formatted(name);
	}

}
