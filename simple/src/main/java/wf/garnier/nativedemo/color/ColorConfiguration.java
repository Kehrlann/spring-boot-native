package wf.garnier.nativedemo.color;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
class ColorConfiguration {

	@Bean
	@Profile("!random")
	public ColorPicker colorPicker() {
		return new RoundRobinColorPicker();
	}

	@Bean
	@Profile("random")
	public ColorPicker randomColorPicker() {
		return new RandomColorPicker();
	}

}
