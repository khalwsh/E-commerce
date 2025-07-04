import customer.Customer;
import cart.Cart;
import product.*;
import product.interfaces.Expirable;
import service.Service;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        TestCheckout();
        System.out.println("================================================================================================");
        TestPaymentOfPurchases();
        System.out.println("================================================================================================");
        TestSendOnlyShippableProductsFromCartToShippingService();
        System.out.println("================================================================================================");
        TestCustomerBalanceInsufficent();
        System.out.println("================================================================================================");
        TestEmptyCard();
        System.out.println("================================================================================================");
        TestShippingFees();
        System.out.println("================================================================================================");
        TestCustomerCanAddProduct();
        System.out.println("================================================================================================");
        TestOrderMoreThanQuantity();
        System.out.println("================================================================================================");
        TestExpiredProduct();
        System.out.println("================================================================================================");
    }
    static private void TestPaymentOfPurchases(){
        Customer khaled = new Customer("khaled" , 10000);
        Cart cart = new Cart();
        Expirable cheese = new ExpirableProduct("cheese" , 100 , 2 ,LocalDate.now().plusDays(1));
        Product tv = new Product("Tv" , 100 , 2);
        Product ScratchCard = new Product("scratch card" , 100 , 2);
        cart.addItem((Product) cheese, 2);
        cart.addItem((Product) tv, 1);
        cart.addItem((Product) ScratchCard, 2);

        ExpiredShippableProduct franceCheese = new ExpiredShippableProduct("france cheese" , 100 , 2 ,LocalDate.now().plusDays(1) , 7);
        cart.addItem(franceCheese , 1);
        Service.paymentOfPurchases(khaled , cart);

        assert(khaled.getBalance() == 9389.50); // assert the balance get reduced to the correct value
        System.out.println("new balance is : " + khaled.getBalance());
    }
    static private void TestCheckout(){
        Customer khaled = new Customer("khaled" , 10000);
        Cart cart = new Cart();
        Expirable cheese = new ExpirableProduct("cheese" , 100 , 2 ,LocalDate.now().plusDays(1));
        Product tv = new Product("Tv" , 100 , 2);
        Product ScratchCard = new Product("scratch card" , 100 , 2);
        cart.addItem((Product) cheese, 2);
        cart.addItem((Product) tv, 1);
        cart.addItem((Product) ScratchCard, 2);

        ExpiredShippableProduct franceCheese = new ExpiredShippableProduct("france cheese" , 100 , 2 ,LocalDate.now().plusDays(1) , 7);
        cart.addItem(franceCheese , 1);
        Service.printCheckout(khaled , cart);
    }
    static private void TestSendOnlyShippableProductsFromCartToShippingService(){
        Cart cart = new Cart();
        Expirable cheese = new ExpirableProduct("cheese" , 100 , 2 ,LocalDate.now().plusDays(1));
        Product tv = new Product("Tv" , 100 , 2);
        Product ScratchCard = new Product("scratch card" , 100 , 2);
        cart.addItem((Product) cheese, 2);
        cart.addItem((Product) tv, 1);
        cart.addItem((Product) ScratchCard, 2);

        ExpiredShippableProduct franceCheese = new ExpiredShippableProduct("france cheese" , 100 , 2 ,LocalDate.now().plusDays(1) , 7);
        cart.addItem(franceCheese , 1);
        cart.addItem(new ShippableProduct("bicksts" , 100 , 1 , 9) , 1);
        Service.ShippingService(cart.getShippableItems());
    }
    static private void TestCustomerBalanceInsufficent(){
        Customer khaled = new Customer("khaled" , 10000);
        Cart cart = new Cart();
        Product tv = new Product("Tv" , 10001 , 2);
        cart.addItem((Product) tv, 1);
        try {
            Service.printCheckout(khaled, cart);
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }
    static private void TestEmptyCard(){
        Cart cart = new Cart();
        Customer khaled = new Customer("khaled" , 10000);
        try {
            Service.printCheckout(khaled, cart);
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }
    static private void TestShippingFees(){
        Customer khaled = new Customer("khaled" , 10000);
        Cart cart = new Cart();
        Expirable cheese = new ExpirableProduct("cheese" , 100 , 2 ,LocalDate.now().plusDays(1));
        Product tv = new Product("Tv" , 100 , 2);
        Product ScratchCard = new Product("scratch card" , 100 , 2);
        cart.addItem((Product) cheese, 2);
        cart.addItem((Product) tv, 1);
        cart.addItem((Product) ScratchCard, 2);

        ExpiredShippableProduct franceCheese = new ExpiredShippableProduct("france cheese" , 100 , 2 ,LocalDate.now().plusDays(1) , 7);
        cart.addItem(franceCheese , 1);

        assert(cart.getShippedPrice() == 10.50);
        Service.printCheckout(khaled , cart);
        System.out.println("Shipped price is : " + cart.getShippedPrice());
    }
    static private void TestCustomerCanAddProduct(){
        Customer khaled = new Customer("khaled" , 10000);
        Cart cart = new Cart();
        Product tv = new Product("Tv" , 100 , 2);
        Product ScratchCard = new Product("scratch card" , 100 , 2);
        try {
            cart.addItem((Product) tv, 1);
            cart.addItem((Product) ScratchCard, 2);
        }catch (Exception e){
            System.out.println(e.getMessage());
            assert(false);
        }
        Service.printCheckout(khaled , cart);
        System.out.println("product added successfully");
    }
    static private void TestOrderMoreThanQuantity(){
        Product tv = new Product("Tv" , 100 , 2);
        Cart cart = new Cart();
        try {
            cart.addItem(tv , 3);
            assert false;
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }
    static private void TestExpiredProduct(){
        Product Cheese = new ExpirableProduct("Cheese" , 100 , 2 , LocalDate.now().minusDays(1));
        Cart cart = new Cart();
        try {
            cart.addItem(Cheese, 1);
            assert false;
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }
}