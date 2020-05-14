package day3.wordcount.v1_wordcount;

import java.util.HashMap;

public class WordCount {
    private static final HashMap<String, Integer> counts =
            new HashMap<String, Integer>();
    
    public static void main(String[] arg) throws Exception {
        Iterable<Page> pages = new Pages(10000, "enwiki-latest-pages-articles26.xml");
        for (Page page: pages) {
            Iterable<String> words = new Words(page.getText());
            for (String word: words) {
                countWord(word);
            }
        }

        counts.forEach((k,v)->System.out.println("'" + k + "'" + ": " + v.toString()));
    }
    
    public static void countWord(String word) {
        Integer currentCount = counts.get(word);
        if (currentCount == null) {
            counts.put(word, 1);
        } else {
            counts.put(word, currentCount + 1);
        }
        
    }
    
}
