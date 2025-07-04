package cart;

import product.Product;
import product.interfaces.Shippable;

public class CartItem {

    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getWeight(){
        if(product instanceof Shippable){
            return ((Shippable)product).getWeight();
        }
        return 0;
    }
    public double getSubtotal(){
        return product.getPrice() * quantity;
    }

    public double getShippingFees(){
        if(product instanceof Shippable){
            return ((Shippable)product).getWeight() * quantity * Shippable.RATE_PER_UNIT;
        }
        return 0;
    }

    public double getTotalAmount(){
        return getSubtotal() + getShippingFees();
    }
}