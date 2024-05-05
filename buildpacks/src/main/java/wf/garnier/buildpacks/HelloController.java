package wf.garnier.buildpacks;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class HelloController {

    @GetMapping
    public String hello() {
        return "Hello World!";
    }
}
