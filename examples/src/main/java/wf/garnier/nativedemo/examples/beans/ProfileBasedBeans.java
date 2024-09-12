package wf.garnier.nativedemo.examples.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

public class ProfileBasedBeans {

	private static final Logger logger = LoggerFactory.getLogger(ProfileBasedBeans.class);

	public interface LanguageService {

		String getName();

	}

	@Component
	@Profile("!french")
	public static class EnglishLanguageService implements LanguageService {

		public EnglishLanguageService() {
			logger.info("⚙️⚙️⚙️ Bootstrapping {} ({})", getClass().getSimpleName(), getName());
		}

		@Override
		public String getName() {
			return "🫘🇬🇧 ENGLISH BEAN";
		}

	}

	@Component
	@Profile("french")
	public static class FrenchLanguageService implements LanguageService {

		public FrenchLanguageService() {
			logger.info("⚙️⚙️⚙️ Bootstrapping {} ({})", getClass().getSimpleName(), getName());
		}

		@Override
		public String getName() {
			return "🫘🇫🇷 HARICOT FRANCAIS";
		}

	}

}
