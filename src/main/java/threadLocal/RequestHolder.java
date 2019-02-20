package threadLocal;

public class RequestHolder {
    private static final ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void add(Long id) {
        threadLocal.set(id);
    }

    public static Long get() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }
}
