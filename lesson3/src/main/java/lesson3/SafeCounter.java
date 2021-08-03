package lesson3;

import java.util.concurrent.locks.ReentrantLock;

public class SafeCounter implements Runnable {
    static int counter = 1; // a global counter

    static ReentrantLock counterLock = new ReentrantLock(true);

    static void incrementCounter(){
        counterLock.lock();

        try{
            System.out.println(Thread.currentThread().getName() + ": " + counter);
            counter++;
        }finally{
            counterLock.unlock();
        }
    }

    @Override
    public void run() {
        while(counter<50){
            incrementCounter();
        }
    }

    public static void main(String[] args) {
        SafeCounter safeCounter = new SafeCounter();
        Thread thread1 = new Thread(safeCounter);
        Thread thread2 = new Thread(safeCounter);

        thread1.start();
        thread2.start();
    }
}
