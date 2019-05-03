package processplanner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PlanificadorProcesos {

    public static void main(String[] args) {
        ArrayList workQueue = new ArrayList();
        loadProcess("/home/junior/Documentos/process.txt", workQueue);
	FCFS fcfs = new FCFS(workQueue);
        fcfs.plan();
        fcfs.imprimir();
        
    }

    private static void loadProcess(String path, ArrayList processes) {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        
        try {
            br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                String[] process = line.split(cvsSplitBy);
                processes.add(new PCB(Integer.parseInt(process[0]), process[1], Integer.parseInt(process[2]), Integer.parseInt(process[3])));
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
}
