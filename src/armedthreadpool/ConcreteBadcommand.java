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

public class ConcreteBadcommand implements CommandExecutable<String>{
    private  String id;

    public ConcreteBadcommand(String id){
        this.id=id;
    }
    @Override
    public String execute() throws Exception {
        Thread.sleep(1000);
        System.out.println("Callable with id + " + id + " worked");
        throw new NullPointerException("Pizdec");
    }
    
}
