package day2.concurrent_linked_list;

import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentSortedList {
    private class Node {
        int value;
        Node prev;
        Node next;
        ReentrantLock lock = new ReentrantLock();
        
        Node() {}
        Node(int value, Node prev, Node next) {
            this.value = value;
            this.prev = prev;
            this.next = next;            
        }
    }
    
    private final Node head;
    private final Node tail;
    
    public ConcurrentSortedList() {
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.prev = head;
    }
    
    public void insert(int value) {
        System.out.println("Start inserting " + value);
        Node current = head;
        current.lock.lock();
        System.out.println("Locking " + current.value);
        Node next = current.next;
        try {
            while (true) {
                next.lock.lock();
                System.out.println("Locking " + next.value);
                try {
                    if (next == tail || next.value < value) {
                        Node node = new Node(value, current, next);
                        next.prev = node;
                        current.next = node;
                        System.out.println("Inserting: " + current.value +
                                           " Current: " + current.value + 
                                           " Next: " + next.value);
                        return;
                    }
                } finally {
                    current.lock.unlock();
                    System.out.println("Un-Locking " + current.value);
                }
                current = next;
                next = current.next;
            }
        } finally {
            next.lock.unlock();
            System.out.println("Un-Locking " + next.value);
        }
    }
    
    public int size() {
        Node current = tail;
        int count = 0;
        
        while (current.prev != head) {
            ReentrantLock lock = current.lock;
            lock.lock();
            try {
                count++;
                current = current.prev;
            } finally {
                lock.unlock();
            }
        }
        
        return count;
    }
    
    public boolean isSorted() {
        Node current = head;
        
        while (current.next.next != tail) {
            current = current.next;
            if (current.value < current.next.value) {
                return false;
            }
        }
        return true;
    }
}
