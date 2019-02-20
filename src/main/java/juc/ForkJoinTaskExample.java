package juc;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTaskExample extends RecursiveTask<Integer> {
    private static final Integer RANGE = 2;
    private int begin;
    private int end;

    public ForkJoinTaskExample(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        boolean canCompulate = end - begin < RANGE;
        if (canCompulate) {
            return (end - begin + 1) * (end + begin) / 2;
        } else {
            int mid = (begin + end) / 2;
            ForkJoinTaskExample leftTask = new ForkJoinTaskExample(begin, mid);
            ForkJoinTaskExample rightTask = new ForkJoinTaskExample(mid + 1, end);
            leftTask.fork();
            rightTask.fork();
            return leftTask.join() + rightTask.join();
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinTaskExample task = new ForkJoinTaskExample(1, 100);
        ForkJoinPool pool = new ForkJoinPool();
        Future<Integer> result = pool.submit(task);
        System.out.println(result.get());
    }
}
