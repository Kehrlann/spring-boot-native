package wf.garnier.nativedemo.color;

/**
 * Not so thread-safe, eh?
 */
class RoundRobinColorPicker implements ColorPicker {

	private int position = -1;

	@Override
	public String pick() {
		position = ++position % colors.length;
		return colors[position];
	}

}
