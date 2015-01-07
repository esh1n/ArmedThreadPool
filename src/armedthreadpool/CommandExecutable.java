/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package armedthreadpool;

import java.io.Serializable;

/**
 *
 * @author sergey
 * @param <T>
 */
public interface CommandExecutable<T>{
    T execute(Integer context);
}
