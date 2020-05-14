package day3.wordcount.v2_wordcount_producer_consumer;

import java.util.concurrent.BlockingQueue;

public class Parser implements Runnable {
    private BlockingQueue<Page> queue;
    
    public Parser(BlockingQueue queue) {
        this.queue = queue;
    }
    
    public void run() {
        try {
            Iterable<Page> pages = new Pages(1000, "enwiki.xml");
            for (Page page: pages) {
                queue.put(page);
            } 
        } catch (Exception e) { e.printStackTrace(); }
    }
}
