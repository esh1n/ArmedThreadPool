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
public class ArmedThreadPool {

    public static void main(String[] args) {
        PriorityExecutor executorService = (PriorityExecutor) PriorityExecutor.newFixedThreadPool(1);
        Callable<String> command1 = new ConcreteCallableCommand("1");
        Callable<String> command2 = new ConcreteCallableCommand("2");
        Callable<String> command3 = new ConcreteCallableCommand("3");
        Callable<String> command4 = new ConcreteCallableCommand("4");
        Callable<String> command5 = new ConcreteCallableCommand("5");

        executorService.submit(command1, 1, "1");
        executorService.submit(command2, 2, "2");
        executorService.submit(command3, 3, "3");
        executorService.submit(command4, 4, "4");
        executorService.submit(command5, 5, "5");

        executorService.shutdown();
        try {
            executorService.awaitTermination(30, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
