package wf.garnier.nativedemo.hello;

import org.springframework.stereotype.Service;

@Service
public class GreetingService {

	public String greet(String name) {
		return "Hello " + name + "!";
	}

}
