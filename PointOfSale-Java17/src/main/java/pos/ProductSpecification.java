package pos;

public class ProductSpecification {
    private final String name;
    private final double price;
    public ProductSpecification(String name, double price) {
        this.name = name;
        this.price = price;
    }
    public String getName() { return name; }
    public double getPrice() { return price; }
}
