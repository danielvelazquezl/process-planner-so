package processplanner;

public class PCB {

    private int pid, tBurst, tInitBurst, arrivalTime;
    private long startTime, finishedTime, tWatingTotal;
    private String pName;
    private boolean arrived = false;
    private boolean started = false;
    private boolean finished = false;
    private boolean active = false;

    public PCB(int pid, String pName, int burstTime, int arrivalTime) {
        this.pid = pid;
        this.pName = pName;
        this.tBurst = burstTime;
        this.tInitBurst = burstTime;
        this.arrivalTime = arrivalTime;
    }

    public synchronized void running(long currentTime) {

        this.active = true;

        if (currentTime == this.arrivalTime) {
            this.arrived = true;
        }

        if (tBurst == tInitBurst) {
            this.started = true;
            this.startTime = currentTime;

        }
        this.tBurst--;
        if (this.tBurst == 0) {
            this.finished = true;
            this.finishedTime = currentTime;
        }
    }

    public synchronized void waiting(long currentTime) {
        this.active = false;
        this.tWatingTotal++;
        if (currentTime == this.arrivalTime) {
            this.arrived = true;
        }
    }

    public void restore() {
        this.tBurst = tInitBurst;
        this.startTime = 0;
        this.tWatingTotal = 0;
        this.finishedTime = 0;
        this.active = false;
        this.started = false;
        this.finished = false;
        this.arrived = false;
    }

    public long gettWatingTotal() {
        return this.tWatingTotal;
    }

    public long getFinishedTime() {
        return this.finishedTime;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public int getArrivalTime() {
        return this.arrivalTime;
    }

    public int getBurstTime() {
        return this.tBurst;
    }

    public int gettInitBurst() {
        return this.tInitBurst;
    }

    public int getPid() {
        return this.pid;
    }

    public boolean isArrived() {
        return this.arrived;
    }

    public boolean isStarted() {
        return this.started;
    }

    public boolean isFinished() {
        return this.finished;
    }

    public boolean isActive() {
        return this.active;
    }

    public void print() {
        System.out.println("PID: " + this.pid + "\nTiempo Burst: " + this.tBurst
                + "Tiempo inicial Burst  : " + this.tInitBurst + "\n"
                + "Tiempo LLegada : " + this.arrivalTime + "\n"
                + "Tiempo Inicio   : " + this.startTime + "\n"
                + "Tiempo Final  : " + this.finishedTime + "\n"
                + "Tiempo Espera    : " + this.tWatingTotal);
    }

    public void decreaseBurstTime() {
        this.tBurst--;
    }

    public String getpName() {
        return this.pName;
    }

}
