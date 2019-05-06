/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processplanner;
// <editor-fold defaultstate="collapsed" desc="Imports">
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
// </editor-fold>    

/**
 *
 * @author junior
 */
public class PlanificadorCPU {
// <editor-fold defaultstate="collapsed" desc="Variables">
    /**
     * Constantes que especifican cada tipo de algoritmo
     */
    public static final int FCFS = 1;
    public static final int SJF = 2;
    public static final int ROUNDROBIN = 3;

    /**
     * Tiempo transcurrido
     */
    private long currentTime = 0;

    /**
     * Tiempo de inactividad transcurrdio
     */
    private long inactivityTime = 0;

    /**
     * Tiempo transcurrido que el CPU estuvo ocupado
     */
    private long occupiedTime = 0;

    /**
     * Porcion de tiempo para Round Robin
     */
    private long quantum = 4;

    /**
     * Cuenta atr�s de cu�ndo interrumpir un proceso, porque su quantum termino
     */
    private long quantumCounter = quantum;
    
    boolean preemptive = true;

    /**
     * Solo Round Robin, esta variable mantiene un registro del n�mero de
     * quantum consecutivos que un proceso ha consumido
     */
    private long turnCounter = 0;

    /**
     * Cantidad de procesos preparados para ejecucion
     */
    private int processesIn = 0;

    /**
     * Cantidad de procesos que se han ejecutado hasta el final
     */
    private int processesOut = 0;

    /**
     * Algoritmo por defecto a utilizarpublic Boolean getPaused() {
        return paused;
    }


    public void setPaused(Boolean paused) {
        this.paused = paused;
    }
     */
    private int algorithm = FCFS;

    /**
     * FPS Velocidad
     */
    private int fps = 0;

    /**
     * Coleccion de todos los procesos que seran usados
     */
    private ArrayList<PCB> workQueue = new ArrayList<>();
    private ArrayList<PCB> allProcess;

    /**
     * Coleccion de todos los procesos que han llegado y requieren CPU
     */
    private ArrayList<PCB> readyQueue = new ArrayList<>();

    /**
     * Referencia al proceso activo. El cpu cambia esta refencia a diferentes
     * procesos en la cola de listos usando su respectivo algoritmo a traves de
     * un criterio.
     */
    private PCB activeProcess = null;

    /**
     * index del vector en colaListos
     */
    private int indexActiveProcess = 0;

    /**
     * Flag para verificar si fue pausada la ejecucion
     */
    private Boolean paused = false;
// </editor-fold>    

    public PlanificadorCPU(String path) {
        loadProcess(path);
        this.allProcess = new ArrayList<>(workQueue);
        
    }

