/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package armedthreadpool;

import java.util.concurrent.Callable;

/**
 *
 * @author sergey
 */
public class RunningTask<T> implements Callable<T> {

    private CommandExecutable<T> commandExecutable;
    private String intent;

    public RunningTask(CommandExecutable<T> commandExecutable,String intent) {
        this.commandExecutable = commandExecutable;
        this.intent=intent;
    }

    @Override
    public T call() throws Exception {
        return commandExecutable.execute();
    }
    public String getIntent(){
        return intent;
    }

}
