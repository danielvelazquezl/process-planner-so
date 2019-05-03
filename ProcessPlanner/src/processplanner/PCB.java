package processplanner;

public class PCB {
    
    private int pid, burstTime, arrivalTime;
    private String pName;
    
    public PCB(int pid, String pName, int burstTime, int arrivalTime) {
        this.pid = pid;
        this.pName = pName;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
    }

    public int getPid() {
        return this.pid;
    }

    public int getBurstTime() {
        return this.burstTime;
    }

    public void decreaseBurstTime() {
        this.burstTime--;
    }

    public int getArrivalTime() {
        return this.arrivalTime;
    }

    public String getpName() {
        return this.pName;
    }
}
