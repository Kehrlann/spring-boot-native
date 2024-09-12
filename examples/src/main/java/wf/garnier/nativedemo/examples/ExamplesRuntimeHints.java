package wf.garnier.nativedemo.examples;

import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;

class ExamplesRuntimeHints implements RuntimeHintsRegistrar {

	@Override
	public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
		hints.reflection().registerType(JsonController.BookToSerialize.class, MemberCategory.values());
	}

}
