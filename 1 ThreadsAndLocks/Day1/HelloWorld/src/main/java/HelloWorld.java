/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class HelloWorld {

  public static void main(String[] args) throws InterruptedException {
    Thread myThread = new Thread() {
                 public void run() {
                System.out.println("Hello from new thread!");
            }
        };
       
        myThread.start();
        Thread.yield();
        System.out.println("Hello from main thread!");
        myThread.join();
    }
}
