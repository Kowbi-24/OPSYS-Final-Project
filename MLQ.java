// Kobe's Part

import java.util.*;

public class MLQ {

    final boolean debug = false; // SET TRUE IF USING DEBUG PROCESS LIST

    /*
     * Algorithm Numbers
     * [1] Round Robin
     * [2] SRT
     * [3] FCFS
     */

    Scanner scanner = new Scanner(System.in);
    Process currProcess;
    List<Process> listProcesses = new ArrayList<Process>();
    List<Process> queue1 = new ArrayList<Process>();
    List<Process> queue2 = new ArrayList<Process>();
    List<Process> queue3 = new ArrayList<Process>();
    List<List<Process>> listQueue = new ArrayList<List<Process>>();

    List<Process> finalGanttChart = new ArrayList<Process>();

    double averageWaitingTime = 0;
    double averageTurnAroundTime = 0;

    

    MLQ(){
        listQueue.add(queue1);
        listQueue.add(queue2);
        listQueue.add(queue3);
    }

    public void initialize(){
        if (!debug)
        {System.out.println("\nThe MLQ Simulation implements the following algorithms for the different queues:");
        System.out.println("==============================================");
        System.out.println("| QUEUE # 1 | Round-Robin                    |");
        System.out.println("| QUEUE # 2 | (SRT) Shortest-Remaining-Time  |");
        System.out.println("| QUEUE # 3 | (FCFS) First-Come-First-Served |");
        System.out.println("==============================================");
        System.out.print("Press ENTER to continue...");
        scanner.nextLine();

        // User inputs data
        boolean createNewProcess = true;
        System.out.println("\nINPUT DATA FOR THE PROCESSES");

        int currentProcessID = 1; // Increments every new process
        while (createNewProcess){
            Process newProcess = new Process(currentProcessID);
            newProcess.inputData(true);
            newProcess.setRemainingBurstTime(newProcess.getBurstTime());

            // TODO: Add to appropriate list

            listQueue.get(newProcess.getPriorityLevel() - 1).add(newProcess);

            currentProcessID++;

            System.out.print(">> Create another process? [Y/N]: ");
            createNewProcess = (scanner.nextLine().toUpperCase().equalsIgnoreCase("Y"));
        }}

        // DEBUG LIST
        if (debug){
            queue1.add(new Process(2, 7, 3, 1));
            queue1.add(new Process(4, 5, 12, 1));
            queue1.add(new Process(5, 8, 50, 1));

            queue2.add(new Process(1, 10, 1, 2));
            queue2.add(new Process(3, 6, 4, 2));

            queue3.add(new Process(10, 1, 2, 3));
            queue3.add(new Process(11, 3, 4, 3));
        }

        displayAllQueues();
        start();
        displayProcessStatistics();
        
        System.out.print("Press ENTER to continue...");
        scanner.nextLine();

    }


    public void start(){
        int cpuCycle = 0;

        RoundRobin rr = new RoundRobin(queue1, 4, finalGanttChart);
        SRT srt = new SRT(queue2, finalGanttChart);
        FCFS fcfs = new FCFS(queue3, finalGanttChart);

        while (!checkIfAllProcessesDone()){
            
            boolean queue1IsValid = checkIfQueueValid(queue1, cpuCycle);
            boolean queue2IsValid = checkIfQueueValid(queue2, cpuCycle);
            boolean queue3IsValid = checkIfQueueValid(queue3, cpuCycle);


            if (queue1IsValid){
                rr.startOneCycle(cpuCycle);
                this.currProcess = rr.getCurrProcess();
            }
            else if (queue2IsValid){
                srt.startOneCycle(cpuCycle);
                this.currProcess = srt.getCurrProcess();
            }
            else if (queue3IsValid){
                fcfs.startOneCycle(cpuCycle);
                this.currProcess = fcfs.getCurrProcess();
            }

            incrementAllWaitingTime();


            // Increment CPU cycle
            cpuCycle += 1;
        }

        

    }

