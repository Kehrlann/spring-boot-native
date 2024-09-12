package wf.garnier.nativedemo.examples;

import java.util.Arrays;

import wf.garnier.nativedemo.examples.beans.ConditionalBasedBeans;
import wf.garnier.nativedemo.examples.beans.ProfileBasedBeans;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class BeansController {

	private final ProfileBasedBeans.LanguageService languageService;

	private final ConditionalBasedBeans.MoodService moodService;

	private final Environment environment;

	public BeansController(ProfileBasedBeans.LanguageService languageService,
			ConditionalBasedBeans.MoodService moodService, Environment environment) {
		this.languageService = languageService;
		this.moodService = moodService;
		this.environment = environment;
	}

	@GetMapping("/beans")
	public String beans(Model model) {
		model.addAttribute("profiles", Arrays.toString(environment.getActiveProfiles()));
		model.addAttribute("languageService", languageService.getName());
		model.addAttribute("appMood", environment.getProperty("app.mood"));
		model.addAttribute("moodService", moodService.getMood());
		return "beans";
	}

}
