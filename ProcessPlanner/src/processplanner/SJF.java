package processplanner;


public class SJF {   
	// Method to find the waiting time for all 
	// processes 
	static void findWaitingTime(PCB proc[], int n, int wt[]) 
	{ 
		int rt[] = new int[n]; 
	
		// Copy the burst time into rt[] 
		for (int i = 0; i < n; i++) 
			rt[i] = proc[i].getBurstTime(); 
	
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
			for (int j = 0; j < n; j++) 
			{ 
				if ((proc[j].getArrivalTime() <= t) && 
				(rt[j] < minm) && rt[j] > 0) { 
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
			if (minm == 0) 
				minm = Integer.MAX_VALUE; 
	
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
				wt[shortest] = finish_time - 
							proc[shortest].getArrivalTime() - 
							proc[shortest].getArrivalTime(); 
	
				if (wt[shortest] < 0) 
					wt[shortest] = 0; 
			} 
			// Increment time 
			t++; 
		} 
	} 
        
	// Method to calculate average time
        // proc: processes list, n: list length
	static void findavgTime(PCB proc[], int n) { 
		int wt[] = new int[n], total_wt = 0;
	
		// Function to find waiting time of all 
		// processes 
		findWaitingTime(proc, n, wt); 
	
		// Display processes along with all 
		// details 
		System.out.println("Processes " + " Burst time " + " Waiting time ");
	
		// Calculate total waiting time and 
		// total turnaround time 
		for (int i = 0; i < n; i++) { 
			total_wt = total_wt + wt[i]; 
			System.out.println(" " + proc[i].getPid() + "\t\t" + proc[i].getBurstTime() + "\t\t " + wt[i]);
		} 
		System.out.println("Average waiting time = " + (float)total_wt / (float)n); 
	} 	
} 

