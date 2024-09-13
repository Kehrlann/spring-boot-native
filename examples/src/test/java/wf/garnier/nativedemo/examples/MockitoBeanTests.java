package wf.garnier.nativedemo.examples;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisabledInAotMode
@Disabled
class MockitoBeanTests {

	// This fails in AOT mode
	@MockitoBean
	JsonController jsonController;

	@Test
	void loads() {
		// can't use ProxyMockMaker, this is a concrete type
		when(jsonController.readJson(any())).thenReturn("this is a mock");
		assertThat(jsonController.readJson(null)).isEqualTo("this is a mock");
	}

}
