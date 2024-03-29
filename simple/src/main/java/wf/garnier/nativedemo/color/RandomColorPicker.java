package wf.garnier.nativedemo.color;

import java.util.Random;

public class RandomColorPicker implements ColorPicker {

	private final Random random = new Random();

	@Override
	public String pick() {
		return colors[random.nextInt(colors.length)];
	}

}
