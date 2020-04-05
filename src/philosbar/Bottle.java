/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package philosbar;

/**
 *
 * @author ubuntu 
 */
class Bottle {
    
    private final int id;
    private boolean occupied = false;
    private String color;
    
    private static int lastId = 0;
 
    public Bottle() {
        this.id = lastId++;
        this.color = "\u001b[38;5;"+this.id+"m";  // aceita até 256 cores
    }
    
    public synchronized void await(Philosopher p) throws InterruptedException{
        while(occupied){
            System.out.println(TextColor.red() + p.getId() + " WAIT >>> "+TextColor.endColor()+this+"\t"+ p.getBottles());
            wait();
        }
        occupied = true;
    }
    
    public synchronized void signal(){
        occupied = false;
        notifyAll();
    }

    @Override
    public String toString() {
        return this.color+this.id+"\u001b[0m";    
    }
    
    
    
}
