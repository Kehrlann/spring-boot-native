package wf.garnier.nativedemo.examples.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

public class ConditionalBasedBeans {

	private static final Logger logger = LoggerFactory.getLogger(ConditionalBasedBeans.class);

	public interface MoodService {

		String getMood();

	}

	@Component
	@ConditionalOnProperty(value = "app.mood", havingValue = "happy", matchIfMissing = true)
	public static class HappyService implements MoodService {

		public HappyService() {
			logger.info("⚙️⚙️⚙️ Bootstrapping {} ({})", getClass().getSimpleName(), getMood());
		}

		@Override
		public String getMood() {
			return "😁 HAPPY";
		}

	}

	@Component
	@ConditionalOnProperty(value = "app.mood", havingValue = "sad", matchIfMissing = false)
	public static class SadService implements MoodService {

		public SadService() {
			logger.info("⚙️⚙️⚙️ Bootstrapping {} ({})", getClass().getSimpleName(), getMood());
		}

		@Override
		public String getMood() {
			return "☹️ SAD";
		}

	}

}
