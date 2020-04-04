/*   
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package philosbar;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ubuntu
 */
public class Philosopher extends Thread{
    
    private final long id;
    private final ArrayList<Bottle> bottles;
    
    private double tranquil = 0;
    private double thirsty = 0;
    private double drinking = 0;
    
    private final int rounds = 6;
    private static int lastId = 0;
    private static final Random random = new Random(System.currentTimeMillis());

    public Philosopher() {
        this.bottles = new ArrayList<>();
        this.id = lastId++;
    }
    
    public static int randomInRange(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }
    
    /**
     *
     */
    @Override
    public void run() {
        
        double time;
        for (int i = 0; i < this.rounds; i++) {
            try {
                time = System.currentTimeMillis();
                
                Thread.sleep(random.nextInt(2000));
                this.tranquil += (System.currentTimeMillis() - time);
                
                time = System.currentTimeMillis();
                System.out.println(this.id + " is THIRSTY!");
                
                drink(randomInRange(2, this.bottles.size()));
                this.thirsty += (System.currentTimeMillis() - time);
                
            } catch (InterruptedException ex) {
                Logger.getLogger(Philosopher.class.getName()).log(Level.SEVERE, null, ex);
            }
                    
        }
    }
    
    /**
     *
     * @param bottle
     */
    public void take(Bottle bottle){
        this.bottles.add(bottle);
        bottle.setVolume(bottle.getVolume()+this.rounds);
    }
    
    public void myBottles(){
        System.out.println(this.bottles);
    }
    
    public void myTimes(){
        System.out.println("\n>> Philosopher "+this.id);
        myBottles();
        System.out.println("Tranquil : "+ this.tranquil/1000 +" s");
        System.out.println("Thirsty : "+this.thirsty/1000+" s");
        System.out.println("Average : "+(this.thirsty/this.rounds)/1000+" s");
        System.out.println("Drinking : "+this.drinking/1000+" s");
        double total = this.tranquil + this.thirsty + this.drinking;
        System.out.println("TOTAL : "+ total/1000 +" s");
    }

    private void drink(int n) throws InterruptedException{
        
        int numBottles = n;
        System.out.println(this.id+" requires "+numBottles+" Bottles");
        
        for (Bottle bottle : bottles) {
            bottle.await(this);
            numBottles--;
            if (numBottles == 0)
                break;
        }
        
        System.out.println(TextColor.green() + id + " is DRINKING " + bottles + TextColor.endColor() );
        
        double time = System.currentTimeMillis();
        numBottles = n;
        for (Bottle bottle : bottles) {
            bottle.glup();
            numBottles--;
            if (numBottles == 0)
                break;
        }
        Thread.sleep(1000);
        
        this.drinking += System.currentTimeMillis() - time;
        
        System.out.println(this.id + " is TRANQUIL!");
        dropBottles(n);
    }

    public ArrayList<Bottle> getBottles() {
        return bottles;
    }
    
    public void dropBottles(int numBottles){
        
        for (Bottle bottle : bottles) {
            bottle.signal();
            numBottles--;
            if (numBottles == 0)
                break;
        }
    }
    
    /**
     *
     * @return
     */
    @Override
    public long getId(){
        return this.id;
    }

    public double getTranquil() {
        return tranquil;
    }

    public void setTranquil(double tranquil) {
        this.tranquil = tranquil;
    }

    public double getThirsty() {
        return thirsty;
    }

    public void setThirsty(double thirsty) {
        this.thirsty = thirsty;
    }

    public double getDrinking() {
        return drinking;
    }

    public void setDrinking(double drinking) {
        this.drinking = drinking;
    }

    public static int getLastId() {
        return lastId;
    }

    public static void setLastId(int lastId) {
        Philosopher.lastId = lastId;
    }
    
    public double getAverage(){
        return (this.thirsty/this.rounds);
    }
    
    
}
