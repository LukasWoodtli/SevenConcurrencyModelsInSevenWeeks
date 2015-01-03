/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.wordcount;

/**
 *
 * @author Boot
 */
abstract class Page {
    
    public String getTitle() {throw new UnsupportedOperationException();}
    public String getText() {throw new UnsupportedOperationException();}
    
    boolean isPoisonPill() {return false;}
}