    public void incrementAllWaitingTime(){
        // QUEUE1
        for (Process currProcess : queue1){
            if (this.currProcess != null){
                if (currProcess.getProcessID() != this.currProcess.getProcessID() && !currProcess.getFinished()){
                    currProcess.incrementWaitingTime();
                }
            }
        }

        // QUEUE2
        for (Process currProcess : queue2){
            if (this.currProcess != null){
                if (currProcess.getProcessID() != this.currProcess.getProcessID() && !currProcess.getFinished()){
                    currProcess.incrementWaitingTime();
                }
            }
        }

        // QUEUE3
        for (Process currProcess : queue3){
            if (this.currProcess != null){
                if (currProcess.getProcessID() != this.currProcess.getProcessID() && !currProcess.getFinished()){
                    currProcess.incrementWaitingTime();
                }
            }
        }
    }

    public boolean checkIfQueueValid(List<Process> queue, int cpuCycle){
         boolean isValid = false;


         for (Process process : queue){
            if (process.getArrivalTime() <= cpuCycle && !process.getFinished()){
                isValid = true;
                break;
            }
            
         }

         return isValid;
    }


    public boolean checkIfAllProcessesDone(){

        boolean allDone = true;

        // Check queue 1
        for (Process process : queue1){
            if (!process.getFinished()){
                allDone = false;
                break;
            }
        }

        // Check queue 2
        for (Process process : queue2){
            if (!process.getFinished()){
                allDone = false;
                break;
            }
        }

        // Check queue 3
        for (Process process : queue3){
            if (!process.getFinished()){
                allDone = false;
                break;
            }
        }

        return allDone;
    }


    public void displayAllQueues(){
        System.out.println("=== DISPLAYING ALL QUEUES =======");

        System.out.print("  QUEUE 1 --- |");
        // Print Queue 1
        for (Process process : queue1){
            System.out.printf( " %s |", process.getProcessID());
        }
        System.out.println();

        
        // Print Queue 2
        System.out.print("  QUEUE 2 --- |");
        for (Process process : queue2){
            System.out.printf( " %s |", process.getProcessID());
        }
        System.out.println();

        // Print Queue 3
        System.out.print("  QUEUE 3 --- |");
        for (Process process : queue3){
            System.out.printf( " %s |", process.getProcessID());
        }
        System.out.println("\n=================================");
    }


    public void printGanttChart(List<Process> ganttChart){
        System.out.print("\n[MLQ] GANTT CHART: |");

        for (Process currentProcess : ganttChart){
            System.out.printf("%s|", currentProcess.getProcessID());
        }
        System.out.println("\n");
    }


    public void displayProcessStatistics(){

        int i=0;
        for (List<Process> queue : listQueue){
            System.out.println();
            System.out.println("=================================================================================================================");
            System.out.printf ("||                                                   QUEUE %1.1s                                                   ||\n", i+1);
            System.out.println("=================================================================================================================");
            System.out.println("||       ID     | Burst Time | Arrival Time | Waiting Time | Turnaround Time | Response Time | Completion Time ||");
            System.out.println("||-------------------------------------------------------------------------------------------------------------||");
            for (Process currProcess : queue){
                System.out.printf("|| PROCESS # %2.2s |     %3.3s    |      %3.3s     |      %3.3s     |        %3.3s      |      %3.3s      |       %3.3s       ||\n", currProcess.getProcessID(), currProcess.getBurstTime(), currProcess.getArrivalTime(),currProcess.getWaitingTime(),currProcess.getTurnAroundTime(), currProcess.getResponseTime(), currProcess.getCompetionTime());
            }
            System.out.println("=================================================================================================================");
            System.out.println();

            i++;

        }

        
        computeAverageWaitingTime();
        computeAverageTurnaroundTime();

        printGanttChart(finalGanttChart);

        System.out.printf(">> Average Waiting Time: %.2f\n", averageWaitingTime);
        System.out.printf(">> Average Turnaround Time: %.2f\n", averageTurnAroundTime);
        System.out.println();
        
    }


    public void computeAverageWaitingTime(){

        for (List<Process> queue : listQueue){
            for (Process process : queue){
                averageWaitingTime += process.getWaitingTime();
            }
        }

        // Divide by total number of processes
        averageWaitingTime /= (queue1.size() + queue2.size() + queue3.size());
    }

    public void computeAverageTurnaroundTime(){

        for (List<Process> queue : listQueue){
            for (Process process : queue){
                averageTurnAroundTime += process.getTurnAroundTime();
            }
        }

        // Divide by total number of processes
        averageTurnAroundTime /= (queue1.size() + queue2.size() + queue3.size());
    }

}
