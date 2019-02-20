package atomic;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicLongTest {
    public static void main(String[] args) {
        AtomicLong atomicLong = new AtomicLong(0);
        atomicLong.addAndGet(1);
        atomicLong.compareAndSet(0, 1);
    }
}
