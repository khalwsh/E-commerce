import customer.Customer;
import cart.Cart;
import product.*;
import service.Service;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        ShippableProduct cheese = new ShippableProduct("Cheese", 100, 10, 0.4);
        ShippableProduct biscuits = new ShippableProduct("Biscuits", 150, 5, 0.7);
        Product ebook = new Product("E-book", 50, 100);
        ExpiredShippableProduct oldCheese = new ExpiredShippableProduct("Old Cheese", 80, 3, LocalDate.now().minusDays(1), 0.5);

        Customer john = new Customer("John", 600);
        Customer poorJohn = new Customer("Poor John", 100);

        // Test Case 1: Valid Purchase
        System.out.println("Test Case 1: Valid Purchase");
        Cart cart1 = new Cart();
        cart1.addItem(cheese, 2);
        cart1.addItem(biscuits, 1);
        cart1.addItem(ebook, 3);
        Service.printCheckout(john, cart1);
        Service.paymentOfPurchases(john, cart1);
        // Expected: Shipment notice with Cheese and Biscuits, receipt with all items, total 502.25 (500 subtotal + 2.25 shipping), balance 97.75

        System.out.println("\nTest Case 2: Expired Product");
        Cart cart2 = new Cart();
        try {
            cart2.addItem(oldCheese, 1); // Add expired product
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        try {
            Service.printCheckout(john, cart2);
        } catch (IllegalArgumentException e) {
            System.out.println("Exception caught: " + e.getMessage());
        }

        System.out.println("\nTest Case 3: Insufficient Stock");
        Cart cart3 = new Cart();
        try {
            cart3.addItem(cheese, 11); // Request 11, stock is 8}
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

        // Test Case 4: Insufficient Balance
        System.out.println("\nTest Case 4: Insufficient Balance");
        Cart cart4 = new Cart();
        cart4.addItem(cheese, 2);
        try {
            Service.printCheckout(poorJohn, cart4);
        } catch (IllegalArgumentException e) {
            System.out.println("Exception caught: " + e.getMessage());
            // Expected: "The balance is insufficient"
        }
    }
}