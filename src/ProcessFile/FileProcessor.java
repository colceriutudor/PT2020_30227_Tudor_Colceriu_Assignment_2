package ProcessFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileProcessor {
    public static int clientNo = -1, queueNo = -1, tMax = -1, tArrivalMin = -1, tArrivalMax = -1, tServiceMin = -1, tServiceMax = -1;

    public static void readFile(String readPath) throws FileNotFoundException {
        String line;
        File input = new File(readPath); //fisierul din care se citeste, facut in directorul curent
        Scanner sc = new Scanner(input);
        line = sc.nextLine();
        clientNo = Integer.parseInt(line);
        line = sc.nextLine();
        queueNo = Integer.parseInt(line);
        line = sc.nextLine();
        tMax = Integer.parseInt(line);
        line = sc.nextLine();
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(line);
        m.find();
        tArrivalMin = Integer.parseInt(m.group(0));
        m.find();
        tArrivalMax = Integer.parseInt(m.group(0));
        line = sc.nextLine();
        Matcher m2 = p.matcher(line);
        m2.find();
        tServiceMin = Integer.parseInt(m2.group(0));
        m2.find();
        tServiceMax = Integer.parseInt(m2.group(0));
    }
    public static void writeOutput(String writePath, String writeOut) throws FileNotFoundException {
        File output = new File(writePath); //fisierul in care se scrie, generat cu numele dat ca parametru
        PrintWriter printer = new PrintWriter(output);
        printer.printf(writeOut);
        printer.close() ;
    }
}
