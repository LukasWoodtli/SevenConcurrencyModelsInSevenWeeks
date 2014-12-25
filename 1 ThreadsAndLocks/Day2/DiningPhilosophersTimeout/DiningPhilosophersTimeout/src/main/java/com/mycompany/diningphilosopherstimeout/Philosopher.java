/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.diningphilosopherstimeout;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Boot
 */
public class Philosopher extends Thread {
    private ReentrantLock leftChopstick, rightChopstick;
    private Random random;
    
    public Philosopher(ReentrantLock leftChopstick,
            ReentrantLock rightChopstick) {
        this.leftChopstick = leftChopstick;
        this.rightChopstick = rightChopstick;
        random = new Random();
    }
    
    public void run() {
        try {
            while (true) {
                Thread.sleep(random.nextInt(1000)); // think for a while
                leftChopstick.lock();
                try {
                    if (rightChopstick.tryLock(1000, TimeUnit.MICROSECONDS)) {
                        // got right chopstick
                        try {
                            Thread.sleep(random.nextInt(1000));
                            // Eat for a while
                        } finally {
                            rightChopstick.unlock();
                        }
                    } else {
                        // Didn't get the right chopstick - give up and go back to thinking
                    }
                        
                } finally {
                    leftChopstick.unlock();
                }
            }
        } catch (InterruptedException e) {
            
        }
    }
};
