package day2.dining_philosophers_timeout;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Philosopher extends Thread {
    private ReentrantLock leftChopstick, rightChopstick;
    private Random random;
    private static int philosophersTotalNo = 0;
    private int thisPhilosopherNo;
    
    public Philosopher(ReentrantLock leftChopstick,
            ReentrantLock rightChopstick) {
        this.leftChopstick = leftChopstick;
        this.rightChopstick = rightChopstick;
        random = new Random();
        thisPhilosopherNo = philosophersTotalNo;
        ++philosophersTotalNo;
    }
    
    public void run() {
        try {
            while (true) {
                Thread.sleep(random.nextInt(1000)); // think for a while
                leftChopstick.lock();
                System.out.println("Philosopher " + thisPhilosopherNo + " (" +
                        this + ") " + "picked up left chopstick");
                try {
                    if (rightChopstick.tryLock(1000, TimeUnit.MICROSECONDS)) {
                        System.out.println("Philosopher " + thisPhilosopherNo + " (" +
                            this + ") " + "picked up right chopstick");
                        try {
                            Thread.sleep(random.nextInt(1000));
                            System.out.println("Philosopher " + thisPhilosopherNo +
                                    " (" + this + ") " + "eats for a while");
                        } finally {
                            rightChopstick.unlock();
                        }
                    } else {
                        System.out.println("Philosopher " + thisPhilosopherNo +
                                    " (" + this + ") " + "didn't get chopsticks");
                    }
                        
                } finally {
                    leftChopstick.unlock();
                }
            }
        } catch (InterruptedException e) {
            
        }
    }
};
