package day3.wordcount.v2_wordcount_producer_consumer;

abstract class Page {
    
    public String getTitle() {throw new UnsupportedOperationException();}
    public String getText() {throw new UnsupportedOperationException();}
    
    boolean isPoisonPill() {return false;}
}
