package juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierExample1 {
    private static CyclicBarrier barrier = new CyclicBarrier(6, () -> {
        System.out.println("callback is running");
    });

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i ++) {
            exec.execute(() -> {
                System.out.println("ready");
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println("finish");
            });
        }
        barrier.await();
        exec.shutdown();
    }
}
