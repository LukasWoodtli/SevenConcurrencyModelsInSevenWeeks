/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.diningphilosopherscondition;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Boot
 */
public class Philosopher extends Thread {
    private boolean eating;
    private Philosopher left;
    private Philosopher right;
    private ReentrantLock table;
    private Condition condition;
    private Random random;
    private static int totalPhilosophersNo = 0;
    private int thisPhilosophersNo;
    
    public Philosopher(ReentrantLock table) {
        eating = false;
        this.table = table;
        condition = table.newCondition();
        random = new Random();
        thisPhilosophersNo = totalPhilosophersNo;
        ++totalPhilosophersNo;
    }
    
    public void setLeft(Philosopher left) {
        this.left = left;
    }
    
    public void setRight(Philosopher right) {
        this.right = right;
    }
    
    public void run() {
        try {
            while (true) {
                think();
                eat();
            }
        } catch (InterruptedException e) {}
    }
    
    private void think() throws InterruptedException  {
        table.lock();
        try {
            eating = false;
            System.out.println("Philosopher " + thisPhilosophersNo + " is thinking");
            left.condition.signal();
            right.condition.signal();
        } finally {
            table.unlock();
        }
        Thread.sleep(1000);
    }
    
    private void eat() throws InterruptedException {
        table.lock();
        try {
            while (left.eating || right.eating) {
                condition.await(); // releases table lock
            }
            eating = true;
            System.out.println("Philosopher " + thisPhilosophersNo + " is eating");
        } finally {
            table.unlock();
        }
        Thread.sleep(1000);
    }
}
