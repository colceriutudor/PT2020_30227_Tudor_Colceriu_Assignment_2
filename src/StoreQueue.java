public class StoreQueue {
    private Node front, rear;
    private int currentSize;
    private int maxSize;

    class Node {
        Customer customer;
        Node next;
    }

    public StoreQueue(int maxSize) {
        front = null;
        rear = null;
        currentSize = 0;
        this.maxSize = maxSize;
    }

    public boolean isEmpty() {
        return (currentSize == 0);
    }

    public boolean isFull() {
        return (currentSize == maxSize);
    }

    public void dequeue() {
        Customer c = front.customer;
        front = front.next;
        if (isEmpty()) {
            rear = null;
        }
        currentSize--;
    }

    public void enqueue(Customer data) {
        if(!isFull()) {
            Node oldRear = rear;
            rear = new Node();
            rear.customer = data;
            rear.next = null;
            if (isEmpty()) {
                front = rear;
            } else {
                oldRear.next = rear;
            }
            currentSize++;
        }
    }

    public int getCurrentSize() {
        return currentSize;
    }
    public Customer getFirst(){
        return this.front.customer;
    }

    public Node getFront() {
        return front;
    }
    public Node getRear() {
        return rear;
    }

    public int getWaitingTime(){
        int totalWaitingTime = 0;
        if(!isEmpty()){
            int size = 0;
            Node start = front;
            while( size < currentSize ) {
                totalWaitingTime += front.customer.getServiceTime();
                size++;
            }
        }
        else totalWaitingTime = 0;
        return totalWaitingTime;
    }

    @Override
    public String toString() {
        String output = new String();
        int size = 0;
        Node start = front;
        while (size < currentSize) {
            if(size == 0) output += start.customer.toString();
            else output += ","+start.customer.toString();
            size++;
            start = start.next;
        }
        return output;
    }
}