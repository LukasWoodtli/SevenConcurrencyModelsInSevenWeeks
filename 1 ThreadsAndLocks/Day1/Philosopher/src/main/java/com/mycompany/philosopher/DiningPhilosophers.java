/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.philosopher;

/**
 *
 * @author Boot
 */
public class DiningPhilosophers {
    public static void main(String[] args) {
        Philosopher[] philosophers = new Philosopher[5];
        Chopstick[] chopsticks = new Chopstick[5];
        
        for (int i = 0; i < 5; ++i) {
            chopsticks[i] = new Chopstick(i);
        }
        for (int i = 0; i < 5; ++i) {
            philosophers[i] = new Philosopher(chopsticks[i], chopsticks[(i + 1) % 5]);
            philosophers[i].start();
        }
        for (int i = 0; i < 5; ++i) {
            try {
                philosophers[i].join();
            }
            catch (InterruptedException e) {}
        }
    }
}
