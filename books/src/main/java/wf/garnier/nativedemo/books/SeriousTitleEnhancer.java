package wf.garnier.nativedemo.books;

public class SeriousTitleEnhancer implements TitleEnhancer {

	@Override
	public String enhance(String originalTitle) {
		return "\"%s\"".formatted(originalTitle);
	}

}
