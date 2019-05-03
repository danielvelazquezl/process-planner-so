package processplanner;

import java.util.ArrayList;
import java.util.Collections;

public class SJF {
    
    /**
     * Plan the processes according to the burst time
     * @param readyQueue 
     */
    public void plan(ArrayList readyQueue){
        Collections.sort(readyQueue, (PCB p1, PCB p2) -> 
            new Integer(p1.getBurstTime()).compareTo(new Integer(p2.getBurstTime())));
    }
    
    /**
     * Method to find the waiting time for all processes
     * @param proc processes list
     * @param n list length
     * @param wt waiting time array
     */
    public void findWaitingTime(ArrayList<PCB> proc, int n, int wt[]) {
        int rt[] = new int[n];

        // Copy the burst time into rt[] 
        for (int i = 0; i < n; i++) rt[i] = proc.get(i).getBurstTime(); 

        int complete = 0, t = 0, minm = Integer.MAX_VALUE;
        int shortest = 0, finish_time;
        boolean check = false;

        // Process until all processes gets 
        // completed 
        while (complete != n) {

            // Find process with minimum 
            // remaining time among the 
            // processes that arrives till the 
            // current time` 
            for (int j = 0; j < n; j++) {
                if ((proc.get(j).getArrivalTime() <= t)
                        && (rt[j] < minm) && rt[j] > 0) {
                    minm = rt[j];
                    shortest = j;
                    check = true;
                }
            }

            if (check == false) {
                t++;
                continue;
            }

            // Reduce remaining time by one 
            rt[shortest]--;

            // Update minimum 
            minm = rt[shortest];
            if (minm == 0) {
                minm = Integer.MAX_VALUE;
            }

            // If a process gets completely 
            // executed 
            if (rt[shortest] == 0) {

                // Increment complete 
                complete++;
                check = false;

                // Find finish time of current 
                // process 
                finish_time = t + 1;

                // Calculate waiting time 
                wt[shortest] = finish_time
                        - proc.get(shortest).getArrivalTime()
                        - proc.get(shortest).getArrivalTime();

                if (wt[shortest] < 0) {
                    wt[shortest] = 0;
                }
            }
            // Increment time 
            t++;
        }
    }

    /**
     * Method to calculate average time
     * @param proc processes list
     * @param n list length
     */
    public void findavgTime(ArrayList<PCB> proc, int n) {
        int wt[] = new int[n], total_wt = 0;

        // Function to find waiting time of all 
        // processes 
        findWaitingTime(proc, n, wt);

        // Calculate total waiting time and 
        for (int i = 0; i < n; i++) total_wt =+ wt[i];

        // Print the total average time
        System.out.println("Average waiting time = " + (float) total_wt / (float) n);
    }
}
