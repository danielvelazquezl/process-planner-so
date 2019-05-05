/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processplanner;

import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author junior
 */
public class PlanificadorCPU {

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
     * Algoritmo por defecto a utilizar
     */
    private int algorithm = FCFS;

    /**
     * FPS Velocidad
     */
    private int fps = 0;

    /**
     * Coleccion de todos los procesos que seran usados
     */
    private ArrayList<PCB> workQueue;
    private ArrayList<PCB> allProcess;

    /**
     * Coleccion de todos los procesos que han llegado y requieren CPU
     */
    private ArrayList<PCB> readyQueue = new ArrayList<PCB>();

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

    public PlanificadorCPU(ArrayList workQueue) {
        this.workQueue = new ArrayList<>(workQueue);
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
            if (this.occupiedTime == 0 || this.activeProcess.isFinished()) {
                this.activeProcess = nextProcessShortest(readyQ);
                this.indexActiveProcess = readyQ.indexOf(this.activeProcess);
            }
        } catch (NullPointerException e) {
        }
    }

    private void RunRoundRobin(ArrayList readyQ) {
        try {
            if (this.occupiedTime == 0 || this.activeProcess.isFinished() || quantumCounter == 0) {
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
            }
        }

    }

    void purgeReadyQueue() {
        PCB p;
        for (int i = 0; i < this.readyQueue.size(); i++) {
            p = (PCB) this.readyQueue.get(i);
            if (p.isFinished() == true) {
                this.readyQueue.remove(i);
                this.processesOut++;
            }
        }
    }
}
