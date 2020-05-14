package day3.wordcount.v1_wordcount;

public class Page {
    private String title;
    private String text;
    
    public Page(String title, String text) {
        this.title = title;
        this.text = text;
    }
    
    public String getTitle() {return title;}
    public String getText() {return text;}
}
