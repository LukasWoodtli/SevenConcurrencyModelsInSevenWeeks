package day3.wordcount.v5_wordcout_batch_concurrent_hashmap;

import day3.wordcount.common.Page;
import day3.wordcount.common.Parser;
import day3.wordcount.common.PoisonPill;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WordCount {
    private static final int NUM_COUNTERS = 4;
    
    public static void main(String[] arg) throws Exception {
        ArrayBlockingQueue<Page> queue = new ArrayBlockingQueue<>(100);
        ConcurrentHashMap<String, Integer> counts = new ConcurrentHashMap<>();
        ExecutorService executor = Executors.newCachedThreadPool();
        
        for (int i = 0; i< NUM_COUNTERS; ++i) {
            executor.execute(new Counter(queue, counts));
        }
        
        Thread parser = new Thread(new Parser(queue));
        
        long start = System.currentTimeMillis();
        parser.start();
        parser.join();
        for (int i=0; i < NUM_COUNTERS; ++i) {
            queue.put(new PoisonPill());
        }
        
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.MINUTES);
        long end = System.currentTimeMillis();
        System.out.println("Elapsed time: " + (end -start) + " ms");
        
        for (Map.Entry<String, Integer> e: counts.entrySet()) {
            System.out.println(e);
        }       
    } 
}
