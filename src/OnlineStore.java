import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

public class OnlineStore {
    private String name;
    // Змінено List на Set згідно з завданням
    private Set<StoreItem> items;

    public OnlineStore(String name) {
        this.name = name;
        this.items = new HashSet<>(); // Використовуємо HashSet
    }

    public void addItem(Product product, double price) {
        items.add(new StoreItem(product, price));
    }

    public String getName() {
        return name;
    }

    public Set<StoreItem> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "Store: " + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OnlineStore that = (OnlineStore) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}