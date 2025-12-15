import java.util.*;

public class ProductSearchService {
    // Змінено List на Set
    private Set<OnlineStore> stores;

    public ProductSearchService() {
        this.stores = new HashSet<>();
    }

    public void addStore(OnlineStore store) {
        stores.add(store);
    }

    // --- Базові методи з Лаб 10 (адаптовані під Set) ---

    public double findMinPrice(String productName) {
        double minPrice = Double.MAX_VALUE;
        boolean found = false;

        for (OnlineStore store : stores) {
            for (StoreItem item : store.getItems()) {
                if (item.getProduct().getName().equals(productName)) {
                    if (item.getPrice() < minPrice) {
                        minPrice = item.getPrice();
                        found = true;
                    }
                }
            }
        }
        return found ? minPrice : -1;
    }

    public List<OnlineStore> findStoresWithMinPrice(String productName) {
        double minPrice = findMinPrice(productName);
        List<OnlineStore> resultStores = new ArrayList<>();

        if (minPrice == -1) return resultStores;

        for (OnlineStore store : stores) {
            for (StoreItem item : store.getItems()) {
                if (item.getProduct().getName().equals(productName) && item.getPrice() == minPrice) {
                    resultStores.add(store);
                    break;
                }
            }
        }
        return resultStores;
    }

    public OnlineStore findCheapStore() {
        for (OnlineStore store : stores) {
            boolean allCheaper = true;
            if (store.getItems().isEmpty()) allCheaper = false;

            for (StoreItem item : store.getItems()) {
                if (item.getPrice() >= item.getProduct().getRecommendedPrice()) {
                    allCheaper = false;
                    break;
                }
            }
            if (allCheaper) return store;
        }
        return null;
    }

    // --- Новий функціонал для Лаб 12 (Map) ---
    /**
     * Розраховує середню ціну на кожен товар серед усіх магазинів.
     * Використовує Map: Ключ - назва товару, Значення - середня ціна.
     */
    public Map<String, Double> calculateAveragePrices() {
        Map<String, List<Double>> priceMap = new HashMap<>();

        // Збір усіх цін
        for (OnlineStore store : stores) {
            for (StoreItem item : store.getItems()) {
                String pName = item.getProduct().getName();
                priceMap.putIfAbsent(pName, new ArrayList<>());
                priceMap.get(pName).add(item.getPrice());
            }
        }

        // Розрахунок середнього
        Map<String, Double> averagePrices = new HashMap<>();
        for (Map.Entry<String, List<Double>> entry : priceMap.entrySet()) {
            double sum = 0;
            for (Double price : entry.getValue()) {
                sum += price;
            }
            averagePrices.put(entry.getKey(), sum / entry.getValue().size());
        }

        return averagePrices;
    }

    public static void main(String[] args) {
        Product p1 = new Product("Laptop", 1000.0);
        Product p2 = new Product("Phone", 500.0);
        Product p3 = new Product("Mouse", 50.0);

        OnlineStore s1 = new OnlineStore("Rozetka");
        s1.addItem(p1, 950.0);
        s1.addItem(p2, 480.0);

        OnlineStore s2 = new OnlineStore("Comfy");
        s2.addItem(p1, 920.0);
        s2.addItem(p2, 510.0);

        // Перевірка роботи Set (дублікат магазину не має додатись, якщо реалізовано hashCode/equals)
        OnlineStore s3 = new OnlineStore("Rozetka");
        s3.addItem(p1, 9999.0); // Цей магазин не повинен вплинути, бо він дублікат по імені "Rozetka"

        ProductSearchService service = new ProductSearchService();
        service.addStore(s1);
        service.addStore(s2);
        service.addStore(s3); // Спроба додати дублікат

        System.out.println("Total stores: " + service.stores.size()); // Має бути 2, а не 3

        System.out.println("\n--- Average Prices (Map Functionality) ---");
        Map<String, Double> averages = service.calculateAveragePrices();
        for (Map.Entry<String, Double> entry : averages.entrySet()) {
            System.out.printf("Product: %s, Avg Price: %.2f%n", entry.getKey(), entry.getValue());
        }
    }
}