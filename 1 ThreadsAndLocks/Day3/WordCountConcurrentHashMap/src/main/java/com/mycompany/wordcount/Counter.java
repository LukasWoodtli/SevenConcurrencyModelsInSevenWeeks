/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.wordcount;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Boot
 * 
 * Consumer
 */
public class Counter implements Runnable {
    private BlockingQueue<Page> queue;
    private ConcurrentHashMap<String, Integer> counts;
    
    public Counter(BlockingQueue<Page> queue, ConcurrentHashMap<String, Integer> counts) {
        this.queue = queue;
        this.counts = counts;
    }
    
    public void run() {
        try {
            while (true) {
                Page page = queue.take();
                if (page.isPoisonPill()) {
                    System.out.println("Poison pill consumed. Thread: " + this);
                    break;
                }
                
                Iterable<String> words = new Words(page.getText());
                for (String word: words) {
                    countWord(word);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void countWord(String word) {
        while (true) {
            Integer currentCount  = counts.get(word);
            if (currentCount == null) {
                if (counts.putIfAbsent(word, 1) == null) {
                    break;
                }
            } else if (counts.replace(word, currentCount, currentCount + 1)) {
                break;
            }
        }
    }
}
