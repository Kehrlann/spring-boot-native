package wf.garnier.nativedemo.color;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ColorController {

    private final ColorPicker colorPicker;

    public ColorController(ColorPicker colorPicker) {
        this.colorPicker = colorPicker;
    }

    @GetMapping("/color")
    public String getColor() {
        return colorPicker.pick();
    }
}
