import java.util.*;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int chosenAlgo = chooseAlgo();

        if (chosenAlgo == 1){
            // MLQ Goes here
        }
        else if (chosenAlgo == 2){
            // Priority Queue goes here
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
}
