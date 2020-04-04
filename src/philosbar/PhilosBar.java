/*  
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package philosbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author ubuntu
 */
public class PhilosBar {
    
    final static JFileChooser fc = new JFileChooser(new File("").getAbsolutePath() + "/src/inputs/");
    public static final ArrayList<Philosopher> philos = new ArrayList<Philosopher>();
    
    public static File selectFile (){
        File file = null;
        int retr = fc.showOpenDialog(fc);
        
        if (retr == JFileChooser.APPROVE_OPTION) {
             file = fc.getSelectedFile();
        } else {
            System.out.println("Erro: File not found");
        }
        
        return file;
    }
    
    public static void makeGraph(){
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(PhilosBar.selectFile()));
            String st;
            Bottle b;
            
            int vert = 0;
            String[] line;
            while ((st = br.readLine()) != null) {
                philos.add(new Philosopher());
                line = st.split(", ");
                for(int i = 0; i < philos.size(); i++){
                    if(Integer.parseInt(line[i]) == 1){
                       b = new Bottle();
                       philos.get(i).take(b);
                       philos.get(vert).take(b);
                    }
                }
                vert++;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PhilosBar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PhilosBar.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        double time = System.currentTimeMillis();
        
        PhilosBar.makeGraph();
        
        ExecutorService exec = Executors.newCachedThreadPool();
        
        for (Philosopher p : PhilosBar.philos) {
            p.myBottles();
        }
        
        newLine(2);
        
        for (Philosopher p : PhilosBar.philos) {
            exec.execute(p);
        }
        
        exec.shutdown();
        
        try {
            if (exec.awaitTermination(2, TimeUnit.MINUTES)){
                for (Philosopher p : PhilosBar.philos) {
                    p.myTimes();
                }
                
                newLine(2);
                System.out.println("Global Average: "+average()/1000 +" s");
                System.out.println("Standart Deviation: "+stdDeviation()/1000 +" s");
                //System.out.println("Starvation: " + ((starvationCoeficiente() / average()))*100+"%");
                System.out.println("Time elapsed: "+ ((System.currentTimeMillis() - time)/1000)+" s");
            }else{
                newLine(2);
                System.out.println(TextColor.red() + "Time limit exceded!" + TextColor.endColor());
            }
        } catch (Exception e) {
        }
        
    }
    
    public static void newLine(int n){
        for (int i = 0; i < n; i++)
            System.out.println();
    }
    
    public static double average(){
        double avg = 0;
        for (Philosopher philo : philos) {
            avg += philo.getAverage();
        }
        return avg/philos.size();
    }
    
    public static double stdDeviation(){
        double stdDeviation = 0;
        double avg = average();
        for (Philosopher philo : philos) {
            stdDeviation += (philo.getAverage() - avg)*(philo.getAverage() - avg);
        }
        stdDeviation /= philos.size();
        return Math.sqrt(stdDeviation);
    }
   
    
}
 