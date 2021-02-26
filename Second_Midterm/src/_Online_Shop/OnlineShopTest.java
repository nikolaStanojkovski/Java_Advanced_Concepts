package _Online_Shop;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

enum COMPARATOR_TYPE {
    NEWEST_FIRST,
    OLDEST_FIRST,
    LOWEST_PRICE_FIRST,
    HIGHEST_PRICE_FIRST,
    MOST_SOLD_FIRST,
    LEAST_SOLD_FIRST
}

class ProductNotFoundException extends Exception {
    ProductNotFoundException(String message) {
        super(message);
    }
}


class Product {
    private String category, id, name;
    private LocalDateTime createdAt;
    private double price;
    private Integer quantity;

    public Product(String category, String id, String name, LocalDateTime createdAt, double price) {
        this.category = category;
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.price = price;
        this.quantity = 0;
    }

    public void makeASale(Integer q) {
        this.quantity+=q;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", price=" + price +
                ", quantitySold=" + quantity + "}";
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }
}


class OnlineShop {

    private Map<String, Product> productsById;

    OnlineShop() {
        this.productsById = new HashMap<>();
    }

    void addProduct(String category, String id, String name, LocalDateTime createdAt, double price){
        this.productsById.put(id, new Product(category, id, name, createdAt, price));
    }

    double buyProduct(String id, int quantity) throws ProductNotFoundException {
        if(!productsById.containsKey(id))
            throw new ProductNotFoundException("Product with id " + id +
                    " does not exist in the online shop!");

        this.productsById.get(id).makeASale(quantity);
        return this.productsById.get(id).getPrice() * quantity;
    }

    public List<Product> filterProducts(String category) {
        if(category == null)
            return new ArrayList<>(this.productsById.values());

        return this.productsById.values().stream()
                .filter(i -> i.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    public List<Product> sortProducts(List<Product> products, COMPARATOR_TYPE comparatorType) {
        if(comparatorType == COMPARATOR_TYPE.HIGHEST_PRICE_FIRST) {
            return products.stream().sorted(Comparator.comparing(Product::getPrice)
                    .reversed()).collect(Collectors.toList());
        } else if(comparatorType == COMPARATOR_TYPE.LOWEST_PRICE_FIRST) {
            return products.stream().sorted(Comparator
                    .comparing(Product::getPrice))
                    .collect(Collectors.toList());
        } else if(comparatorType == COMPARATOR_TYPE.LEAST_SOLD_FIRST) {
            return products.stream().sorted(Comparator
                    .comparing(Product::getQuantity))
                    .collect(Collectors.toList());
        } else if(comparatorType == COMPARATOR_TYPE.MOST_SOLD_FIRST) {
            return products.stream().sorted(Comparator
                    .comparing(Product::getQuantity).reversed())
                    .collect(Collectors.toList());
        } else if(comparatorType == COMPARATOR_TYPE.NEWEST_FIRST) {
            return products.stream().sorted(Comparator
                    .comparing(Product::getCreatedAt).reversed())
                    .collect(Collectors.toList());
        } else {
            return products.stream().sorted(Comparator
                    .comparing(Product::getCreatedAt))
                    .collect(Collectors.toList());
        }
    }

    public List<List<Product>> paginateProducts(List<Product> products, int pageSize) {
        List<List<Product>> finalProducts = new ArrayList<>();
        List<Product> currentPage = new ArrayList<>();
        int brojac = 0;

        for (Product product : products) {
            if (brojac == pageSize) {
                finalProducts.add(currentPage);
                currentPage = new ArrayList<>();
                brojac = 0;
            }
            currentPage.add(product);
            brojac++;
        }

        if(!currentPage.isEmpty())
            finalProducts.add(currentPage);

        return finalProducts;
    }

    List<List<Product>> listProducts(String category, COMPARATOR_TYPE comparatorType, int pageSize) {
        List<Product> products = filterProducts(category);
        products = sortProducts(products, comparatorType);
        return paginateProducts(products, pageSize);
    }

}

public class OnlineShopTest {

    public static void main(String[] args) {
        OnlineShop onlineShop = new OnlineShop();
        double totalAmount = 0.0;
        Scanner sc = new Scanner(System.in);
        String line;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            String[] parts = line.split("\\s+");
            if (parts[0].equalsIgnoreCase("addproduct")) {
                String category = parts[1];
                String id = parts[2];
                String name = parts[3];
                LocalDateTime createdAt = LocalDateTime.parse(parts[4]);
                double price = Double.parseDouble(parts[5]);
                onlineShop.addProduct(category, id, name, createdAt, price);
            } else if (parts[0].equalsIgnoreCase("buyproduct")) {
                String id = parts[1];
                int quantity = Integer.parseInt(parts[2]);
                try {
                    totalAmount += onlineShop.buyProduct(id, quantity);
                } catch (ProductNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                String category = parts[1];
                if (category.equalsIgnoreCase("null"))
                    category=null;
                String comparatorString = parts[2];
                int pageSize = Integer.parseInt(parts[3]);
                COMPARATOR_TYPE comparatorType = COMPARATOR_TYPE.valueOf(comparatorString);
                printPages(onlineShop.listProducts(category, comparatorType, pageSize));
            }
        }
        System.out.println("Total revenue of the online shop is: " + totalAmount);

    }

    private static void printPages(List<List<Product>> listProducts) {
        for (int i = 0; i < listProducts.size(); i++) {
            System.out.println("PAGE " + (i + 1));
            listProducts.get(i).forEach(System.out::println);
        }
    }
}
