package processplanner;

public class PCB {

    private int pid, tBurst, arrivalTime;
    private long tWatingTotal;
    private String pName;
    private boolean arrived = false;
    private boolean finished = false;
    private boolean active = false;
    public static int nextPID = 0;

    /**
     * Modelo de Bloque de control de procesos
     *
     * @param pName Nombre del proceso
     * @param burstTime Rafagas del proceso
     * @param arrivalTime Tiempo de llegada
     */
    public PCB(String pName, int burstTime, int arrivalTime) {
        this.pid = PCB.nextPID++;   //PID generado automaticamente
        this.pName = pName;
        this.tBurst = burstTime;
        this.arrivalTime = arrivalTime;
    }

    /**
     * Ejecuta una (1) rafaga del proceso
     *
     * @param currentTime Tiempo actual
     */
    public void running(long currentTime) {
        this.active = true;
        if (currentTime == this.arrivalTime) {
            this.arrived = true;
        }
        this.tBurst--;
        if (this.tBurst == 0) {
            this.finished = true;
        }
    }

    /**
     * Proceso en estado de espera
     *
     * @param currentTime Tiempo actual
     */
    public void waiting(long currentTime) {
        if (currentTime == this.arrivalTime) {
            this.arrived = true;
        }
        if (this.arrived == true) {
            this.tWatingTotal++;
        }
        this.active = false;

    }

    /**
     * @return Tiempo de espera total
     */
    public long gettWatingTotal() {
        return this.tWatingTotal;
    }

    /**
     * @return Tiempo de llegada
     */
    public int getArrivalTime() {
        return this.arrivalTime;
    }

    /**
     * @return Rafaga de ejecucion
     */
    public int getBurstTime() {
        return this.tBurst;
    }

    /**
     * @return Id del proceso
     */
    public int getPid() {
        return this.pid;
    }

    /**
     * @return Nombre del proceso
     */
    public String getpName() {
        return this.pName;
    }

    /**
     * @return 
     */
    public boolean isArrived() {
        return this.arrived;
    }

    /**
     * @return Si el proceso esta finalizado
     */
    public boolean isFinished() {
        return this.finished;
    }

    /**
     * @return Si el proceso esta activo
     */
    public boolean isActive() {
        return this.active;
    }
}
