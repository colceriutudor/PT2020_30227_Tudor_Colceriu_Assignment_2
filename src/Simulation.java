import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Simulation extends Thread implements Runnable {
    private int linesNo; //number of queues (line at the store)
    private int customers; //total nr of customers
    private int maxTime; //total time that the store is opened (i.e. tMaxSimulation_
    private int currentTime;
    private float averageWaitingTime;
    private StoreQueue[] lines;
    private Thread[] processCustomer;
    private ArrayList<Customer> allCustomers;
    private ArrayList<Customer> displayCustomers;
    private boolean stop; //process-running flag
    private AtomicInteger i = new AtomicInteger();
    private String returnText;

    public Simulation(int linesNo, int customers, int maxTime, ArrayList<Customer> allCustomers) {
        this.allCustomers = allCustomers;
        displayCustomers = new ArrayList<Customer>(allCustomers);
        currentTime = 0;
        stop = false;
        this.linesNo = linesNo;
        this.customers = customers;
        this.maxTime = maxTime;
        this.lines = new StoreQueue[linesNo];
        this.processCustomer = new Thread[customers];
    }
    private int chooseClearestLine() {
        int min = customers + 1; //above max nr of customers
        for (int i = 0; i < linesNo; i++) {
            if (lines[i].getCurrentSize() < min && lines[i].getCurrentSize() != customers) //not all lines are not full
                min = i; //holds the line nr that has the least customers
        }
        return min;
    }
    private int chooseWaitingTime() {
        int minWaitingTime = customers + 1; //above max nr of customers
        for (int i = 0; i < linesNo; i++) {
            if(lines[i].isEmpty()) return i;
            if(lines[i].getWaitingTime() <= minWaitingTime && !lines[i].isFull())
                minWaitingTime = i; //this is the line where you have to wait the least ammount of time
        }
        return minWaitingTime;
    }

    @Override
    public void run() {
        while (!stop) {
            while (i.get() < linesNo && !stop) {
                final int index;
                index = i.get();
                lines[i.get()] = new StoreQueue(customers);
                processCustomer[i.get()] = new Thread() {
                    @Override
                    public void run() {
                        while (!stop) {
                            try {
                                //full queue : process the first client in the queue, not allow any other clients
                                if (lines[index].isFull())
                                    this.sleep(lines[index].getFirst().getServiceTime());
                                //empty queue : close it and wait until reopened
                                if (lines[index].isEmpty())
                                    //the queue is closed, so don't do anything until a client comes
                                    //queue has some customers in it: process the first client in the queue
                                    if (!lines[index].isFull() && !lines[index].isEmpty())
                                        this.sleep(lines[index].getFirst().getServiceTime());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
                processCustomer[i.get()].start();
                i.getAndIncrement();
            }
            int bestLine;
            while (!stop) {
                ArrayList<Customer> removeThis = new ArrayList<Customer>();
                for (Customer c : allCustomers) {
                    //customer should sit in the queue if it's the time to serve him
                    if (c.getArrivalTime() == currentTime) {
                        bestLine = chooseWaitingTime();
                        if (!lines[bestLine].isFull()) {
                            lines[bestLine].enqueue(c);
                            removeThis.add(c);
                        }
                    }
                    //the customer is already in a queue, but another queue may be better for him, or empty
                    else if(c.getServiceTime() != 0 && c.getArrivalTime() > currentTime && c.getServiceTime() != c.getInitialServiceTime()){
                        bestLine = chooseClearestLine();
                        if (!lines[bestLine].isFull()) {
                            lines[bestLine].enqueue(c);
                            removeThis.add(c);
                        }
                    }
                }
                for (Customer c : removeThis) {
                    displayCustomers.remove(c);
                }
                for (StoreQueue l : lines) {
                    if (!l.isEmpty()) {
                        StoreQueue.Node start = l.getFront();
                        if (start.customer.getServiceTime() == 1) {
                            averageWaitingTime += currentTime - start.customer.getArrivalTime(); //add the time this client waited in line to get served
                            l.dequeue();
                        } else start.customer.setServiceTime(start.customer.getServiceTime() - 1);
                    } else continue;

                }
                returnText += "Waiting clients: " + displayCustomers.toString() + "\n\n" + "Time: " + currentTime + "\n";
                for (int i = 1; i <= linesNo; i++) {
                    if (lines[i - 1].isEmpty()) returnText += "Queue " + i + ": empty" + "\n";
                    else returnText += "Queue " + i + ": " + lines[i - 1].toString() + "\n";
                }
                returnText += "\n";
                if (currentTime != maxTime) currentTime++;
                else {
                    stop = true; //time to close the store
                    returnText += "\n" + "Average waiting time: " + averageWaitingTime / customers + "\n"; // avg waiting time = all avg waiting times / nr. of clients
                }
            }
        }
    }

    @Override
    public String toString() { return returnText; }
}

