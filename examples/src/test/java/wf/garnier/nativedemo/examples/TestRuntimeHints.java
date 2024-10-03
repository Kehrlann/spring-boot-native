package wf.garnier.nativedemo.examples;

import org.mockito.ArgumentMatcher;
import org.mockito.internal.matchers.Any;
import org.mockito.internal.matchers.Equals;
import org.mockito.plugins.MemberAccessor;
import org.mockito.plugins.MockMaker;

import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.ui.Model;

class TestRuntimeHints implements RuntimeHintsRegistrar {

	@Override
	public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
		hints.proxies().registerJdkProxy(MockMaker.class);
		hints.proxies().registerJdkProxy(MemberAccessor.class);
		hints.proxies().registerJdkProxy(Model.class);
		hints.reflection().registerType(ArgumentMatcher.class, MemberCategory.values());
		hints.reflection().registerType(Any.class, MemberCategory.values());
		hints.reflection().registerType(Equals.class, MemberCategory.values());
	}

}
