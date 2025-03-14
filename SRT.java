// Kobe's Part

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SRT {
    List<Process> listProcess;
    List<Process> processesInCPU = new ArrayList<>();
    List<Process> ganttChart = new ArrayList<>();
    int cpuCycle = 0;

    SRT(List<Process> listProcesses){
        this.listProcess = listProcesses;
    }

    public void start(){
        while (!checkIfAllProcessesDone()){

            sortListByRemainingTime();

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
            ganttChart.add(currProcess); // Add current process to gantt chart
        }

        displayGanttChart();
    }

    public void sortListByRemainingTime(){
        listProcess.sort(Comparator.comparing(Process::getBurstTime));
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

    public void displayGanttChart(){
        for (Process currProcess : ganttChart){
            //System.out.println(currProcess.getProcessID());
        }
    }
}
