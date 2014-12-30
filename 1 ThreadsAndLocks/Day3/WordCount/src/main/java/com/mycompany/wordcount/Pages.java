/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.wordcount;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.NoSuchElementException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author Boot
 */
public class Pages implements Iterable<Page>{
    
    private final int maxPages;
    private final String fileName;
    
    public Pages(int maxPages, String fileName) {
        this.maxPages = maxPages;
        this.fileName = fileName;
    }
    
    private class PageIterator implements Iterator<Page> {
        private XMLEventReader reader;
        private int remainingPages;
        
        public PageIterator() throws Exception {
            remainingPages = maxPages;
            reader = XMLInputFactory.newInstance().createXMLEventReader(new FileInputStream(fileName));
        }
    
        public boolean hasNext() {return remainingPages > 0;}
        
        public Page next() {
            try {
                XMLEvent event;
                String title = "";
                String text = "";
                
                while (true) {
                    event = reader.nextEvent();
                    if (event.isStartElement()) {
                        if (event.asStartElement().getName().getLocalPart().equals("page")) {
                            while (true) {
                                event = reader.nextEvent();
                                if (event.isStartElement()) {
                                    String name = event.asStartElement().getName().getLocalPart();
                                    if (name.equals("title")) {
                                        title = reader.getElementText();
                                    } else if (name.equals("text")) {
                                        text = reader.getElementText();
                                    } else if (event.isEndElement()) {
                                        if (event.asEndElement().getName().getLocalPart().equals("page")) {
                                            --remainingPages;
                                            return new Page(title, text);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {}
            throw new NoSuchElementException();
        }
        
        public void remove() {throw new UnsupportedOperationException();}
        
    }
    
    public Iterator<Page> iterator() {
        try {
            return new PageIterator();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
