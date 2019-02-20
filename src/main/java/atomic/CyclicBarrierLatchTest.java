package atomic;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierLatchTest {
    public static void main(String[] args) throws InterruptedException {
        CyclicBarrierLatchTest latchTest = new CyclicBarrierLatchTest();
        latchTest.startNThreadsByBarrier(5);
    }

    public void startNThreadsByBarrier(int threadNums) throws InterruptedException {
        // 设置栅栏解除时的动作，比如初始化某些值
        CyclicBarrier barrier = new CyclicBarrier(threadNums,  new Runnable() {
            private int iCounter;
            @Override
            public void run() {
                iCounter++;
                System.out.println(System.nanoTime() + " [" + Thread.currentThread().getName() + "] iCounter = " + iCounter);
            }
        });
        // 启动 n 个线程，与栅栏阀值一致，即当线程准备数达到要求时，栅栏刚好开启，从而达到统一控制效果
        for (int i = 0; i < threadNums; i++) {
            Thread.sleep(100);
            new Thread(new CounterTask(barrier)).start();
        }
        System.out.println(Thread.currentThread().getName() + " out over...");
    }
}

class CounterTask implements Runnable {

    // 传入栅栏，一般考虑更优雅方式
    private CyclicBarrier barrier;

    public CounterTask(final CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    public void run() {
        System.out.println(Thread.currentThread().getName() + " - " + System.currentTimeMillis() + " is ready...");
        try {
            // 设置栅栏，使在此等待，到达位置的线程达到要求即可开启大门
            barrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " - " + System.currentTimeMillis() + " started...");
    }
}
