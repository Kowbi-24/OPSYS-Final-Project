// Kobe's Part

import java.util.*;

public class MLQ {

    /*
     * Algorithm Numbers
     * [1] FCFS
     * [2] Round Robin
     * [3] SRT
     */

    Scanner scanner = new Scanner(System.in);

    private Map<Integer, Integer> algoMap; // <Queue Num, AlgoNum>
    private int numOfQueues;

    MLQ(){

    }


    public void initialize(){
        System.out.println("=== INITIALIZING MLQ =====");
        System.out.print("- Input Number of Queues: ");
        numOfQueues = Integer.parseInt(scanner.nextLine());

        for (int i=0; i<numOfQueues; i++){
            System.out.printf("- Choose Algorithm for (QUEUE %s)", i);
        }
    }

}
