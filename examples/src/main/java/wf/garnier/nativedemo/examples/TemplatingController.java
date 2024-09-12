package wf.garnier.nativedemo.examples;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class TemplatingController {

	@GetMapping("/templating")
	public String templating(Model model) {
		//@formatter:off
		model.addAttribute(
				"books",
				List.of(
						new Book("Jonathan Strange & Mr Norrell"),
						new Book("The Three-Body Problem"),
						new Book("The Fifth Season")
				)
		);
		//@formatter:on;
		return "templating";
	}

	record Book(String title) {

	}

}
