package cart;

import product.Product;
import product.interfaces.Expirable;
import product.interfaces.Shippable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Cart {
    private List<CartItem> items;
    public Cart(){
        items = new ArrayList<CartItem>();
    }

    public void addItem(Product product , int quantity) throws IllegalArgumentException{
        if(quantity <= 0){
            throw new IllegalArgumentException("you can't take amount less than 1");
        }
        if(!product.Available(quantity)){
            throw new IllegalArgumentException("you can't take amount move than "+product.getQuantity());
        }
        if(product instanceof Expirable && ((Expirable) product).isExpired())
            throw new IllegalArgumentException("you can't take expired products");

        items.add(new CartItem(product, quantity));
    }

    public boolean isEmpty(){
        return items.isEmpty();
    }

    public List<CartItem> getItems(){
        return items;
    }

    public List<Expirable> getExpirableItems() {
        return items.stream()
                .filter(item -> item instanceof Expirable)
                .map(item -> (Expirable) item)
                .collect(Collectors.toList());
    }


    public List<Shippable> getShippableItems() {
        return items.stream()
                .map(CartItem::getProduct)
                .filter(product -> product instanceof Shippable)
                .map(product -> (Shippable) product)
                .collect(Collectors.toList());
    }


    public double getShippedPrice() {
        return items.stream()
                .mapToDouble(CartItem::getShippingFees)
                .sum();
    }

    public double getSubtotal() {
        return items.stream()
                .mapToDouble(CartItem::getSubtotal)
                .sum();
    }

    public double getTotalWeight() {
        return items.stream()
                .mapToDouble(CartItem::getWeight)
                .sum();
    }

    public double getTotalAmount() {
        return items.stream()
                .mapToDouble(CartItem::getTotalAmount)
                .sum();
    }

}
