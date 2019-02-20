package juc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionExample {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        CountDownLatch latch = new CountDownLatch(1);

        new Thread(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
            System.out.println("get lock");
            condition.signalAll();
            System.out.println("send signal");
            lock.unlock();
        }).start();


        new Thread(() -> {
            lock.lock();
            System.out.println("wait signal");
            try {
                latch.countDown();
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("get singla");
            lock.unlock();
        }).start();
    }
}
