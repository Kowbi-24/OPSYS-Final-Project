// Kobe's Part

import java.util.*;

public class FCFS {
    List<Process> listProcess;
    List<Process> orderProcess = new ArrayList<Process>();
    List<Process> finalGanttChart;
    Process currProcess;
    int cpuCycle = 0;
    
    FCFS(){

    }

    FCFS(List<Process> listProcesses, List<Process> finalGanttChart){
        this.listProcess = listProcesses;
        this.finalGanttChart = finalGanttChart;
    }


    public void startOneCycle(int cpuCycle){

        this.cpuCycle = cpuCycle;

        // Get the process with the lowest arrival time
        Process currProcess = findCurrProcess();
        this.currProcess = currProcess;
        System.out.printf("[SYSTEM | FCFS : Cycle %s] Starting Process[%s]\n", cpuCycle, currProcess.getProcessID());

        // Update the burst time
        currProcess.setRemainingBurstTime(currProcess.getRemainingBurstTime() - 1);

        // Increment CPU cycle by 1
        cpuCycle += 1;

        // Set process as finished
        if (currProcess.getRemainingBurstTime() <= 0){
            orderProcess.add(currProcess);
            finalGanttChart.add(currProcess);
            currProcess.setFinished(true);
            currProcess.setCompletionTime(cpuCycle);
            currProcess.computeTurnAroundTime();
            System.out.printf("[SYSTEM | FCFS : Cycle %s] Process[%s] has finished\n", cpuCycle, currProcess.getProcessID());
        }

    }


    public Process findCurrProcess(){
        Process currProcess = null;
        listProcess.sort(Comparator.comparing(Process::getArrivalTime));
        
        for (Process process : listProcess){
            if (!process.getFinished()){
                currProcess = process;
            }
        }

        System.out.println(currProcess.getResponseTime());

        // Update response time
        if (currProcess.getResponseTime() < 0){
            currProcess.setResponseTime(cpuCycle);
        }

        return currProcess;
    }

    // Called in the MLQ.java file
    public Process getCurrProcess(){
        return this.currProcess;
    }

}
