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
public class ArmedThreadPoolMain {

    public static void main(String[] args) {
        ArmedThreadPool executorService = (ArmedThreadPool) ArmedThreadPool.newFixedThreadPool(1);
        CommandExecutable<String> command1 = new ConcreteCommand("1");
        CommandExecutable<String> command2 = new ConcreteCommand("2");
        CommandExecutable<String> command3 = new ConcreteCommand("3");
        CommandExecutable<String> command4 = new ConcreteCommand("4");
        CommandExecutable<String> command5 = new ConcreteCommand("5");
        CommandExecutable<String> command6 = new ConcreteCommand("6");
        Callable<String> wrapper=new CallableCommandWrapper<String>(100500,command6);
        RunningFutureTask<String> task6 = new RunningFutureTask<String>(wrapper, 6,"123");

        executorService.submit(task6);
        executorService.submit(command1, 1, "1");
        executorService.submit(command3, 3, "3");
        executorService.submit(command2, 2, "2");
        executorService.submit(command5, 5, "5");
        executorService.submit(command4, 4, "4");
        
        task6.cancel(true);
        

        executorService.shutdown();
        try {
            executorService.awaitTermination(30, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
