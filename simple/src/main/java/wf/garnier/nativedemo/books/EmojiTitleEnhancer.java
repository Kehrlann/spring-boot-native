package wf.garnier.nativedemo.books;

class EmojiTitleEnhancer implements TitleEnhancer {

	private final String[] emojis = { "😊", "🐵", "💥", "😱" };

	@Override
	public String enhance(String originalTitle) {
		int sum = 0;
		for (Byte b : originalTitle.getBytes()) {
			sum += b.intValue();
		}
		var emoji = emojis[sum % emojis.length];

		return "%s %s".formatted(emoji, originalTitle);
	}

}
