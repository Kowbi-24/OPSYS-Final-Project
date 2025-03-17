// Kobe's Part

import java.util.*;

public class FCFS {
    List<Process> listProcess;
    List<Process> orderProcess = new ArrayList<Process>();
    List<Process> finalGanttChart;
    int cpuCycle = 0;
    
    FCFS(){

    }

    FCFS(List<Process> listProcesses, List<Process> finalGanttChart){
        this.listProcess = listProcesses;
        this.finalGanttChart = finalGanttChart;
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

    public void startOneCycle(int cpuCycle){
        // Sort the processes by arrival time
        listProcess.sort(Comparator.comparing(Process::getArrivalTime));

        // Get the process with the lowest arrival time
        Process currProcess = listProcess.get(0);
        System.out.printf("[SYSTEM | FCFS : Cycle %s] Starting Process[%s]\n", cpuCycle, currProcess.getProcessID());

        // Update the burst time
        currProcess.setBurstTime(currProcess.getBurstTime() - 1);

        // Increment CPU cycle by 1
        cpuCycle += 1;

        // Remove from list if finished
        if (currProcess.getBurstTime() <= 0){
            orderProcess.add(currProcess);
            finalGanttChart.add(currProcess);
            listProcess.remove(0);
            System.out.printf("[SYSTEM | FCFS : Cycle %s] Process[%s] has finished\n", cpuCycle, currProcess.getProcessID());
        }

    }

    public void printGanttChart(){
        System.out.print("[FCFS] GANTT CHART: |");

        for (Process currentProcess : orderProcess){
            System.out.printf("%s|", currentProcess.getProcessID());
        }
        System.out.println();
    }

}
