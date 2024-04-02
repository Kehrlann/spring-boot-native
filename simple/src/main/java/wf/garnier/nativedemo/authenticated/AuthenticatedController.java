package wf.garnier.nativedemo.authenticated;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class AuthenticatedController {

	@GetMapping("/authenticated/hello")
	@PreAuthorize("hasRole('admin')")
	public String helloController(Authentication authentication) {
		return "Hello %s".formatted(authentication.getName());
	}

	@GetMapping("/authenticated/a-team")
	@PreAuthorize("@aclService.isAllowed(authentication)")
	public String aTeam() {
		return "You are part of the A team!";
	}

}
