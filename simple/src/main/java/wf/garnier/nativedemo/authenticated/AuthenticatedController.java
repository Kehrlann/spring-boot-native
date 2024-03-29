package wf.garnier.nativedemo.authenticated;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class AuthenticatedController {

	@GetMapping("/authenticated/hello")
	public String helloController(Authentication authentication) {
		return "Hello %s".formatted(authentication.getName());
	}

}
