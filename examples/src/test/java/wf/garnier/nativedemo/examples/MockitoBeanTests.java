package wf.garnier.nativedemo.examples;

import org.junit.jupiter.api.Test;
import wf.garnier.nativedemo.examples.beans.ProfileBasedBeans;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisabledInAotMode
class MockitoBeanTests {

	// This fails in AOT mode
	@MockitoBean
	ProfileBasedBeans.LanguageService languageService;

	@Test
	void loads() {
		// can't use ProxyMockMaker, this is a concrete type
		when(languageService.getName()).thenReturn("this is a mock");
		assertThat(languageService.getName()).isEqualTo("this is a mock");
	}

}
