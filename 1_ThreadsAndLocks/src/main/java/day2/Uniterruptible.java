package day2;

public class Uniterruptible {
    public static void main(String[] args) throws InterruptedException {
        final Object o1 = new Object();
        final Object o2 = new Object();
    
        Thread t1 = new Thread() {
            public void run() {
                try {
                    synchronized(o1) {
                        System.out.println("t1 started");
                        Thread.sleep(1000);
                        synchronized(o2) {}
                    }
                } catch (InterruptedException e) {
                    System.out.println("t1 interrupted");
                }
            }
        };
        
        Thread t2 = new Thread() {
            public void run() {
                try {
                    synchronized(o2) {
                        System.out.println("t2 started");
                        Thread.sleep(1000);
                        synchronized(o1) {}
                    }
                } catch (InterruptedException e) {
                    System.out.println("t2 interrupted");
                }
            }
        };
        
        t1.start();
        t2.start();
        Thread.sleep(2000);
        t1.interrupt();
        t2.interrupt();
        t1.join();
        t2.join();
    }
}
