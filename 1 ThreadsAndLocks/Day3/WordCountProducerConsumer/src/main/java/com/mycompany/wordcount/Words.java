/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.wordcount;

import com.apple.jobjc.Utils.Strings;
import java.text.BreakIterator;
import java.util.Iterator;

/**
 *
 * @author Boot
 */
public class Words implements Iterable<String> {
    private final String text;
    public Words(String text) {
        this.text = text;
    }
    
    private class WordIterator implements Iterator<String> {
        private BreakIterator wordBoundary;
        private int start;
        private int end;
        
        public WordIterator() {
            wordBoundary = BreakIterator.getWordInstance();
            wordBoundary.setText(text);
            start = wordBoundary.first();
            end = wordBoundary.next();
        }
        
        public boolean hasNext() {return end != BreakIterator.DONE;}
        
        public String next() {
            String s = text.substring(start, end);
            start = end;
            end = wordBoundary.next();
            return s;
        }
        
        public void remove() {throw new UnsupportedOperationException();}

    }
    
    @Override
    public Iterator<String> iterator() {return new WordIterator();}
}
