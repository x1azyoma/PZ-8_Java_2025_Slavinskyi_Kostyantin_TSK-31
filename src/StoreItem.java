import java.util.Objects;

public class StoreItem {
    private Product product;
    private double price;

    public StoreItem(Product product, double price) {
        this.product = product;
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return product.getName() + " - " + price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreItem storeItem = (StoreItem) o;
        return Double.compare(storeItem.price, price) == 0 &&
                Objects.equals(product, storeItem.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, price);
    }
}