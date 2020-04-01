import java.util.*;
class CompArrivalTime implements Comparator<Customer> {
    public int compare(Customer c1, Customer c2) {
        return c1.getArrivalTime() - c2.getArrivalTime();
    }
}

public class CustomerGenerator {
    public ArrayList<Customer> customers;

    public CustomerGenerator(int totalClients, int minArrtTime, int maxArrTime, int minServ, int maxServ){
        this.customers = new ArrayList();
        int idClient = 0;
        Random random = new Random();
        while(totalClients != 0){
            totalClients--;
            idClient++;
            //generarea de clienti cu parametrii specificati din citirea fisierului
            Customer client = new Customer(idClient, random.nextInt(maxArrTime - minArrtTime) + minArrtTime, random.nextInt(maxServ - minServ) + minServ);
            this.customers.add(client);
        }
        Collections.sort(customers,new CompArrivalTime());
    }
    public void displayCustomers(){
        System.out.println("All the generated customers + customerInfo");
        for (Customer c: this.customers) {
            System.out.println(c.toString());
        }
    }
}