    private void loadProcess(String path) {
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
    
    /**
     * Utilice el planificador apropiado para elegir el siguiente proceso. A
     * continuaci�n, enviaremos el proceso.
     */
    private void planner() {
        switch (this.algorithm) {
            case FCFS:
                RunFCFS(this.readyQueue);
                break;
            case SJF:
                RunSJF(this.readyQueue);
                break;
            case ROUNDROBIN:
                RunRoundRobin(this.readyQueue);
                break;
            default:
                System.out.println("Ningun algoritmo de planificacion valido");
                break;
        }
        dispatch();
    }

    private void dispatch() {
        PCB p = null;

        this.activeProcess.running(this.currentTime);

        for (int i = 0; i < this.readyQueue.size(); ++i) {
            p = (PCB) this.readyQueue.get(i);
            if (p.getPid() != this.activeProcess.getPid()) {
                p.waiting(this.currentTime);
            }
        }
    }

    private void RunFCFS(ArrayList readyQ) {
        try {
            if (this.occupiedTime == 0 || this.activeProcess.isFinished()) {
                this.activeProcess = nextProcessArriveFirst(readyQ);
                this.indexActiveProcess = readyQ.indexOf(this.activeProcess);
            }
        } catch (NullPointerException e) {
        }
    }

    private void RunSJF(ArrayList readyQ) {
        try {
            if (this.occupiedTime == 0 || this.activeProcess.isFinished() || this.preemptive == true) {
                this.activeProcess = nextProcessShortest(readyQ);
                this.indexActiveProcess = readyQ.indexOf(this.activeProcess);
            }
        } catch (NullPointerException e) {
        }
    }

    private void RunRoundRobin(ArrayList readyQ) {
        try {
            if(this.occupiedTime == 0){
                this.activeProcess = (PCB)readyQ.get(0);
            }
            if (this.activeProcess.isFinished() || quantumCounter == 0) {
                this.activeProcess = nextProcessRR(readyQ);
                this.indexActiveProcess = readyQ.indexOf(this.activeProcess);
                this.quantumCounter = quantum;
            }
            this.quantumCounter--;
        } catch (NullPointerException e) {
        }
    }

    private PCB nextProcessRR(ArrayList readyQ) {
        PCB nextP = null;
        int index = 0;

        if (this.indexActiveProcess >= (readyQ.size() - 1)) {
            index = 0;
        } else if (this.activeProcess != null && this.activeProcess.isFinished()) {
            index = this.indexActiveProcess;
        } else {
            index = (this.indexActiveProcess + 1);
        }

        nextP = (PCB) readyQ.get(index);

        return nextP;
    }

    private PCB nextProcessShortest(ArrayList readyQ) {
        PCB nextP = null, shortest = null;
        long time = 0, shortTime = 0;

        for (int i = 0; i < readyQ.size(); ++i) {
            nextP = (PCB) readyQ.get(i);
            time = nextP.getBurstTime();
            if ((time < shortTime) || (i == 0)) {
                shortTime = time;
                shortest = nextP;
            }
        }
        return shortest;
    }

    private PCB nextProcessArriveFirst(ArrayList readyQ) {
        PCB nextP = null, earliest = null;
        long time = 0, arriveTime = 0;

        for (int i = 0; i < readyQ.size(); ++i) {
            nextP = (PCB) readyQ.get(i);
            time = nextP.getArrivalTime();
            if ((time < arriveTime) || (i == 0)) {
                arriveTime = time;
                earliest = nextP;
            }
        }
        return earliest;
    }

    private void loadReadyQueue() {
        PCB p;
        for (int i = 0; i < this.workQueue.size(); i++) {
            p = (PCB) workQueue.get(i);
            if (p.getArrivalTime() == this.currentTime) {
                this.readyQueue.add(p);
                this.processesIn++;
                this.preemptive = true;
            }
        }

    }

    private void purgeReadyQueue() {
        PCB p;
        for (int i = 0; i < this.readyQueue.size(); i++) {
            p = (PCB) this.readyQueue.get(i);
            if (p.isFinished() == true) {
                this.readyQueue.remove(i);
                this.processesOut++;
            }
        }
    }

    private void purgeWorkQueue() {
        PCB p;
        for (int i = 0; i < this.workQueue.size(); i++) {
            p = (PCB) this.workQueue.get(i);
            if (p.isFinished() == true) {
                this.workQueue.remove(i);
            }
        }
    }

    public long getCurrentTime() {
        return currentTime;
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

    public void setQuantum(long quantum) {
        this.quantum = quantum;
    }

    public int getAlgorithm() {
        return this.algorithm;
    }

    public void setAlgorithm(int algorithm) {
        this.algorithm = algorithm;
    }

    public PCB getActiveProcess() {
        return activeProcess;
    }

    @SuppressWarnings("empty-statement")
    public void simulate() {
        while (nextCycle());
    }

    public boolean nextCycle() {
        boolean moreCycles = false;
        if (this.workQueue.isEmpty()) {
            moreCycles = false;
        } else {
            loadReadyQueue();
            moreCycles = true;
            if (this.readyQueue.isEmpty()) {
                this.inactivityTime++;
            } else {
                planner();
                this.occupiedTime++;
                cleanUp();
            }
            this.currentTime++;
        }
        //recoleccionEstadisticas();
        return moreCycles;
    }

    private void cleanUp() {
        purgeWorkQueue();
        purgeReadyQueue();
    }
    
    public int getFps() {
        return fps;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

    public Boolean isPaused() {
        return paused;
    }

    public void setPaused(Boolean paused) {
        this.paused = paused;
    }
    
    public void restart() {
        activeProcess = null;
        currentTime = 0;
        occupiedTime = 0;
        quantum = 4;
        quantumCounter = quantum;
        turnCounter = 0;
        processesIn = 0;
        processesOut = 0;
        workQueue.clear();
        readyQueue.clear();
        loadProcess("/home/daniel/Desktop/processes.txt");
    }
    
}
