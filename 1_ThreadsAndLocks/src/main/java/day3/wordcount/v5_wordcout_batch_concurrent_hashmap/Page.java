package day3.wordcount.v5_wordcout_batch_concurrent_hashmap;

abstract class Page {
    
    public String getTitle() {throw new UnsupportedOperationException();}
    public String getText() {throw new UnsupportedOperationException();}
    
    boolean isPoisonPill() {return false;}
}
