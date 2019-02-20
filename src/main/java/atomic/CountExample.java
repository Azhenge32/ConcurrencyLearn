package atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;


@Slf4j
public class CountExample {
    private static final int threadNum = 200;
    private static final int clientNum = 5000;
    private static long count = 0;

    public static void  add() {
        count ++;
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        Semaphore semaphore = new Semaphore(threadNum);
        for (int i = 0; i < clientNum; i ++) {
            exec.execute(() -> {
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        try {
            exec.awaitTermination(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        exec.shutdown();
        System.out.println(count);
    }
}
