/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package day3.wordcount.v3_wordcount_synchronized_hashmap;

import java.util.concurrent.BlockingQueue;

public class Parser implements Runnable {
    private BlockingQueue<Page> queue;
    
    public Parser(BlockingQueue queue) {
        this.queue = queue;
    }
    
    public void run() {
        try {
            Iterable<Page> pages = new Pages(100000, "enwiki.xml");
            for (Page page: pages) {
                queue.put(page);
            } 
        } catch (Exception e) { e.printStackTrace(); }
    }
}
