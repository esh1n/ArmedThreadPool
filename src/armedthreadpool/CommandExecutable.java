/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package armedthreadpool;

/**
 *
 * @author sergey
 */

 public interface CommandExecutable<T> {

    public abstract T execute() throws Exception;
}
