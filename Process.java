// DO NOT EDIT!!!

import java.util.*;

public class Process{
    
    Scanner scanner = new Scanner(System.in);
    private int processID, burstTime, arrivalTime, priorityLevel, waitingTime, turnAroundTime, responseTime, completionTime;

    Process (int processID){
        this.processID = processID;
    }

    Process(int processID, int burstTime, int arrivalTime, int priorityLevel){
        this.processID = processID;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.priorityLevel = priorityLevel;
    }

    public void inputData(boolean isMLQ){
        System.out.printf("\nINPUT DATA FOR PROCESS # %s\n", processID);

        System.out.print("\t- Burst Time: ");
        this.burstTime = scanner.nextInt(); scanner.nextLine();

        System.out.print("\t- Arrival Time: ");
        this.arrivalTime = scanner.nextInt(); scanner.nextLine();

        if (isMLQ){
            System.out.print("\t- Queue Number [1-3]: ");
            this.priorityLevel = scanner.nextInt(); scanner.nextLine();
        } else{
            System.out.print("\t- Priority Level: ");
            this.priorityLevel = scanner.nextInt(); scanner.nextLine();
        }
    }

    // Getter Methods
    public int getProcessID(){
        return processID;
    }

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
        return responseTime;
    }
    
    public int getCompetionTime(){
        return completionTime;
    }

    // Setter Methods
    public void setProcessID(int processID) {
        this.processID = processID;
    }

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

    public void setCompletionTime(int completionTime){
        this.completionTime = completionTime;
    }
}