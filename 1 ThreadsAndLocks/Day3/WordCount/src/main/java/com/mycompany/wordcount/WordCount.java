/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.wordcount;

import java.util.HashMap;

/**
 *
 * @author Boot
 */
public class WordCount {
    private static final HashMap<String, Integer> counts =
            new HashMap<String, Integer>();
    
    public static void main(String[] arg) throws Exception {
        Iterable<Page> pages = new Pages(100000, "enwiki.xml");
        for (Page page: pages) {
            Iterable<String> words = new Words(page.getText());
            for (String word: words) {
                countWord(word);
            }
        }
    }
    
    public static void countWord(String word) {
        Integer currentCount = counts.get(word);
        if (currentCount == null) {
            counts.put(word, 1);
        } else {
            counts.put(word, currentCount + 1);
        }
        
    }
    
}
