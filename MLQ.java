// Kobe's Part

import java.util.*;

public class MLQ {

    /*
     * Algorithm Numbers
     * [1] Round Robin
     * [2] SRT
     * [3] FCFS
     */

    Scanner scanner = new Scanner(System.in);
    List<Process> listProcesses = new ArrayList<Process>();
    List<Process> queue1 = new ArrayList<Process>();
    List<Process> queue2 = new ArrayList<Process>();
    List<Process> queue3 = new ArrayList<Process>();

    List<Process> finalGanttChart = new ArrayList<Process>();

    final boolean debug = true; // SET TRUE IF USING DEBUG PROCESS LIST

    MLQ(){

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

        int currentProcessID = 0; // Increments every new process
        while (createNewProcess){
            Process newProcess = new Process(currentProcessID);
            newProcess.inputData(true);

            listProcesses.add(newProcess);
            currentProcessID++;

            System.out.print("Create another process? [Y/N]: ");
            createNewProcess = (scanner.nextLine().toUpperCase().equalsIgnoreCase("Y"));
        }}

        // GUIDE: ID | BT | AT | PL

        // Predetermine process list for DEBUGGING
        List<Process> debugListQueue1 = new ArrayList<Process>();
        List<Process> debugListQueue2 = new ArrayList<Process>();
        List<Process> debugListQueue3 = new ArrayList<Process>();

        debugListQueue1.add(new Process(2, 7, 3, 1));
        debugListQueue1.add(new Process(4, 5, 12, 1));
        debugListQueue1.add(new Process(5, 8, 50, 1));

        debugListQueue2.add(new Process(1, 10, 0, 2));
        debugListQueue2.add(new Process(3, 6, 4, 2));


        debugListQueue3.add(new Process(10, 1, 2, 3));
        debugListQueue3.add(new Process(11, 3, 4, 3));


        // FOR DEBUGGING
        //int timeQuantum = 4;
        //RoundRobin rr = new RoundRobin(debugListProcesses, timeQuantum);
        //rr.start();

        // FOR DEBUGGING
        //FCFS fcfs = new FCFS(debugListProcesses);
        //fcfs.start();

        // FOR DEBUGGING
        //SRT srt = new SRT(debugListProcesses);
        //srt.start();

        displayAllQueues(debugListQueue1, debugListQueue2, debugListQueue3);
        start(debugListQueue1, debugListQueue2, debugListQueue3);

    }


    public void start(List<Process> queue1, List<Process> queue2, List<Process> queue3){
        int cpuCycle = 0;

        RoundRobin rr = new RoundRobin(queue1, 4, finalGanttChart);
        // RoundRobin rr4 = new RoundRobin(queue1, 4, finalGanttChart);
        // RoundRobin rr3 = new RoundRobin(queue2, 3, finalGanttChart);
        
        SRT srt = new SRT(queue2, finalGanttChart);
        FCFS fcfs = new FCFS(queue3, finalGanttChart);

        while (!checkIfAllProcessesDone(queue1, queue2, queue3)){
            
            boolean queue1IsValid = checkIfQueueValid(queue1, cpuCycle);
            boolean queue2IsValid = checkIfQueueValid(queue2, cpuCycle);
            boolean queue3IsValid = checkIfQueueValid(queue3, cpuCycle);


            if (queue1IsValid){
                rr.startOneCycle(cpuCycle);
            }
            else if (queue2IsValid){
                srt.startOneCycle(cpuCycle);
            }
            else if (queue3IsValid){
                fcfs.startOneCycle(cpuCycle);
            }


            // Increment CPU cycle
            cpuCycle += 1;
        }

        rr.printGanttChart();
        fcfs.printGanttChart();
        srt.printGanttChart();
        printGanttChart(finalGanttChart);

    }

    public boolean checkIfQueueValid(List<Process> queue, int cpuCycle){
         boolean isValid = false;

         for (Process process : queue){

            if (process.getArrivalTime() <= cpuCycle){
                isValid = true;
                break;
            }
            
         }

         return isValid;
    }


    public boolean checkIfAllProcessesDone(List<Process> queue1, List<Process> queue2, List<Process> queue3){

        boolean allDone = true;

        // Check queue 1
        if (!queue1.isEmpty()) allDone = false;

        // Check queue 2
        if (!queue2.isEmpty()) allDone = false;

        // Check queue 3
        if (!queue3.isEmpty()) allDone = false;

        return allDone;
    }


    public void displayAllQueues(List<Process> queue1, List<Process> queue2, List<Process> queue3){
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
        System.out.print("[MLQ] GANTT CHART: |");

        for (Process currentProcess : ganttChart){
            System.out.printf("%s|", currentProcess.getProcessID());
        }
        System.out.println();
    }

}
