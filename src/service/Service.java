package service;

import customer.Customer;
import cart.Cart;
import cart.CartItem;
import product.interfaces.Shippable;
import product.interfaces.Expirable;

// Utility class for handling checkout processes
public class Service {

    public static void printCheckout(Customer customer, Cart cart) {
        VerifyValidityOfPurchases(customer, cart);
        printShipmentNotice(cart);
        printCheckoutReceipt(customer, cart);
    }

    // Print the shipment notice for shippable items
    private static void printShipmentNotice(Cart cart) {
        System.out.println("** Shipment notice **");
        for (CartItem item : cart.getItems()) {
            if (item.getProduct() instanceof Shippable) {
                // Assuming item.getWeight() returns total weight in kg for the item
                System.out.printf("%dx %s %.0fg%n",
                        item.getQuantity(),
                        item.getProduct().getName(),
                        item.getWeight() * 1000);  // Convert kg to g
            }
        }
        System.out.printf("Total package weight %.1fkg%n%n", cart.getTotalWeight());
    }

    // Print the checkout receipt with all items and totals
    private static void printCheckoutReceipt(Customer customer, Cart cart) {
        System.out.println("** Checkout receipt **");
        for (CartItem item : cart.getItems()) {
            System.out.printf("%dx %s\t%.2f%n",
                    item.getQuantity(),
                    item.getProduct().getName(),
                    item.getSubtotal());
        }
        System.out.println("----------------------------------------");
        System.out.printf("Subtotal\t%.2f%n", cart.getSubtotal());
        System.out.printf("Shipping\t%.2f%n", cart.getShippedPrice());
        System.out.printf("Amount\t\t%.2f%n", cart.getTotalAmount());
        System.out.printf("Remaining Balance\t\t%.2f%n", customer.getBalance() - cart.getTotalAmount());
    }

    // Process the payment: withdraw from customer balance and decrease item stock
    public static void paymentOfPurchases(Customer customer, Cart cart) {
        VerifyValidityOfPurchases(customer, cart);
        customer.withdraw(cart.getTotalAmount());
        for (CartItem item : cart.getItems()) {
            item.getProduct().buyProduct(item.getQuantity());
        }
        System.out.println("** Successfully payment **");
    }

    // Verify the validity of the purchases
    public static void VerifyValidityOfPurchases(Customer customer, Cart cart) {
        if (cart.isEmpty()) {
            throw new IllegalArgumentException("The cart can't be empty");
        }
        for (CartItem item : cart.getItems()) {
            if (!item.getProduct().Available(item.getQuantity())) {
                throw new IllegalArgumentException("Not enough quantity in stock for " + item.getProduct().getName());
            }
            if (item.getProduct() instanceof Expirable && ((Expirable) item.getProduct()).isExpired()) {
                throw new IllegalArgumentException("Cannot purchase expired product: " + item.getProduct().getName());
            }
        }
        if (customer.getBalance() < cart.getTotalAmount()) {
            throw new IllegalArgumentException("The balance is insufficient");
        }
    }
}