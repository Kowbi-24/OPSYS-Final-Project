// Kobe's Part

import java.util.*;

public class FCFS {
    List<Process> listProcess;
    int cpuCycle = 0;
    

    FCFS(List<Process> listProcesses){
        this.listProcess = listProcesses;
    }

    public void start(){

        // Sort the processes by arrival time using bubble sort
        listProcess.sort(Comparator.comparing(Process::getArrivalTime));

        // Begin processing tasks
        for (Process currProcess : listProcess){
            System.out.printf("[SYSTEM | FCFS : Cycle %s] Starting Process[%s]\n", cpuCycle, currProcess.getProcessID());

            // Update CPU cycle based on process' burst time
            cpuCycle += currProcess.getBurstTime();

            System.out.printf("[SYSTEM | FCFS : Cycle %s] Process[%s] has finished\n", cpuCycle, currProcess.getProcessID());
        }

    }

}
