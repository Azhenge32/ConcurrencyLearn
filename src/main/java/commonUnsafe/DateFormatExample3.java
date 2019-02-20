package commonUnsafe;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class DateFormatExample3 {
    private static DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMdd");
    private static final int threadNum = 200;
    private static final int clientNum = 5000;
    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        Semaphore semaphore = new Semaphore(threadNum);
        CountDownLatch latch = new CountDownLatch(clientNum);
        for (int i = 0; i < clientNum; i ++) {
            exec.execute(() -> {
                try {
                    semaphore.acquire();
                    latch.countDown();
                    doSomething();
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        latch.await();
        exec.shutdown();
    }

    public static void doSomething() {
        System.out.println(DateTime.parse("20180208", formatter).toDate());
    }
}
