package wf.garnier.nativedemo.examples;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class IndexController {

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("now", LocalTime.now().truncatedTo(ChronoUnit.SECONDS));
		return "index";
	}

}
