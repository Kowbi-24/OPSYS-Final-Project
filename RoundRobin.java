import java.util.*;

public class RoundRobin{
    final List<Process> listProcess;
    private int timeQuantum;

    RoundRobin(List<Process> listProcesses, int timeQuantum){
        this.listProcess = listProcesses;
        this.timeQuantum = timeQuantum;
    }

    // Getter Methods
    public int getTimeQuantum() {
        return timeQuantum;
    }

    // Setter Methods
    public void setTimeQuantum(int timeQuantum) {
        this.timeQuantum = timeQuantum;
    }
}
