package deadLock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class DeadLock implements Runnable{
    private static Object o1 = new Object(), o2 = new Object();
    private CyclicBarrier barrier = new CyclicBarrier(2);
    public int flag;
    @Override
    public void run() {
        if (flag == 1) {
            synchronized (o1) {
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                synchronized (o2) {
                }
            }

        } else if (flag == 2) {
            synchronized (o2) {
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                synchronized (o1) {
                }
            }
        }
    }

    public static void main(String[] args) {
        DeadLock deadLock = new DeadLock();
        deadLock.flag = 1;
        Thread t1 = new Thread(deadLock);
        deadLock.flag = 2;
        Thread t2 = new Thread(deadLock);
        t1.start();
        t2.start();
    }
}
