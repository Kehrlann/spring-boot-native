package wf.garnier.nativedemo.examples;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class AuthorizationController {

    @PreAuthorize("@authorizationService.isAllowed(authentication)")
    @GetMapping("/authorization")
    public String authorization(Model model) {
        return "authorization";
	}
}
