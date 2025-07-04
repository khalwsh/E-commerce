package product;

import product.interfaces.Expirable;
import product.interfaces.Shippable;

import java.time.LocalDate;

public class ExpiredShippableProduct extends Product implements Shippable , Expirable {
    private double weight;
    private LocalDate expiredDate;
    public ExpiredShippableProduct(String name, double price, int quantity , LocalDate expiredDate , double weight) {
        super(name, price, quantity);
        this.expiredDate = expiredDate;
        this.weight = weight;
    }

    @Override
    public String getName(){
        return super.getName();
    }

    @Override
    public boolean isExpired() {
        return LocalDate.now().isAfter(expiredDate);
    }

    @Override
    public double getWeight() {
        return this.weight;
    }
}
