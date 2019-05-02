package planificadorprocesos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PlanificadorProcesos {

    public static void main(String[] args) {
        loadProcess("/home/daniel/Desktop/process.txt");
        
    }

    private static void loadProcess(String path) {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        
        ArrayList processes = new ArrayList();
       
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
