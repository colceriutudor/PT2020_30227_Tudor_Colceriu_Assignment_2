public class Customer {

    private int customerID, arrivalTime, serviceTime, waitingTime, initialServiceTime;

    public Customer(){

    }
    public Customer(int customerID, int arrivalTime, int serviceTime){
        this.customerID = customerID;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        initialServiceTime = new Integer(serviceTime);
        waitingTime = 0;
    }

    public int getInitialServiceTime() { return initialServiceTime; }

    public int getCustomerID() { return this.customerID; }
    public void setCustomerID(int customerID) { this.customerID = customerID; }

    public int getArrivalTime() { return this.arrivalTime; }
    public void setArrivalTime(int arrivalTime) { this.arrivalTime = arrivalTime; }

    public int getServiceTime() { return this.serviceTime; }
    public void setServiceTime(int serviceTime) { this.serviceTime = serviceTime; }

    public int getWaitingTime() { return waitingTime; }

    public void setWaitingTime(int waitingTime) { this.waitingTime = waitingTime; }

    @Override
    public String toString() {
        return "(" + customerID + "," + arrivalTime + "," + serviceTime +")";
    }
}
