package wf.garnier.nativedemo.books;

import java.util.Map;

import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.TypeReference;

class BookRuntimeHints implements RuntimeHintsRegistrar {

	@Override
	public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
		// Required for book.html template
		hints.reflection().registerType(Book.class, MemberCategory.PUBLIC_FIELDS, MemberCategory.INVOKE_PUBLIC_METHODS);
		hints.reflection().registerType(TitleEnhancer.class, MemberCategory.INVOKE_PUBLIC_METHODS);
		hints.reflection().registerType(EmojiTitleEnhancer.class, MemberCategory.INVOKE_PUBLIC_METHODS);
		hints.reflection().registerType(SeriousTitleEnhancer.class, MemberCategory.INVOKE_PUBLIC_METHODS);

		// book.html: we iterate over entries in the map(s) and use .getKey and .getValue
		hints.reflection()
			.registerType(TypeReference.of("java.util.HashMap$Node"), MemberCategory.INVOKE_DECLARED_METHODS);
		hints.reflection()
			.registerType(TypeReference.of("java.util.TreeMap$Entry"), MemberCategory.INVOKE_DECLARED_METHODS);

		// book.html: thymeleaf's SpEL integration calls into Map.Entry.getKey /
		// Map.Entry.getValue
		// see:
		// ThymeleafEvaluationContext.ThymeleafEvaluationContextACLMethodResolver.resolve
		// and ExpressionUtils.isMemberAllowedForInstanceOfType
		hints.reflection()
			.registerType(Map.Entry.class, MemberCategory.INTROSPECT_DECLARED_METHODS,
					MemberCategory.INVOKE_DECLARED_METHODS);

		// Required for xpath in tests, through StringBufferPool
		// TODO: put in a test-focused runtime hints class
		hints.reflection()
			.registerType(TypeReference.of("com.sun.org.apache.xml.internal.utils.FastStringBuffer"),
					MemberCategory.INVOKE_DECLARED_CONSTRUCTORS);
		// Note that if this is missing you get a separate error, pointing to a missing
		// resource bundle, fixed
		// by adding the line below:
		// hints.resources().registerResourceBundle("com.sun.org.apache.xml.internal.res.XMLErrorResources");
	}

}
