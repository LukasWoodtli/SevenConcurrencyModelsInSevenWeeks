package day3.wordcount.common;

public class WikiPage extends Page {
    private final String title;
    private final String text;

    public WikiPage(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}
