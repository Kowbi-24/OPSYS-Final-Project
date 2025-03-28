// Kim Charles Priority Queue

import java.util.*;

public class PriorityQueue 
{
    static Scanner scanner = new Scanner(System.in);

    List<Process> listProcesses = new ArrayList<Process>();
    int completedCount, cpuTime = 0;
    double avgTurnAroundTime, avgWaitingTime = 0.0;
    
    PriorityQueue() {

    }

    PriorityQueue(List<Process> listProcesses){
        this.listProcesses = listProcesses;
    } 

    public void initializePriority() {
        
        System.out.println("\n==============================================");
        System.out.println(  "|        PRIORITY QUEUE SIMULATION           |");
        System.out.println(  "==============================================");
        System.out.println("Press ENTER to continue...");
        scanner.nextLine();

        boolean createNewProcess = true;

        int currentProcessID = 0;
        while (createNewProcess){
            Process newProcess = new Process(currentProcessID);
            // Input data for priority queue
            newProcess.inputData(false);

            listProcesses.add(newProcess);
            currentProcessID++;

            System.out.print(">> Create another process? [Y/N]: ");
            createNewProcess = (scanner.nextLine().toUpperCase().equalsIgnoreCase("Y"));
        }
        
        /*
        List<Process> debugListProcesses = new ArrayList<>();
        debugListProcesses.add(new Process(0, 2, 1, 5));
        debugListProcesses.add(new Process(1, 3, 2, 3));
        debugListProcesses.add(new Process(2, 4, 3, 2));
        debugListProcesses.add(new Process(3, 10, 4, 1));

        PriorityQueue pq = new PriorityQueue(debugListProcesses);
        */

        PriorityQueue pq = new PriorityQueue(listProcesses);
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
        System.out.println();
        System.out.println("=================================================================================================================");
        System.out.printf ("||                                                   TABLE                                                     ||\n");
        System.out.println("=================================================================================================================");
        System.out.println("||       ID     | Burst Time | Arrival Time | Waiting Time | Turnaround Time | Response Time | Completion Time ||");
        System.out.println("||-------------------------------------------------------------------------------------------------------------||");
        for (Process currProcess : listProcesses){
            System.out.printf("|| PROCESS # %2.2s |     %3.3s    |      %3.3s     |      %3.3s     |        %3.3s      |      %3.3s      |       %3.3s       ||\n", currProcess.getProcessID(), currProcess.getBurstTime(), currProcess.getArrivalTime(),currProcess.getWaitingTime(),currProcess.getTurnAroundTime(), currProcess.getResponseTime(), currProcess.getCompetionTime());
        }
        System.out.println("=================================================================================================================");
        System.out.println();


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

        // Check if all process list have completed.
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

            // If no process is arriving yet, increment cpu time until process is found.
            // Else, process with highest priority is found, calculate completion time, turnaround time, and waiting time process. 
            if (indexProcess == -1) {
                cpuTime++;
            } else {

                // Update response time
                if (listProcesses.get(indexProcess).getResponseTime() < 0){
                    listProcesses.get(indexProcess).setResponseTime(cpuTime);
                }
                
                // Calculate completion, turnaround, waiting time in the process index.
                completionTime = calculateCompletionTimeProcess(cpuTime, sortedProcess[indexProcess].getBurstTime());
                sortedProcess[indexProcess].setCompletionTime(completionTime);

                turnAroundTime = calculateTurnAroundTimeProcess(completionTime, sortedProcess[indexProcess].getArrivalTime());
                avgTurnAroundTime+=turnAroundTime;
                sortedProcess[indexProcess].setTurnAroundTime(turnAroundTime);

                waitingTime = calculateWaitingTimeProcess(sortedProcess[indexProcess].getTurnAroundTime(), sortedProcess[indexProcess].getBurstTime());
                avgWaitingTime+=waitingTime;
                sortedProcess[indexProcess].setWaitingTime(waitingTime);
            
                cpuTime = sortedProcess[indexProcess].getCompetionTime();
                completedRun[indexProcess] = true;
                completedCount++; // Increment completed count until all process are done
            }
        }
        
        // Display priority table
        displayPriorityTable(sortedProcess);

        // Get average turnaround and waiting time
        System.out.println("\nAverage Turnaround Time: " + calculateAverageTurnAroundTime(avgTurnAroundTime, listProcesses.size()));
        System.out.println("Average Waiting Time: " + calculateAverageWaitingTime(avgWaitingTime, listProcesses.size()));

        // Display gantt chart
        displayGanttChart(Arrays.asList(sortedProcess));

        System.out.print("Press ENTER to continue...");
        scanner.nextLine();
    }

    public double calculateAverageTurnAroundTime(double totalTurnAroundTime, int numProcess) {
        return totalTurnAroundTime/numProcess;
    }
    public double calculateAverageWaitingTime(double totalWaitingTime, int numProcess) {
        return totalWaitingTime/numProcess;
    }

    public int calculateCompletionTimeProcess(int cpuTime, int burstTime){
        return cpuTime + burstTime;
    }
    public int calculateTurnAroundTimeProcess(int completionTime, int arrivalTime){
        return completionTime - arrivalTime;
    }
    public int calculateWaitingTimeProcess(int turnAroundTime, int burstTime){
        return turnAroundTime - burstTime;
    }
}
