package aqs;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchExample {
    private static final int clientNum = 3;

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(clientNum);
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i ++) {
            int finalI = i;
            exec.execute(() -> {
                System.out.println(finalI);
                latch.countDown();
            });
        }
        new Thread(() -> {
            try {
                latch.await();
                System.out.println("thread 1");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                latch.await();
                System.out.println("thread 2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        latch.await();
        exec.shutdown();
    }
}
