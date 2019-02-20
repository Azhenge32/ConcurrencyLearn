package atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAdder;

public class LongAddrTest {
    private static final Integer clientNum = 1_000_000;
    private static LongAdder longAdder = new LongAdder();
    public static void main1(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(clientNum);
        for (int i = 0; i < clientNum; i ++) {
            exec.execute(() -> {
                longAdder.increment();
                latch.countDown();
            });
        }

        latch.await();
        exec.shutdown();
        System.out.println(longAdder.longValue());
    }

    public static void main(String[] args) {
        LongAdder longAdder = new LongAdder();
        longAdder.increment();
        System.out.println(longAdder.longValue());
    }
}
