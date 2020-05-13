package day2.dining_philosophers_timeout;

import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosophers {
    public static void main(String[] args) {
        Philosopher[] philosophers = new Philosopher[5];
        ReentrantLock[] chopsticks = new ReentrantLock[5];
        
        for (int i = 0; i < 5; ++i) {
            chopsticks[i] = new ReentrantLock();
        }
        for (int i = 0; i < 5; ++i) {
            philosophers[i] = 
                   new Philosopher(chopsticks[i], chopsticks[(i + 1) % 5]);
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
