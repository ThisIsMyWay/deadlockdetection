package com.thisismyway.deadlockdetection;

import java.util.concurrent.locks.Lock;

public class FirstThread implements Runnable {

    private Lock firstLock;
    private Lock secondLock;

    public FirstThread(Lock firstLock, Lock secondLock) {
        this.firstLock = firstLock;
        this.secondLock = secondLock;
    }

    @Override
    public void run() {
        System.out.println("FirstThread - trying to acquire first lock");
        firstLock.lock();
        System.out.println("FirstThread - first lock acquired");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            //nothing to do
        }

        System.out.println("FirstThread - trying to acquire second lock");
        secondLock.lock();
        System.out.println("FirstThread - second lock acquired");

        // some work which needs to be done

        System.out.println("FirstThread - unlocking first lock");
        firstLock.unlock();
        System.out.println("FirstThread - first lock unlocked");

        System.out.println("FirstThread - unlocking second lock");
        secondLock.unlock();
        System.out.println("FirstThread - second lock unlocked");
    }
}
