package _Stacked_Canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

enum Color {
    RED, GREEN, BLUE
}

interface Scalable {
    void scale(float scaleFactor);
}

interface Stackable {
    float weight();
}

abstract class Shape implements Scalable, Stackable, Comparable<Shape> {
    private String id;
    private Color color;

    public Shape(String id, Color color) {
        this.id = id;
        this.color = color;
    }

    public String getColor() {
        return color.toString();
    }

    public String getId() {
        return id;
    }

    @Override
    public abstract void scale(float scaleFactor);

    @Override
    public abstract float weight();

    @Override
    public int compareTo(Shape o) {
        return Float.compare(this.weight(), o.weight());
    }
}

class Ball extends Shape {

    private float radius;

    public Ball(String id, Color color, float radius) {
        super(id, color);
        this.radius = radius;
    }

    @Override
    public void scale(float scaleFactor) {
        radius *= scaleFactor;
    }

    @Override
    public float weight() {
        return (float) (radius*radius*Math.PI);
    }

    @Override
    public String toString() {
        return String.format("C: %-5s%-10s%10.2f\n", getId(), getColor(),
                weight());
    }
}

class Rectangle extends Shape {

    private float width, height;

    public Rectangle(String id, Color color, float width, float height) {
        super(id, color);
        this.width = width;
        this.height = height;
    }

    @Override
    public void scale(float scaleFactor) {
        width *= scaleFactor;
        height *= scaleFactor;
    }

    @Override
    public float weight() {
        return width*height;
    }

    @Override
    public String toString() {
        return String.format("R: %-5s%-10s%10.2f\n", getId(), getColor(),
                weight());
    }
}

class Canvas {
    List<Shape> shapes;
    public Canvas() {
        shapes = new ArrayList<>();
    }

    public void updateCanvas(Shape shape) {
        if(shapes.size() == 0) {
            shapes.add(shape);
            return;
        }

        for(int i=0;i<shapes.size();i++) {
            if(shape.compareTo(shapes.get(i)) > 0) {
                shapes.add(i, shape);
                return;
            }
        }

        shapes.add(shape);
    }
    
    public void add(String id, Color color, float radius) {
        Ball newShape = new Ball(id, color, radius);
        updateCanvas(newShape);
    }

    public void add(String id, Color color, float width, float height) {
        Rectangle newShape = new Rectangle(id, color, width, height);
        updateCanvas(newShape);
    }


    public void scale(String id, float scaleFactor) {
        Shape theShape = shapes.stream().filter(i -> i.getId().equals(id)).findFirst().get();
        shapes.remove(theShape);
        theShape.scale(scaleFactor);
        updateCanvas(theShape);
    }

    @Override
    public String toString() {
        return shapes.stream().map(i -> i.toString()).collect(Collectors.joining());
    }
}

public class ShapesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Canvas canvas = new Canvas();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            int type = Integer.parseInt(parts[0]);
            String id = parts[1];
            if (type == 1) {
                Color color = Color.valueOf(parts[2]);
                float radius = Float.parseFloat(parts[3]);
                canvas.add(id, color, radius);
            } else if (type == 2) {
                Color color = Color.valueOf(parts[2]);
                float width = Float.parseFloat(parts[3]);
                float height = Float.parseFloat(parts[4]);
                canvas.add(id, color, width, height);
            } else if (type == 3) {
                float scaleFactor = Float.parseFloat(parts[2]);
                System.out.println("ORIGNAL:");
                System.out.print(canvas);
                canvas.scale(id, scaleFactor);
                System.out.printf("AFTER SCALING: %s %.2f\n", id, scaleFactor);
                System.out.print(canvas);
            }

        }
    }
}