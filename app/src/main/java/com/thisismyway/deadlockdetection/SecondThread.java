package com.thisismyway.deadlockdetection;

import java.util.concurrent.locks.Lock;

public class SecondThread implements Runnable {

    private Lock firstLock;
    private Lock secondLock;

    public SecondThread(Lock firstLock, Lock secondLock) {
        this.firstLock = firstLock;
        this.secondLock = secondLock;
    }

    @Override
    public void run() {
        System.out.println("SecondThread - trying to acquire second lock");
        secondLock.lock();
        System.out.println("SecondThread - second lock acquired");

        try {
            // thread sleep to be sure that deadlock will happen
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            //nothing to do
        }

        System.out.println("SecondThread - trying to acquire first lock");
        firstLock.lock();
        System.out.println("SecondThread - first lock acquired");

        // some work which needs to be done

        System.out.println("SecondThread - unlocking second lock");
        secondLock.unlock();
        System.out.println("SecondThread - second lock unlocked");

        System.out.println("SecondThread - unlocking first lock");
        firstLock.unlock();
        System.out.println("SecondThread - first lock unlocked");
    }
}
