package day3.wordcount.common;

import java.util.concurrent.BlockingQueue;

public class Parser implements Runnable {
    private final BlockingQueue<Page> queue;

    public Parser(BlockingQueue queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            Iterable<Page> pages = new Pages(1000, "enwiki.xml");
            for (Page page : pages) {
                queue.put(page);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
