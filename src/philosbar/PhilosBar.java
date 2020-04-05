package philosbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

public class PhilosBar {
    
    final static JFileChooser fc = new JFileChooser(new File("").getAbsolutePath() + "/src/inputs/");
    public static final ArrayList<Philosopher> philos = new ArrayList<Philosopher>();
    
    public static File selectFile (){
        File file = null;
        int retr = fc.showOpenDialog(fc);
        
        if (retr == JFileChooser.APPROVE_OPTION) 
             file = fc.getSelectedFile();
        else 
            System.out.println("Erro: File not found");

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

    public static void main(String[] args) {
        
        double time = System.currentTimeMillis();
        
        makeGraph();
        
        ExecutorService exec = Executors.newCachedThreadPool();
        
        for (Philosopher p : PhilosBar.philos) {
            System.out.print(p.getId() +" :"); 
            p.myBottles(); 
        }
        
        newLine(2);

        sortPhilosophers();
        
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
                System.out.println("Time elapsed: "+ ((System.currentTimeMillis() - time)/1000)+" s");
            }else{
                newLine(2);
                System.out.println(TextColor.yellow() + "Time limit exceded!" + TextColor.endColor());
            }
        } catch (Exception e) {
        }
        
    }

    public static void sortPhilosophers(){
        Collections.sort(PhilosBar.philos, new Comparator<Philosopher>() {
            @Override
            public int compare(Philosopher arg0, Philosopher arg1) {
                return arg0.getBottles().size() - arg1.getBottles().size();
            }
        });
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
 