package wf.garnier.nativedemo.books;

/**
 * Enhances the title, so it shows nicely on the page.
 */
public interface TitleEnhancer {

    /**
     * Take the original title, and make it better.
     * @param originalTitle -
     * @return a better title!
     */
    public String enhance(String originalTitle);
}
