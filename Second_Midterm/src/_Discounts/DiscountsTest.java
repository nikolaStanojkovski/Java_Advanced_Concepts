package _Discounts;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

class Price implements Comparable<Price> {
    private Long price, discount;

    public Price(Long price, Long discount) {
        this.price = price;
        this.discount = discount;
    }

    public Long getPrice() {
        return price;
    }

    public Long getPercentDiscount() {
        return (long) ( ( discount - price ) * 100.0 / (double) discount );
    }


    public Double getTotalDicount() {
        return (double) discount - price;
    }

    @Override
    public String toString() {
        return String.format("%2d%% %d/%d", this.getPercentDiscount()
            ,this.price, this.discount);
    }

    @Override
    public int compareTo(Price o) {
        int res = Long.compare(o.getPercentDiscount(), this.getPercentDiscount());
        if(res == 0) {
            return Long.compare(o.getPrice(), this.getPrice());
        } else
            return res;
    }
}

class Store {
    private String name;
    private Set<Price> prices;

    public Store(String name) {
        this.name = name;
        this.prices = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void addPrice(Long price, Long discount) {
        this.prices.add(new Price(price, discount));
    }

    public double getAverageDiscount() {
        return this.prices.stream().mapToDouble(Price::getPercentDiscount).average().getAsDouble();
    }

    public double getTotalDiscount() {
        return this.prices.stream().mapToDouble(Price::getTotalDicount).sum();
    }

    public static Store readStore(String line) {
        String[] parts = line.split("\\s+");
        Store newStore = new Store(parts[0]);
        for(int i=1;i<parts.length;i++) {
            String[] priceParts = parts[i].split(":");
            newStore.addPrice(Long.parseLong(priceParts[0]),
                    Long.parseLong(priceParts[1]));
        }
        return newStore;
    }

    public String getString() {
        return this.prices.stream().sorted().map(Price::toString).
                collect(Collectors.joining("\n"));
    }

    @Override
    public String toString() {
        return String.format("%s\nAverage discount: %.1f%%\nTotal discount: %d\n%s",
                this.getName(), this.getAverageDiscount(), (long)this.getTotalDiscount(), this.getString());
    }
}

class Discounts {

    private List<Store> stores;

    public Discounts() {
        this.stores = new ArrayList<>();
    }

    public int readStores(InputStream in) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        br.lines().forEach(i -> stores.add(Store.readStore(i)));
        return this.stores.size();
    }

    public List<Store> byAverageDiscount() {
        return this.stores.stream().sorted(Comparator.comparing(Store::getAverageDiscount)
                .reversed().thenComparing(Store::getName)).limit(3).collect(Collectors.toList());
    }

    public List<Store> byTotalDiscount() {
        return this.stores.stream().sorted(Comparator.comparing(Store::getTotalDiscount)
                .thenComparing(Store::getName)).limit(3).collect(Collectors.toList());
    }
}

public class DiscountsTest {
    public static void main(String[] args) {
        Discounts discounts = new Discounts();
        int stores = discounts.readStores(System.in);
        System.out.println("Stores read: " + stores);
        System.out.println("=== By average discount ===");
        discounts.byAverageDiscount().forEach(System.out::println);
        System.out.println("=== By total discount ===");
        discounts.byTotalDiscount().forEach(System.out::println);
    }
}
