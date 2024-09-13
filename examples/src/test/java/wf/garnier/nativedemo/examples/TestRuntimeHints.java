package wf.garnier.nativedemo.examples;

import org.mockito.ArgumentMatcher;
import org.mockito.internal.creation.bytebuddy.InlineByteBuddyMockMaker;
import org.mockito.internal.matchers.Any;
import org.mockito.internal.matchers.Equals;
import org.mockito.plugins.MemberAccessor;
import org.mockito.plugins.MockMaker;

import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.ui.Model;
import org.springframework.web.client.RestClient;

class TestRuntimeHints implements RuntimeHintsRegistrar {

	@Override
	public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
		hints.proxies().registerJdkProxy(MockMaker.class);
		hints.proxies().registerJdkProxy(MemberAccessor.class);
		hints.proxies().registerJdkProxy(RestClient.Builder.class);
		hints.proxies().registerJdkProxy(Model.class);
		hints.proxies().registerJdkProxy(RestClient.class);
		hints.proxies().registerJdkProxy(RestClient.RequestHeadersUriSpec.class);
		hints.proxies().registerJdkProxy(RestClient.UriSpec.class);
		hints.proxies().registerJdkProxy(RestClient.RequestHeadersSpec.class);
		hints.proxies().registerJdkProxy(RestClient.ResponseSpec.class);
		hints.reflection().registerType(InlineByteBuddyMockMaker.class, MemberCategory.values());
		hints.reflection().registerType(ArgumentMatcher.class, MemberCategory.values());
		hints.reflection().registerType(Any.class, MemberCategory.values());
		hints.reflection().registerType(Equals.class, MemberCategory.values());
	}

}
