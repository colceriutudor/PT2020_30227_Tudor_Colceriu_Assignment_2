import java.util.LinkedList;

public class Line {
    LinkedList<Customer> line = new LinkedList<Customer>();
    private int maxSize, currentSize = 0;

    public Line(int maxSize){
        this.maxSize = maxSize;
    }

    public void addCustomer(Customer c){
        if(line.size() != maxSize)
            line.add(c);
        else System.out.println("Line is full!");
        currentSize = line.size();
    }
    public void removeCustomer(){
        if(!this.line.isEmpty())
            this.line.remove(); //remove the head
        else System.out.println("Line is empty!");
        currentSize = this.line.size();
    }
    public boolean isLineEmpty(){
        if(this.line.peek() == null) return true;
        else return false;
    }
    public boolean isLineFull(){
        return this.line.size() == maxSize;
    }
    public int getSize(){
        return this.line.size();
    }
    @Override
    public String toString() {
        String output = "(";
        for (Customer c: this.line) {
            output += c.getCustomerID() + ",";
        }
        return output.substring(0,output.length() - 1) + ")";
    }
}
