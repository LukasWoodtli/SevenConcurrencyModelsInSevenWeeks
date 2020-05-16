package day3.wordcount.v4_wordcount_cuncurrent_hashmap;

public class WikiPage extends Page {
    private String title;
    private String text;
    
    public WikiPage(String title, String text) {
        this.title = title;
        this.text = text;
    }
    
    public String getTitle() {return title;}
    public String getText() {return text;}
}
