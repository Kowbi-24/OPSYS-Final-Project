import java.util.*;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int chosenAlgo = chooseAlgo();

        if (chosenAlgo == 1){
            // MLQ Goes here, edit the function below
            chosenMLQ();
        }
        else if (chosenAlgo == 2){
            // Priority Queue goes here, edit the function below
            chosenPriorityQueue();
        }
        else{
            // Invalid input goes here
        }
    }


    public static int chooseAlgo(){

        // Ask user to choose algorithm
        System.out.println("[1] MLQ     [2] Priority Queue");
        System.out.print("Choose Algorithm: ");
        int chosenAlgo = scanner.nextInt();

        return chosenAlgo;
    }

    public static void chosenMLQ(){
        MLQ mlq = new MLQ();
        mlq.initialize();
    }

    public static void chosenPriorityQueue(){
        
    }
}
