package processplanner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author junior
 */
public class FCFS {
    private ArrayList<PCB> readyQueue;
    public FCFS(ArrayList workQueue) {
        this.readyQueue = new ArrayList<>(workQueue);
    }

    public ArrayList getReadyQueue() {
        return this.readyQueue;
    }
    
    public void plan(){
        Collections.sort(readyQueue, (PCB p1, PCB p2) -> 
            new Integer(p1.getArrivalTime()).compareTo(new Integer(p2.getArrivalTime())));
    }
    
    public void imprimir(){
        readyQueue.forEach((aux) -> {
            System.out.println(aux.getpName());
        });
    }
    
}
