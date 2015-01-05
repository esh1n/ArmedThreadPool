/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package armedthreadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author sergey
 */
public class PriorityQueue {

    public static void main(String[] args) {
        PriorityExecutor executorService = (PriorityExecutor) PriorityExecutor.newFixedThreadPool(1);
        executorService.submit(getCallable("1"), 1);
        executorService.submit(getBadCallable("3"), 3);
        executorService.submit(getCallable("2"), 2);
        executorService.submit(getBadCallable("5"), 5);
        executorService.submit(getCallable("4"), 4);

        executorService.shutdown();
        try {
            executorService.awaitTermination(30, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Runnable getRunnable(final String id) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println(id);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public static Callable<String> getCallable(final String id) {
        return new Callable<String>() {

            @Override
            public String call() throws Exception {
                Thread.sleep(1000);
                System.out.println(id);
                return id;
            }
        };
    }
    public static Callable<String> getBadCallable(final String id) {
        return new Callable<String>() {

            @Override
            public String call() throws Exception {
                Thread.sleep(1000);
                throw  new NullPointerException("Pizdec");
            }
        };
    }

    static class PriorityExecutor extends ThreadPoolExecutor {

        public PriorityExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        }
        //Utitlity method to create thread pool easily 

        public static ExecutorService newFixedThreadPool(int nThreads) {
            return new PriorityExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<>());
        }
        //Submit with New comparable task 

        public Future<?> submit(Runnable task, int priority) {
            return super.submit(new ComparableFutureTask(task, null, priority));
        }
        //execute with New comparable task 

        public void execute(Runnable command, int priority) {
            super.execute(new ComparableFutureTask(command, null, priority));
        }

        @Override
        protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
            return (RunnableFuture<T>) callable;
        }

        public <T> Future<?> submit(Callable<T> callable, int priority) {
            ComparableFutureTask<T> task = new ComparableFutureTask<T>(callable, priority);
            return super.submit(task);
        }

        @Override
        protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
            return (RunnableFuture<T>) runnable;
        }

        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            super.afterExecute(r, t);
            if (t == null && r instanceof Future<?>) {
                try {
                    Future<?> future = (Future<?>) r;
                    if (future.isDone()) {
                       String result= (String) future.get();
                       System.out.println("result "+result);
                    }
                } catch (CancellationException ce) {
                    t = ce;
                } catch (ExecutionException ee) {
                    t = ee.getCause();
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt(); // ignore/reset
                }
            }
            if (t != null) {
                System.out.println(t);
            }
        }

    }

    static class ComparableFutureTask<T> extends FutureTask<T> implements Comparable<ComparableFutureTask<T>> {

        volatile int priority = 0;

        public ComparableFutureTask(Runnable runnable, T result, int priority) {
            super(runnable, result);
            this.priority = priority;
        }

        public ComparableFutureTask(Callable<T> callable, int priority) {
            super(callable);
            this.priority = priority;
        }

        @Override
        public int compareTo(ComparableFutureTask<T> o) {
            return Integer.valueOf(priority).compareTo(o.priority);
        }
    }
}
