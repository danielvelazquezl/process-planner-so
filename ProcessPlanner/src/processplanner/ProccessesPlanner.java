package processplanner;

// <editor-fold defaultstate="collapsed" desc="Imports">
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
// </editor-fold>    

public class ProccessesPlanner {
// <editor-fold defaultstate="collapsed" desc="Variables">
    /**
     * Constantes que especifican cada tipo de algoritmo
     */
    public static final int FCFS = 1;
    public static final int SJF = 2;
    public static final int MULTIQUEUE = 3;

    private long currentTime = 0;
    private long inactivityTime = 0;
    private long occupiedTime = 0;
    private int quantum = 2;

    private int quantumCounter = quantum;

    /**
     * Algoritmo por defecto a utilizar
     */
    private int algorithm = FCFS;

    /**
     * Todos los procesos
     */
    private ArrayList<PCB> workQueue = new ArrayList<>();

    /**
     * Cola de listos
     */
    private ArrayList<PCB> readyQueue = new ArrayList<>();
    
    /**
     * Cola de algoritmos con rafagas mayores o iguales a 10
     */
    private ArrayList<PCB> maxQueue = new ArrayList<>();
    
    /**
     * Cola de algoritmos con rafagas menores a 10 
     */
    private ArrayList<PCB> minQueue = new ArrayList<>();
    /**
     * Referencia al proceso activo. 
     */
    private PCB activeProcess = null;

    private double avgWait = 0;
    private int finishedCount = 0;
// </editor-fold>    

    /**
     * Constructor del planificador
     * 
     * @param path Direccion del archivo .txt de los procesos a ejecutar
     */
    public ProccessesPlanner(String path) {
        loadProcess(path);
    }

