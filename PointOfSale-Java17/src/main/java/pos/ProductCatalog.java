package pos;

import java.util.*;

public class ProductCatalog {
    private final Map<String, ProductSpecification> byName = new HashMap<>();

    public ProductCatalog() {
        // Seed demo products similar to slides
        add(new ProductSpecification("Moritz", 1.5));
        add(new ProductSpecification("Coca-cola", 1.2));
        add(new ProductSpecification("Donut", 1.0));
    }

    public void add(ProductSpecification ps) {
        byName.put(ps.getName().toLowerCase(Locale.ROOT), ps);
    }

    public ProductSpecification searchByName(String name) {
        ProductSpecification ps = byName.get(name.toLowerCase(Locale.ROOT));
        if (ps == null) throw new IllegalArgumentException("Product not found: " + name);
        return ps;
    }
}
