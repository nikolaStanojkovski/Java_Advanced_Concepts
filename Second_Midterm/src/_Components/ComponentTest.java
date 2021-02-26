package _Components;

import java.util.*;

class InvalidPositionException extends Exception {
    public InvalidPositionException(int position) {
        super(String.format("Invalid position %d, alredy taken!", position));
    }
}

class Component implements Comparable<Component> {

    private String color;
    private Integer weight;
    private Set<Component> components;

    public Component(String color, Integer weight) {
        this.color = color;
        this.weight = weight;
        this.components = new TreeSet<>();
    }

    public String getColor() {
        return color;
    }

    public Integer getWeight() {
        return weight;
    }

    @Override
    public int compareTo(Component o) {
        int res = Float.compare(this.getWeight(), o.getWeight());
        if(res == 0)
            return this.getColor().compareTo(o.getColor());
        else
            return res;
    }

    public Set<Component> getComponents() {
        return components;
    }

    public void addComponent(Component component) {
        this.components.add(component);
    }

    public String print() {
        return String.format("%d:%s\n", getWeight(), getColor());
    }

    public String printComponents(String tab, Component component) {
        StringBuilder sb = new StringBuilder();

        for(Component c : component.getComponents()) {
            sb.append(tab + c.print());
            if(!c.getComponents().isEmpty())
                sb.append(printComponents(tab + "---", c));
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return this.print() + printComponents("---", this);
    }

    public void makeComparisonColor(int weight, String color, Component i) {
        if(i.getWeight() < weight)
            i.setColor(color);
    }

    public void setColorToComponents(int weight, String color, Component i) {
        makeComparisonColor(weight, color, i);

        for(Component c : i.getComponents()) {
            makeComparisonColor(weight, color, c);
            if(!c.getComponents().isEmpty())
                setColorToComponents(weight,color,c);
        }
    }

    private void setColor(String color) {
        this.color = color;
    }
}

class Window {
    private String name;
    private Map<Integer, Component> components;

    public Window(String name) {
        this.name = name;
        this.components = new TreeMap<>();
    }

    public void addComponent(int position, Component component) throws InvalidPositionException {
        if(components.containsKey(position))
            throw new InvalidPositionException(position);
        else
            components.put(position, component);
    }

    public void changeColor(int weight, String color) {
        this.components.forEach((key, value) ->
                value.setColorToComponents(weight, color, value));
    }

    public void swichComponents(int pos1, int pos2) {
        Component removed1 = this.components.remove(pos1);
        Component removed2 = this.components.remove(pos2);

        this.components.put(pos1, removed2);
        this.components.put(pos2, removed1);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("WINDOW " + this.getName() + "\n");
        for(Map.Entry entry : this.components.entrySet())
            sb.append(String.format("%d:%s", entry.getKey(),
                    entry.getValue()));
        return sb.toString();
    }
}

public class ComponentTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        Window window = new Window(name);
        Component prev = null;
        while (true) {
            try {
                int what = scanner.nextInt();
                scanner.nextLine();
                if (what == 0) {
                    int position = scanner.nextInt();
                    window.addComponent(position, prev);
                } else if (what == 1) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev = component;
                } else if (what == 2) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev.addComponent(component);
                    prev = component;
                } else if (what == 3) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev.addComponent(component);
                } else if(what == 4) {
                    break;
                }

            } catch (InvalidPositionException e) {
                System.out.println(e.getMessage());
            }
            scanner.nextLine();
        }

        System.out.println("=== ORIGINAL WINDOW ===");
        System.out.println(window);
        int weight = scanner.nextInt();
        scanner.nextLine();
        String color = scanner.nextLine();
        window.changeColor(weight, color);
        System.out.println(String.format("=== CHANGED COLOR (%d, %s) ===", weight, color));
        System.out.println(window);
        int pos1 = scanner.nextInt();
        int pos2 = scanner.nextInt();
        System.out.println(String.format("=== SWITCHED COMPONENTS %d <-> %d ===", pos1, pos2));
        window.swichComponents(pos1, pos2);
        System.out.println(window);
    }
}
