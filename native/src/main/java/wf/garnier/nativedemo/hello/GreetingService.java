package wf.garnier.nativedemo.hello;

public class GreetingService {

	private final String template = "Hello %s!";

	public String greet(String name) {
		return template.formatted(name);
	}

}
