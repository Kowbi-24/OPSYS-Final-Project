// Kobe's Part

import java.util.*;

public class MLQ {

    Scanner scanner = new Scanner(System.in);

    private String algorithmSelected;
    private int numOfQueues;

    MLQ(String algorithmSelected){
        this.algorithmSelected = algorithmSelected;
    }


    public void initialize(){
        System.out.println("=== INITIALIZING MLQ =====");
        System.out.print("- Input Number of Queues: ");
        numOfQueues = Integer.parseInt(scanner.nextLine());
    }


    // Getter Methods
    public String getAlgorithmSelected() {
        return algorithmSelected;
    }

    // Setter Methods
    public void setAlgorithmSelected(String algorithmSelected) {
        this.algorithmSelected = algorithmSelected;
    }
}
