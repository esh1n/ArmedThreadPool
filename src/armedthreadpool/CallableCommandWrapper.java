/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package armedthreadpool;

import java.io.Serializable;
import java.util.concurrent.Callable;

/**
 *
 * @author sergey
 */
public class CallableCommandWrapper<T> implements Callable<T> {

    private Integer context;
    private CommandExecutable<T> executable;

    public CallableCommandWrapper(Integer context, CommandExecutable<T> executable) {
        this.context = context;
        this.executable = executable;
    }

    @Override
    public T call() throws Exception {
        Thread.sleep(1000);
        return executable.execute(context);
    }

}
