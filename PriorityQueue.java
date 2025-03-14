// Kim Charles Priority Queue

import java.util.*;

public class PriorityQueue 
{
    static Scanner scanner = new Scanner(System.in);

    List<Process> listProcesses;
    int completedCount, cpuTime = 0;
    double totalTurnAroundTime, totalWaitingTime = 0.0;
    
    PriorityQueue() {

    }

    PriorityQueue(List<Process> listProcesses){
        this.listProcesses = listProcesses;
    } 

    public void initializePriority() {
        /*
        System.out.println("\nThe Priority Simulation starts now:");
        System.out.println("Press ENTER to continue...");
        scanner.nextLine();

        boolean createNewProcess = true;
        System.out.println("\n INPUT DATA FOR THE PROCESSES");

        int currentProcessID = 0;
        while (createNewProcess){
            Process newProcess = new Process(currentProcessID);
            newProcess.inputData(true);

            listProcesses.add(newProcess);
            currentProcessID++;

            System.out.print("Create another process? [Y/N]: ");
            createNewProcess = (scanner.nextLine().toUpperCase().equalsIgnoreCase("Y"));
        }
        */

        List<Process> debugListProcesses = new ArrayList<>();
        debugListProcesses.add(new Process(0, 2, 1, 5));
        debugListProcesses.add(new Process(1, 3, 2, 3));
        debugListProcesses.add(new Process(2, 4, 3, 2));
        debugListProcesses.add(new Process(3, 10, 4, 1));

        PriorityQueue pq = new PriorityQueue(debugListProcesses);
        pq.start();
    }

    // Comparator to sort by arrival time, if equal, sort by priority level.
    class SortByArrivalTime implements Comparator<Process> {
        public int compare(Process firstProcess, Process secProcess) {
            if (firstProcess.getArrivalTime() == secProcess.getArrivalTime()) {
                return firstProcess.getPriorityLevel() - secProcess.getPriorityLevel();  // Higher priority = lower level value
            } else {
                return firstProcess.getArrivalTime() - secProcess.getArrivalTime();
            }
        }
    }

    public void sortProcessesByArrivalAndPriority(Process[] processes) {
        Arrays.sort(processes, new SortByArrivalTime());
    }

    public void displayPriorityTable(Process[] listProcesses){
        System.out.println("\n|Process\t|Arrival\t|Burst\t\t|Priority\t\t|Completion\t\t|Turnaround\t\t|Waiting");
        for (Process p : listProcesses) {
            System.out.println("|" + p.getProcessID() + "\t\t|" + p.getArrivalTime() + "\t\t|" + p.getBurstTime() + "\t\t|" + p.getPriorityLevel() + "\t\t\t|"
                    + p.getCompetionTime() + "\t\t\t|" + p.getTurnAroundTime() + "\t\t\t|" + p.getWaitingTime());
        }
    }

    public void displayGanttChart(List<Process> processes) {
        // Sort process by completion time
        processes.sort(Comparator.comparing(Process::getCompetionTime));

        System.out.println("\nGantt Chart:");
        for(Process p : processes){
            System.out.print("|  P" + p.getProcessID() + "  ");
        }
        System.out.println(" |");

        // Display 
        System.out.print(processes.get(0).getArrivalTime());
        for (Process p : processes) {
            System.out.print("      " + p.getCompetionTime());
        }
        System.out.println();
    }

    public void start(){
        Process[] sortedProcess = new Process[listProcesses.size()];
        int completionTime, turnAroundTime, waitingTime;

        for (int i = 0; i < listProcesses.size(); i++) {
            sortedProcess[i] = listProcesses.get(i);
        }

        // Sort list by arrival time 
        sortProcessesByArrivalAndPriority(sortedProcess);

        // Check if all process list have completed getting completion, turnaround, and wating time.
        boolean[] completedRun = new boolean[listProcesses.size()];

        // Loop the process list
        while (completedCount < listProcesses.size()) {
            int indexProcess = -1;
            int minPriority = Integer.MAX_VALUE;

            // Find the highest priority from the sorted process (some process have same arrival time)
            for (int i = 0; i < listProcesses.size(); i++) {
                if (!completedRun[i] && sortedProcess[i].getArrivalTime() <= cpuTime) {
                    if (sortedProcess[i].getPriorityLevel() < minPriority) {
                        minPriority = sortedProcess[i].getPriorityLevel();
                        indexProcess = i; // Process with highest priority is found
                    }
                }
            }

            // If process with highest priority is found, calculate completion time, turnaround time, and waiting time process. 
            // Else, increment cpu cycle until process is found.
            if (indexProcess == -1) {
                cpuTime++;
            } else {

                // TANONG MO KAY BEKO ABOUT DITO ------------------------------------------------------------------

                Process proc = sortedProcess[indexProcess];
                completionTime = getCompletionTimeProcess(cpuTime, proc.getBurstTime());
                proc.setCompletionTime(completionTime);

                turnAroundTime = getTurnAroundTimeProcess(completionTime, proc.getArrivalTime());
                totalTurnAroundTime+=turnAroundTime;
                proc.setTurnAroundTime(turnAroundTime);

                waitingTime = getWaitingTimeProcess(proc.getTurnAroundTime(), proc.getBurstTime());
                totalWaitingTime+=waitingTime;
                proc.setWaitingTime(waitingTime);

                // -------------------------------------------------------------------------------------------------
            
                cpuTime = proc.getCompetionTime();
                completedRun[indexProcess] = true;
                completedCount++; // Increment count until all process are done
            }
        }
        
        // Display priority table
        displayPriorityTable(sortedProcess);

        // Get average turn around and waiting time
        System.out.println("\nAverage Turnaround Time: " + getAverageTurnAroundTime(totalTurnAroundTime, listProcesses.size()));
        System.out.println("Average Turnaround Time: " + getAverageWaitingTime(totalWaitingTime, listProcesses.size()));

        // Display gantt chart
        displayGanttChart(Arrays.asList(sortedProcess));
    }

    public double getAverageTurnAroundTime(double totalTurnAroundTime, int numProcess) {
        return totalTurnAroundTime/numProcess;
    }
    public double getAverageWaitingTime(double totalWaitingTime, int numProcess) {
        return totalWaitingTime/numProcess;
    }

    public int getCompletionTimeProcess(int cpuTime, int burstTime){
        return cpuTime + burstTime;
    }
    public int getTurnAroundTimeProcess(int completionTime, int arrivalTime){
        return completionTime - arrivalTime;
    }
    public int getWaitingTimeProcess(int turnAroundTime, int burstTime){
        return turnAroundTime - burstTime;
    }
}
