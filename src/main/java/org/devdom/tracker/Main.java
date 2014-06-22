package org.devdom.tracker;

/**
 *
 * @author Carlos Vásquez Polanco
 */
public class Main {

    public static void main(String[] args) throws Exception{
        
        Runnable worker = new Worker();
        Thread thread = new Thread(worker);
        thread.setName("w");
        thread.start();

    }
}
