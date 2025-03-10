// Kobe's Part

import java.util.*;

public class RoundRobin{
    final List<Process> listProcess;
    List<Process> orderProcess = new ArrayList<>();
    private int timeQuantum;

    RoundRobin(List<Process> listProcesses, int timeQuantum){
        this.listProcess = listProcesses;
        this.timeQuantum = timeQuantum;
    }


    public void start(){

        int cpuCycle = 0;
        List<Process> processesInCPU = new ArrayList<>();
        boolean firstIteration = true;

        while (!processesInCPU.isEmpty() || firstIteration){

            firstIteration = false;

            // Add all processes that has arrived
            processesInCPU = checkForNewProceses(cpuCycle, processesInCPU);
            printProcessesInCPU(processesInCPU, cpuCycle);

            for (int i=0; i<processesInCPU.size(); i++){
                i=0;
                Process currentProcess = processesInCPU.get(i);

                // Increment time quantum
                cpuCycle += timeQuantum;

                // Deduct the time quantum from the current process' burst time
                currentProcess.setBurstTime(currentProcess.getBurstTime() - timeQuantum);
                System.out.printf("[SYSTEM : Cycle %s] Processed Process[%s] | Remaining Burst Time: %s\n",cpuCycle ,currentProcess.getProcessID(), currentProcess.getBurstTime());

                // Add current process to order process
                orderProcess.add(currentProcess);

                // If current process' burst time <= 0, remove from list
                if (currentProcess.getBurstTime() <= 0){
                    processesInCPU.remove(i);
                    listProcess.remove(currentProcess);
                    System.out.printf("[SYSTEM : Cycle %s] Removed Process[%s] from CPU\n",cpuCycle ,currentProcess.getProcessID());
                }
                else{ // If process not removed, put it to the end of the processesInCPU
                    
                    processesInCPU = checkForNewProceses(cpuCycle, processesInCPU);

                    // Put the item in index 0 to the last index
                    Process firstProcess = processesInCPU.remove(0);
                    processesInCPU.add(firstProcess);
                    System.out.printf("[SYSTEM : Cycle %s] Moved Process[%s] to end of list | New order: [",cpuCycle ,currentProcess.getProcessID());

                    for (Process currrentProcess : processesInCPU){
                        System.out.print(currrentProcess.getProcessID());
                        System.out.print(", ");
                    }
            
                    System.out.print("]\n");
                    
                }
            }

        }

        printGanttChart(orderProcess);
    }


    public void printProcessesInCPU(List<Process> processesInCPU, int cpuCycle){
        System.out.printf("[SYSTEM : Cycle %s] Proccesses in CPU: [", cpuCycle);

        for (Process currrentProcess : processesInCPU){
            System.out.print(currrentProcess.getProcessID());
            System.out.print(", ");
        }

        System.out.print("]\n");
    }

    public void printGanttChart(List<Process> orderProcesses){
        System.out.print("GANTT CHART: ");

        for (Process currentProcess : orderProcesses){
            System.out.printf("%s , ", currentProcess.getProcessID());
        }
    }


    public List<Process> checkForNewProceses(int cpuCycle, List<Process> processesInCPU){
        
        // Iterate listProcess
        for (int i=0; i<listProcess.size(); i++){
            Process currentProcess = listProcess.get(i);

            // Check if process has arrived
            if (currentProcess.getArrivalTime() <= cpuCycle){
                // GOODLUCK KIM!!!
                if (!processesInCPU.contains(currentProcess)){
                    System.out.printf("[SYSTEM : Cycle %s] Added Process[%s] to CPU | ",cpuCycle ,currentProcess.getProcessID());
                    processesInCPU.add(currentProcess);

                    // Print new process list
                    System.out.printf("Proccesses in CPU: [", cpuCycle);

                    for (Process currrentProcess : processesInCPU){
                        System.out.print(currrentProcess.getProcessID());
                        System.out.print(", ");
                    }

                    System.out.print("]\n");
                }
            }
        }

        


        return processesInCPU;
    }


    // Getter Methods
    public int getTimeQuantum() {
        return timeQuantum;
    }

    // Setter Methods
    public void setTimeQuantum(int timeQuantum) {
        this.timeQuantum = timeQuantum;
    }
}
