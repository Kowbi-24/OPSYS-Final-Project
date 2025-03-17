// Kobe's Part

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SRT {
    List<Process> listProcess;
    List<Process> orderProcess = new ArrayList<>();
    List<Process> finalGanttChart;
    int cpuCycle = 0;

    SRT(List<Process> listProcesses, List<Process> finalGanttChart){
        this.listProcess = listProcesses;
        this.finalGanttChart = finalGanttChart;
    }

    public void start(){
        while (!checkIfAllProcessesDone()){

            sortListByRemainingTime(cpuCycle);

            // Find process that has arrived 
            Process currProcess = new Process(Integer.MAX_VALUE);
            boolean foundProcess = false;
            for (Process process : listProcess){
                if (process.getArrivalTime() <= cpuCycle){
                    currProcess = process;
                    foundProcess = true;
                    if(currProcess.getBurstTime() > 0){
                        System.out.printf("[SYSTEM | SRT : Cycle %s] Running Process[%s] | Remaining Burst Time: %s\n", cpuCycle, currProcess.getProcessID(), currProcess.getBurstTime());
                    }
                    break;
                }
            }
            if (!foundProcess){
                cpuCycle+=1;
                continue;
            }

            // Check if current process has finished and remove it from the list
            if (currProcess.getBurstTime() <= 0){
                listProcess.remove(currProcess);
                System.out.printf("[SYSTEM | SRT : Cycle %s] Finished Process[%s]\n", cpuCycle, currProcess.getProcessID());
                continue;
            }

            
            cpuCycle += 1; // Increment CPU Cycle
            currProcess.setBurstTime(currProcess.getBurstTime()-1);
            orderProcess.add(currProcess); // Add current process to gantt chart
        }

        printGanttChart();
    }

    public void sortListByRemainingTime(int cpuCycle){
        listProcess.sort(Comparator.comparing(Process::getBurstTime));
        System.out.printf("[SYSTEM | SRT : Cycle %s] Sorted process list | Result: |", cpuCycle);

        for (Process process: listProcess){
            System.out.printf("%s|", process.getProcessID());
        }
        System.out.println();
    }


    public boolean checkIfAllProcessesDone(){
        boolean done = true;
        for (Process currProcess : listProcess){
            if (currProcess.getBurstTime() > 0){
                done = false;
                break;
            }
        }
        return done;
    }

    public void printGanttChart(){
        System.out.print("[SRT] GANTT CHART: |");

        for (Process currentProcess : orderProcess){
            System.out.printf("%s|", currentProcess.getProcessID());
        }

        System.out.println();
    }


    public void startOneCycle(int cpuCycle){

        // Sort the list by remaining time
        sortListByRemainingTime(cpuCycle);

        // Get process with the shortest remaining time
        Process currProcess = listProcess.get(0);
        System.out.printf("[SYSTEM | SRT : Cycle %s] Starting Process[%s] | Remaining Burst Time: %s\n", cpuCycle, currProcess.getProcessID(), currProcess.getBurstTime());

        // Update gantt chart
        orderProcess.add(currProcess);
        finalGanttChart.add(currProcess);
        

        // Update current process' burst time
        currProcess.setBurstTime(currProcess.getBurstTime() - 1);

        // Increment cpu cycle
        cpuCycle += 1;

        // Remove current process from list if finished
        if (currProcess.getBurstTime() <= 0){
            listProcess.remove(0);
            System.out.printf("[SYSTEM | SRT : Cycle %s] Finished Process[%s]\n", cpuCycle, currProcess.getProcessID());
        }



    }
}
