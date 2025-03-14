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

        

        List<Process> debugListProcesses = new ArrayList<>();
        debugListProcesses.add(new Process(0, 10, 10, 1));
        debugListProcesses.add(new Process(1, 4, 2, 1));
        debugListProcesses.add(new Process(2, 5, 3, 1));
        debugListProcesses.add(new Process(3, 7, 4, 1));
        debugListProcesses.add(new Process(4, 8, 10, 1));

        // FOR DEBUGGING
        //int timeQuantum = 4;
        //RoundRobin rr = new RoundRobin(debugListProcesses, timeQuantum);
        //rr.start();

        // FOR DEBUGGING
        FCFS fcfs = new FCFS(debugListProcesses);
        fcfs.start();

    }

}
