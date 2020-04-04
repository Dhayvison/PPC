/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package philosbar;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ubuntu
 */
public class TimeOut implements Runnable{
        
        private Philosopher philosopher;

        public TimeOut(Philosopher philosopher) {
            this.philosopher = philosopher;
        }
        
        @Override
        public void run() {
            try {
                Thread.sleep((long) (3000 * (1/this.philosopher.getAverage())));
                this.philosopher.setPriority(Thread.MAX_PRIORITY);
                System.out.println("Philosopher "+ philosopher.getId() +" receives MAX PRIORITY");
            } catch (InterruptedException ex) {
                Logger.getLogger(Bottle.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    
    }