package day3.wordcount.v2_wordcount_producer_consumer;

import day3.wordcount.common.Page;
import day3.wordcount.common.Parser;
import day3.wordcount.common.PoisonPill;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;


public class WordCount {
    private static final int NUM_COUNTERS = 4;
    
    public static void main(String[] arg) throws Exception {
        ArrayBlockingQueue<Page> queue = new ArrayBlockingQueue<>(100);
        HashMap<String, Integer> counts = new HashMap<>();

        Thread counter = new Thread(new Counter(queue, counts));
        Thread parser = new Thread(new Parser(queue));
        
        long start = System.currentTimeMillis();
        counter.start();
        parser.start();
        parser.join();
        queue.put(new PoisonPill());
        counter.join();
        long end = System.currentTimeMillis();
        System.out.println("Elapsed time: " + (end -start) + " ms");

        counts.forEach((k, v)->System.out.println("'"+k+"': " + v));

    } 
}
