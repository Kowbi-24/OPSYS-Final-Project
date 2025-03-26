// Kobe's Part

import java.util.*;

public class RoundRobin{
    final List<Process> listProcess;
    List<Process> listProcessCopy = new ArrayList<>();
    List<Process> processesInCPU = new ArrayList<>();
    List<Process> finalGanttChart;
    Process currProcess;
    private int timeQuantum;
    private int localCycle = 0;

    RoundRobin(List<Process> listProcesses, int timeQuantum, List<Process> finalGanttChart){
        this.listProcess = listProcesses;
        this.timeQuantum = timeQuantum;
        this.finalGanttChart = finalGanttChart;
        this.listProcessCopy.addAll(listProcesses);
        this.processesInCPU = this.checkForNewProceses(0, processesInCPU);
    }


    public void startOneCycle(int cpuCycle){

        if (processesInCPU.isEmpty()){
            processesInCPU = checkForNewProceses(cpuCycle, processesInCPU);
        }

        // To determine if the current time quantum cycle has finished
        if (localCycle >= timeQuantum){
            // Reset the local cycle
            localCycle = 0;

            System.out.printf("[SYSTEM | ROUND ROBIN : Cycle %s] Moving Process[%s] to end of queue\n",cpuCycle ,processesInCPU.get(0).getProcessID());

            // Move the first process to the end as the time quantum cycle has finished
            listProcessCopy.remove(processesInCPU.get(0));
            Process temp = processesInCPU.get(0);
            processesInCPU.remove(0);
            processesInCPU.add(temp);
            listProcessCopy.add(temp);

        }

        // Get the current process
        Process currProcess = processesInCPU.get(0);
        this.currProcess = currProcess;
        finalGanttChart.add(currProcess);
        System.out.printf("[SYSTEM | ROUND ROBIN : Cycle %s] Starting Process[%s] | Remaining Time: %s | List Order: |",cpuCycle ,currProcess.getProcessID(), currProcess.getRemainingBurstTime());
        printListOrder();

        // Update response time
        if (currProcess.getResponseTime() < 0){
            currProcess.setResponseTime(cpuCycle);
        }

        // Update the burst time
        currProcess.setRemainingBurstTime(currProcess.getRemainingBurstTime() - 1);

        // Check if process is done
        if (currProcess.getRemainingBurstTime() <= 0){
            System.out.printf("[SYSTEM | ROUND ROBIN : Cycle %s] Finished Process[%s]\n",cpuCycle+1 ,currProcess.getProcessID());
            currProcess.setFinished(true);
            currProcess.setCompletionTime(cpuCycle+1);
            currProcess.computeTurnAroundTime();
            listProcessCopy.remove(processesInCPU.get(0));
            processesInCPU.remove(0);
        }

        processesInCPU = checkForNewProceses(cpuCycle, processesInCPU);
        localCycle += 1;
    }
    

    // To be used for system messages
    public void printListOrder(){
        for (Process process : processesInCPU){
            System.out.printf("%s|", process.getProcessID());
        }
        System.out.println();
    }


    // Function to update the "processesInCPU" with arrived processes
    public List<Process> checkForNewProceses(int cpuCycle, List<Process> processesInCPU){
        
        // Iterate listProcess
        for (int i=0; i<listProcessCopy.size(); i++){
            Process currentProcess = listProcessCopy.get(i);

            // Check if process has arrived
            if (currentProcess.getArrivalTime() <= cpuCycle){
  
                if (!processesInCPU.contains(currentProcess)){
                    System.out.printf("[SYSTEM | ROUND ROBIN : Cycle %s] Added Process[%s] to CPU | ",cpuCycle ,currentProcess.getProcessID());
                    processesInCPU.add(currentProcess);

                    // Print new process list
                    System.out.printf("Proccesses in CPU: |", cpuCycle);
                    printListOrder();

                }
            }
        }


        return processesInCPU;
    }


    // Getter Methods
    public int getTimeQuantum() {
        return timeQuantum;
    }

    public Process getCurrProcess(){
        return this.currProcess;
    }

    // Setter Methods
    public void setTimeQuantum(int timeQuantum) {
        this.timeQuantum = timeQuantum;
    }
}
