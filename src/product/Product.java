package product;

public class Product {
    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean Available(int requiredQuantity){
        return requiredQuantity <= this.quantity;
    }

    public void buyProduct(int amount) throws IllegalArgumentException{
        if(!Available(amount))
            throw new IllegalArgumentException("Not enough products");
        this.quantity -= amount;
    }

    @Override
    public boolean equals(Object obj) {
        return this.name.equals(((Product)obj).name) &&
        this.price == ((Product)obj).price && this.quantity == ((Product)obj).quantity;
    }
}
