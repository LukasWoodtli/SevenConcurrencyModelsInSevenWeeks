package day3.wordcount.v4_wordcount_cuncurrent_hashmap;

abstract class Page {
    
    public String getTitle() {throw new UnsupportedOperationException();}
    public String getText() {throw new UnsupportedOperationException();}
    
    boolean isPoisonPill() {return false;}
}
