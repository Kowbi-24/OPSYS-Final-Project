import java.util.*;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        while (true){
            int chosenAlgo = chooseAlgo();

            if (chosenAlgo == 1){
                // MLQ Goes here, edit the function below
                chosenMLQ();
            }
            else if (chosenAlgo == 2){
                // Priority Queue goes here, edit the function below
                chosenPriorityQueue();
            }
            else if (chosenAlgo == 3){
                System.out.println(">> EXITING PROGRAM...");
                break;
            }
        }
    }


    public static int chooseAlgo(){

        // Ask user to choose algorithm
        System.out.println("[1] MLQ     [2] Priority Queue   [3] EXIT PROGRAM");
        System.out.print("Choose Algorithm: ");
        int chosenAlgo = scanner.nextInt();

        return chosenAlgo;
    }

    public static void chosenMLQ(){
        MLQ mlq = new MLQ();
        mlq.initialize();
    }

    public static void chosenPriorityQueue(){
        PriorityQueue pq = new PriorityQueue();
        pq.initializePriority();
    }
}
