public class Process{
    
    private int burstTime, arrivalTime, priorityLevel, waitingTime, turnAroundTime, responseTime;

    Process(int burstTime, int arrivalTime, int priorityLevel){
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.priorityLevel = priorityLevel;
    }

    // Getter Methods
    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public int getTurnAroundTime() {
        return turnAroundTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public int getResponseTime() {
        return responseTime
        ;
    }

    // Setter Methods
    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public void setTurnAroundTime(int turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public void setResponseTime(int responseTime) {
        this.responseTime = responseTime;
    }
}