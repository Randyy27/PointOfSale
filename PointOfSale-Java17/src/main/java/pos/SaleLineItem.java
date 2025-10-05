package pos;

public class SaleLineItem {
    private final ProductSpecification productSpecification;
    private int quantity;

    public SaleLineItem(ProductSpecification ps, int quantity) {
        this.productSpecification = ps;
        this.quantity = quantity;
    }

    public void incrementQuantity(int q) { this.quantity += q; }
    public double subtotal() { return Math.rint(productSpecification.getPrice() * quantity * 100.0)/100.0; }
    public ProductSpecification getProductSpecification() { return productSpecification; }
    public int getQuantity() { return quantity; }

    public void print() {
        System.out.printf("%s %d %.2f%n", productSpecification.getName(), quantity, subtotal());
    }
}
