package wf.garnier.nativedemo.books;

import org.springframework.aot.hint.ExecutableMode;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.security.core.Authentication;
import org.springframework.util.ReflectionUtils;

class SecurityRuntimeHints implements RuntimeHintsRegistrar {

	@Override
	public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
		var method = ReflectionUtils.findMethod(LibrarianService.class, "isLibrarian", Authentication.class);

		hints.reflection().registerMethod(method, ExecutableMode.INVOKE);
	}

}
