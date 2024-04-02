package wf.garnier.nativedemo.authenticated;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AclService {

	public boolean isAllowed(Authentication authentication) {
		return authentication.getName().toLowerCase().contains("a");
	}

}
