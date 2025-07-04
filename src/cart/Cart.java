package cart;

import product.Product;
import product.interfaces.Expirable;
import product.interfaces.Shippable;

import java.util.ArrayList;
import java.util.List;

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

    public List <Expirable> getExpirableItems(){
        List <Expirable> ExpirableItems = new ArrayList<>();
        for(CartItem item : items){
            if(item instanceof Expirable){
                ExpirableItems.add((Expirable) item);
            }
        }
        return ExpirableItems;
    }

    public List <Shippable> getShippableItems(){
        List <Shippable> ShippableItems = new ArrayList<>();
        for(CartItem item : items){
            if(item instanceof Shippable){
                ShippableItems.add((Shippable) item);
            }
        }
        return ShippableItems;
    }

    public double getShippedPrice(){
        double total = 0;
        for(CartItem item : items){
            total += item.getShippingFees();
        }
        return total;
    }


    public double getSubtotal(){
        double total = 0;
        for(CartItem item : items){
            total += item.getSubtotal();
        }
        return total;
    }

    public double getTotalWeight(){
        double total = 0;
        for(CartItem item : items){
            total += item.getWeight();
        }
        return total;
    }

    public double getTotalAmount(){
        double total = 0;
        for(CartItem currentItem: items){
            total += currentItem.getTotalAmount();
        }
        return total;
    }
}
