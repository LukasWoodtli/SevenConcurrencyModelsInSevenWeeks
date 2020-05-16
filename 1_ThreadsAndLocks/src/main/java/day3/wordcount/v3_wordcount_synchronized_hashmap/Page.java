package day3.wordcount.v3_wordcount_synchronized_hashmap;

abstract class Page {
    
    public String getTitle() {throw new UnsupportedOperationException();}
    public String getText() {throw new UnsupportedOperationException();}
    
    boolean isPoisonPill() {return false;}
}
