package Lab_03._Pizza_Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

interface Item {
    int getPrice();
    void checkType(String type);
    String getType();
    void addIndex();
    int getIndex();
    void minusIndex();
}

class InvalidExtraTypeException extends RuntimeException {
    public InvalidExtraTypeException() {
        super("InvalidExtraTypeException");
    }
}

class InvalidPizzaTypeException extends RuntimeException {
    public InvalidPizzaTypeException() {
        super("InvalidPizzaTypeException");
    }
}

class ItemOutOfStockException extends RuntimeException {
    public ItemOutOfStockException() {
        super("ItemOutOfStockException");
    }
}

class ArrayIndexOutOfBоundsException extends RuntimeException {
    public ArrayIndexOutOfBоundsException(int index) {
        super("ArrayIndexOutOfBоundsException");
    }
}

class OrderLockedException extends RuntimeException {
    public OrderLockedException() {
        super("OrderLockedException");
    }
}

class EmptyOrder extends RuntimeException {
    public EmptyOrder() {
        super("EmptyOrder");
    }
}

class ExtraItem implements Item {

    private String type;
    private int price;
    private int index;

    public ExtraItem(String type) {
        try {
            checkType(type);
            this.type = type;
            this.price = getPrice();
            this.index = 0;
        }
        catch (InvalidExtraTypeException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public void minusIndex() {
        if(index != 0)
            this.index--;
    }

    @Override
    public void addIndex() {
        this.index+=1;
    }

    @Override
    public int getPrice() {
        if(type.equals("Ketchup"))
            return 3;
        else
            return 5;
    }

    @Override
    public void checkType(String type) {
        if(!type.equals("Coke") && !type.equals("Ketchup"))
            throw new InvalidExtraTypeException();
    }

    @Override
    public String getType() {
        return type;
    }
}

class PizzaItem implements Item {

    private String type;
    private int price;
    private int index;


    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public void minusIndex() {
        if(this.index != 0)
            this.index--;
    }

    public PizzaItem(String type) {
        try {
            checkType(type);
            this.type = type;
            this.price = getPrice();
            this.index = 0;
        }
        catch (InvalidPizzaTypeException ex) {
            System.out.println(ex.getMessage());
        }


    }

    @Override
    public void addIndex() {
        index++;
    }

    @Override
    public int getPrice() {
        if(type.equals("Standard"))
            return 10;
        else if(type.equals("Pepperoni"))
            return 12;

        return 8;

    }

    @Override
    public void checkType(String type) {
        if(!type.equals("Standard") && !type.equals("Pepperoni") &&
        !type.equals("Vegetarian"))
            throw new InvalidPizzaTypeException();
    }

    @Override
    public String getType() {
        return type;
    }
}

class Order {

    private List<Item> items;
    private boolean isLocked;

    public Order() {
        items = new ArrayList<>();
        isLocked = false;
    }

    public void addItem(Item item, int count) {
        if(!isLocked) {
            if(count > 10)
                throw new ItemOutOfStockException();

            boolean flag = false;
            for(Item i : items)
                if(item.getType().equals(i.getType()))
                    flag = true;

            for(int i=0;i<count;i++)
                item.addIndex();

            if(flag){
                for(Item i : items)
                    if(i.getType().equals(item.getType())) {
                        i.minusIndex();
                        break;
                    }
            }
            else
                items.add(item);

        }
        else
            throw new OrderLockedException();

    }

    public int getPrice() {
        int sum=0;
        for(Item item : items)
            sum+=(item.getPrice()*item.getIndex());
        return sum;
    }

    public void displayOrder() { // direktno pechati

        StringBuilder fullString = new StringBuilder();
        for(int i=0;i<items.size();i++) {
            fullString.append(String.format("%3s.%-15sx%2s%5s$\n", (i + 1), items.get(i).getType(), items.get(i).getIndex()
                    , (items.get(i).getIndex() * items.get(i).getPrice())));
        }
        fullString.append(String.format("Total:%21s$\n", getPrice()));
        System.out.print(fullString);
    }

    public void lock() {
        if(items.size() > 0)
            isLocked = true;
        else
            throw new EmptyOrder();
    }

    public void removeItem(int idx) {
        if(!isLocked) {
            if(items.size() < idx)
                throw new ArrayIndexOutOfBоundsException(idx);

            ArrayList<Item> tmp = new ArrayList<>();
            for (int i = 0; i < idx; i++)
                tmp.add(items.get(i));

            for(int i=idx; i<items.size()-1;i++)
                tmp.add(items.get(i+1));

            items = tmp;
        }
        else
            throw new OrderLockedException();
    }
}

public class PizzaOrderTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) { //test Item
            try {
                String type = jin.next();
                String name = jin.next();
                Item item = null;
                try {
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else
                        item = new ExtraItem(name);
                    System.out.println(item.getPrice());
                }catch (NullPointerException ex) {}

            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
        if (k == 1) { // test simple order
            Order order = new Order();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            jin.next();
            System.out.println(order.getPrice());
            order.displayOrder();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            System.out.println(order.getPrice());
            order.displayOrder();
        }
        if (k == 2) { // test order with removing
            Order order = new Order();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            jin.next();
            System.out.println(order.getPrice());
            order.displayOrder();
            while (jin.hasNextInt()) {
                try {
                    int idx = jin.nextInt();
                    order.removeItem(idx);
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            System.out.println(order.getPrice());
            order.displayOrder();
        }
        if (k == 3) { //test locking & exceptions
            Order order = new Order();
            try {
                order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.addItem(new ExtraItem("Coke"), 1);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.removeItem(0);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
    }

}
