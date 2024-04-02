package wf.garnier.nativedemo.authenticated;

import org.springframework.aot.hint.ExecutableMode;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.security.core.Authentication;
import org.springframework.util.ReflectionUtils;

public class AuthenticatedRuntimeHints implements RuntimeHintsRegistrar {

	@Override
	public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
		hints.reflection()
			.registerMethod(ReflectionUtils.findMethod(AclService.class, "isAllowed", Authentication.class),
					ExecutableMode.INVOKE);
	}

}
