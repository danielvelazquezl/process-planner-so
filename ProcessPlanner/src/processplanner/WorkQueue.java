/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processplanner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;

/**
 *
 * @author junior
 */
public class WorkQueue {

    private ArrayList<PCB> workQueue;

    public WorkQueue() {
        this.workQueue = new ArrayList<>();
    }

    public void loadProcess(String path) {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {
            br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                String[] process = line.split(cvsSplitBy);
                this.workQueue.add(new PCB(Integer.parseInt(process[0]), process[1], Integer.parseInt(process[2]), Integer.parseInt(process[3])));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public ArrayList<PCB> getProcesses() {
        return workQueue;
    }
    
}
