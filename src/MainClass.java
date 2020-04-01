import ProcessFile.FileProcessor;
import java.io.FileNotFoundException;

public class MainClass extends FileProcessor {
    public static void main(String[] args) throws FileNotFoundException {
        readFile(args[0]);
        //readFile("input.txt");
        CustomerGenerator generator = new CustomerGenerator(clientNo, tArrivalMin, tArrivalMax, tServiceMin, tServiceMax);
        Simulation store = new Simulation(queueNo, clientNo, tMax, generator.customers);
        store.run();
        writeOutput(args[1], store.toString());
        System.out.println("Done!");
    }
}