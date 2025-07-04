package customer;

public class Customer {

    private String name;
    private double balance;

    public Customer(String name, double balance) {
        // Ensure that the balance is positive value
        if(balance <= 0){
            throw new IllegalArgumentException("Balance can't be a non-positive value");
        }
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void withdraw(double amount){
        if(amount > balance){
            throw  new IllegalArgumentException("There 's no enough balance to withdraw");
        }
        this.balance -= amount;
    }
}