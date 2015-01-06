/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package armedthreadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 *
 * @author sergey
 */
  public class ComparableFutureTask<T> extends FutureTask<T> implements Comparable<ComparableFutureTask<T>> {

        volatile int priority = 0;
        private String intent;
        public ComparableFutureTask(Runnable runnable, T result, int priority) {
            super(runnable, result);
            this.priority = priority;
        }

        public ComparableFutureTask(Callable<T> callable, int priority,String intent) {
            super(callable);
            this.priority = priority;
            this.intent=intent;
        }
        public String getIntent(){
            return intent;
        }

        @Override
        public int compareTo(ComparableFutureTask<T> o) {
            return Integer.valueOf(priority).compareTo(o.priority);
        }
    }