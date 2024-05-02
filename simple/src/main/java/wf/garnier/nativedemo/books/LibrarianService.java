package wf.garnier.nativedemo.books;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
class LibrarianService {

	public boolean isLibrarian(Authentication authentication) {
		return authentication.getName().toLowerCase().contains("a");
	}

}
