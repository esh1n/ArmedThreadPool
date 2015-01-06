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
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author sergey
 */
public class PriorityExecutor extends ThreadPoolExecutor {

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

        public <T> Future<?> submit(Callable<T> command, int priority,String intent) {
            ComparableFutureTask<T> task = new ComparableFutureTask<T>(command, priority,intent);
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
                    ComparableFutureTask<?> futureTask = (ComparableFutureTask<?>) r;
                    if (futureTask.isDone()) {
                        String result = (String) futureTask.get();
                        System.out.println("result " + result);
                        String intent =futureTask.getIntent();
                        System.out.println("intent " + intent);
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