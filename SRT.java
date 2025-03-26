// Kobe's Part

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SRT {
    List<Process> listProcess;
    List<Process> finalGanttChart;
    Process currProcess;
    int cpuCycle = 0;

    SRT(List<Process> listProcesses, List<Process> finalGanttChart){
        this.listProcess = listProcesses;
        this.finalGanttChart = finalGanttChart;
    }


    public void sortListByRemainingTime(int cpuCycle){
        listProcess.sort(Comparator.comparing(Process::getRemainingBurstTime));
        System.out.printf("[SYSTEM | SRT : Cycle %s] Sorted process list | Result: |", cpuCycle);

        for (Process process: listProcess){
            System.out.printf("%s|", process.getProcessID());
        }
        System.out.println();
    }



    public void startOneCycle(int cpuCycle){

        this.cpuCycle = cpuCycle;

        // Get process with the shortest remaining time
        Process currProcess = findCurrProcess();
        this.currProcess = currProcess;
        System.out.printf("[SYSTEM | SRT : Cycle %s] Starting Process[%s] | Remaining Burst Time: %s\n", cpuCycle, currProcess.getProcessID(), currProcess.getRemainingBurstTime());

        // Update gantt chart
        finalGanttChart.add(currProcess);
        

        // Update current process' burst time
        currProcess.setRemainingBurstTime(currProcess.getRemainingBurstTime() - 1);

        // Increment cpu cycle
        cpuCycle += 1;

        // Remove current process from list if finished
        if (currProcess.getRemainingBurstTime() <= 0){
            listProcess.get(0).setFinished(true);
            listProcess.get(0).setCompletionTime(cpuCycle);
            listProcess.get(0).computeTurnAroundTime();
            System.out.printf("[SYSTEM | SRT : Cycle %s] Finished Process[%s]\n", cpuCycle, currProcess.getProcessID());
        }

    }


    public Process findCurrProcess(){

        Process currProcess = null;

        sortListByRemainingTime(cpuCycle);
        for (Process process : listProcess){
            if (!process.getFinished()){
                currProcess = process;
            }
        }

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
