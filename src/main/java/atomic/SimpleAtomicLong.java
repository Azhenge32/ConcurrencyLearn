package atomic;

import sun.misc.Unsafe;

public class SimpleAtomicLong {
    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private volatile long value;
    private static final long valueOffset;
    static {
        try {
            valueOffset = unsafe.objectFieldOffset
                    (SimpleAtomicLong.class.getDeclaredField("value"));
        } catch (Exception ex) { throw new Error(ex); }
    }
    public SimpleAtomicLong(long initialValue) {
        value = initialValue;
    }

    public final long addAndGet(long delta) {
        return unsafe.getAndAddLong(this, valueOffset, delta) + delta;
    }
}