    /**
     * Carga los procesos desde el .txt al workQueue
     * 
     * @param path Direccion del archivo .txt a leer
     */
    private void loadProcess(String path) {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {
            br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                String[] process = line.split(cvsSplitBy);
                this.workQueue.add(new PCB(process[0], Integer.parseInt(process[1]), Integer.parseInt(process[2])));
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

    /**
     * Ejecuta el algoritmo seleccionado en la UI
     */
    private void planner() {
        switch (this.algorithm) {
            case FCFS:
                runFCFS(this.readyQueue);
                break;
            case SJF:
                runSJF(this.readyQueue);
                break;
            case MULTIQUEUE:
                runMultiQueue(this.readyQueue);
                break;
            default:
                System.out.println("Ningun algoritmo de planificacion valido");
                break;
        }
        dispatch();
    }

    /**
     * Ejecuta el proceso y pone a los demas en espera
     */
    private void dispatch() {
        PCB p = null;
        if (activeProcess != null) {
            this.activeProcess.running(this.currentTime);
            for (int i = 0; i < this.readyQueue.size(); ++i) {
                p = (PCB) this.readyQueue.get(i);
                if (p.getPid() != this.activeProcess.getPid()) {
                    p.waiting(this.currentTime);
                }
            }
        }
    }

    /**
     * Ejecutar el algoritmo FCFS
     * 
     * @param readyQ Cola de listos
     */
    public void runFCFS(ArrayList readyQ) {
        Collections.sort(readyQ, (PCB p1, PCB p2)
                -> new Integer(p1.getArrivalTime()).compareTo(new Integer(p2.getArrivalTime())));
        this.activeProcess = (PCB) readyQ.get(0);
    }

    /**
     * Ejecuta el algoritmo SJF
     *  
     * @param readyQ Cola de listos
     */
    public void runSJF(ArrayList readyQ) {
        Collections.sort(readyQ, (PCB p1, PCB p2)
                -> new Integer(p1.getBurstTime()).compareTo(new Integer(p2.getBurstTime())));
        this.activeProcess = (PCB) readyQ.get(0);
    }

    /**
     * Ejecuta el algoritmo Round Robin
     * 
     * @param readyQ Cola de listos
     */
    private void runRoundRobin(ArrayList readyQ) {

        if (this.occupiedTime == 0 || activeProcess == null) {
            this.activeProcess = (PCB) readyQ.get(0);
        } else if (this.activeProcess.isFinished() || quantumCounter == 0) {
            int pos = readyQ.indexOf(activeProcess);
            if(pos >= (readyQ.size() - 1)){
                this.activeProcess = (PCB) readyQ.get(0);
            }else{
                this.activeProcess = (PCB) readyQ.get(pos + 1);
            }
            
            this.quantumCounter = quantum;
        }
        this.quantumCounter--;
    }

    /**
     * Ejecuta la cola multinivel
     * 
     * @param readyQ Cola de listos
     */
    private void runMultiQueue(ArrayList<PCB> readyQ) {
        for (int i = 0; i < readyQ.size(); i++) {
            if (!maxQueue.contains(readyQ.get(i)) && !minQueue.contains(readyQ.get(i))) {
                if (readyQ.get(i).getBurstTime() < 5) {
                    minQueue.add(readyQ.get(i));
                } else {
                    maxQueue.add(readyQ.get(i));
                }
            }

        }

        if (!minQueue.isEmpty()) {
            System.out.println("RR" + this.currentTime);
            runRoundRobin(minQueue);
        } else if (minQueue.isEmpty() && !maxQueue.isEmpty()) {
            System.out.println("FIFO" + this.currentTime);
            runFCFS(maxQueue);
        }
    }

    /**
     * Carga la cola de listos
     */
    private void loadReadyQueue() {
        PCB p;
        for (int i = 0; i < this.workQueue.size(); i++) {
            p = (PCB) workQueue.get(i);
            if (p.getArrivalTime() == this.currentTime && !p.isFinished()) {
                this.readyQueue.add(p);
            }
        }

    }

    /**
     * Limpia la cola de listos
     */
    private void cleanReadyQueue() {
        PCB p;
        for (int i = 0; i < this.readyQueue.size(); i++) {
            p = (PCB) this.readyQueue.get(i);
            if (p.isFinished() == true) {
                this.readyQueue.remove(i);
                finishedCount++;
            }
        }
        if (this.algorithm == MULTIQUEUE && activeProcess != null) {
            if (activeProcess.isFinished()) {
                minQueue.remove(activeProcess);
                maxQueue.remove(activeProcess);
            }
        }
    }

    /**
     * @return Si es que aun hay ciclos por ejecutar
     */
    public boolean nextCycle() {
        boolean moreCycles = false;
        if (this.finishedCount == this.workQueue.size()) {
            moreCycles = false;
        } else {
            loadReadyQueue();
            moreCycles = true;
            if (this.readyQueue.isEmpty()) {
                this.inactivityTime++;
                activeProcess = null;
            } else {
                planner();
                this.occupiedTime++;
                cleanReadyQueue();
            }
            this.currentTime++;
        }
        calcAVGWait();
        return moreCycles;
    }

    /**
     * Reinicia la ejecucion del CPU
     */
    public void restart() {
        activeProcess = null;
        finishedCount = 0;
        currentTime = 0;
        occupiedTime = 0;
        quantum = 4;
        quantumCounter = quantum;
        avgWait = 0.0;
        workQueue.clear();
        readyQueue.clear();
        maxQueue.clear();
        minQueue.clear();
        loadProcess("processes.txt");
    }

    public void addProcess(PCB p) {
        this.workQueue.add(p);
    }

    /**
     * Calcula el tiempo de espera promedio 
     */
    private void calcAVGWait() {
        PCB p = null;
        int allWaited = 0;
        for (int i = 0; i < workQueue.size(); i++) {
            p = (PCB) workQueue.get(i);

            if (p.isFinished()) {
                int waited = (int) p.gettWatingTotal();
                allWaited += waited;
            }
        }
        if (finishedCount > 0) {
            this.avgWait = (double) allWaited / (double) finishedCount;
        }
    }
    
    public long getCurrentTime() {
        return currentTime;
    }

    public double getAvgWait() {
        return avgWait;
    }

    public long getInactivityTime() {
        return inactivityTime;
    }

    public long getOccupiedTime() {
        return occupiedTime;
    }

    public long getQuantum() {
        return quantum;
    }
    
    public PCB getActiveProcess() {
        return activeProcess;
    }
    
    public ArrayList<PCB> getWorkQueue() {
        return workQueue;
    }

    public ArrayList<PCB> getReadyQueue() {
        return readyQueue;
    }

    public int getAlgorithm() {
        return this.algorithm;
    }

    public void setAlgorithm(int algorithm) {
        this.algorithm = algorithm;
    }
    
    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }

}
