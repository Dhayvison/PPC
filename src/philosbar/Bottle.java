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
    private int volume = 0;
    private boolean occupied = false;
    
    private static int lastId = 0;
 
    public Bottle() {
        this.id = lastId++;
    }
    
    public synchronized void await(Philosopher p) throws InterruptedException{
        
        while(occupied){
            
            System.out.println(TextColor.red() + p.getId() + " WAIT >>> "+this+"\t"+ p.getBottles() + TextColor.endColor());
            
            //new Thread(new TimeOut(p)).start();
            wait();
        }
        occupied = true;
    }
    
    public synchronized void signal(){
        occupied = false;
        notifyAll();
    }
    
    public void glup(){
        this.volume--;
    }
    
    @Override
    public String toString() {
        return ""+this.id+" : "+this.occupied;
        //return ""+this.id+" : "+this.occupied +"\tvolume: "+this.volume;    
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
    
    
    
}
