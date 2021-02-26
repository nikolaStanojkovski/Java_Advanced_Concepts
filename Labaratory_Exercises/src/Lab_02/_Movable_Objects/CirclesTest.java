package Lab_02._Movable_Objects;

import java.util.*;

enum TYPE {
    POINT,
    CIRCLE
}

enum DIRECTION {
    UP,
    DOWN,
    LEFT,
    RIGHT
}

class ObjectCanNotBeMovedException extends RuntimeException {

    public ObjectCanNotBeMovedException(int x, int y) {
        super("Point (" + x + "," + y + ") is out of bounds");
    }

}

class MovableObjectNotFittableException extends RuntimeException {

    public MovableObjectNotFittableException(MovableCircle circle) {
        super("Movable circle with center (" + circle.getCurrentXPosition() + "," + circle.getCurrentYPosition() +
                ") and radius " + circle.getRadius() + " can not be fitted into the collection");
    }

}

interface Movable {
    void moveUp() throws ObjectCanNotBeMovedException;
    void moveLeft() throws ObjectCanNotBeMovedException;
    void moveRight() throws ObjectCanNotBeMovedException;
    void moveDown() throws ObjectCanNotBeMovedException;
    int getCurrentXPosition();
    int getCurrentYPosition();
    int getXSpeed();
    int getYSpeed();
}

class MovablePoint implements Movable {

    private int x,y;
    private int xSpeed, ySpeed;

    public MovablePoint(int x, int y, int xSpeed, int ySpeed) {
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    @Override
    public String toString() {
        return "Movable point with coordinates (" +this.x+","+this.y+")" ;
    }

    public int getXSpeed() {
        return xSpeed;
    }

    public int getYSpeed() {
        return ySpeed;
    }

    @Override
    public void moveUp() {
        this.y+=ySpeed;
    }

    @Override
    public void moveLeft() {
        this.x-=xSpeed;
    }

    @Override
    public void moveRight() {
        this.x+=xSpeed;
    }

    @Override
    public void moveDown() {
        this.y-=ySpeed;
    }

    @Override
    public int getCurrentXPosition() {
        return this.x;
    }

    @Override
    public int getCurrentYPosition() {
        return this.y;
    }
}

class MovableCircle implements Movable {

    private int radius;
    private MovablePoint center;

    public MovableCircle(int radius, MovablePoint center) {
        this.radius = radius;
        this.center = center;
    }

    public int getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "Movable circle with center coordinates (" + center.getCurrentXPosition() +
                "," + center.getCurrentYPosition() + ") and radius " + radius;
    }

    @Override
    public void moveUp() {
        center.moveUp();
    }

    @Override
    public void moveLeft() {
        center.moveLeft();
    }

    @Override
    public void moveRight() {
        center.moveRight();
    }

    @Override
    public void moveDown() {
        center.moveDown();
    }
    @Override
    public int getCurrentXPosition() {
        return center.getCurrentXPosition();
    }

    @Override
    public int getCurrentYPosition() {
        return center.getCurrentYPosition();
    }

    @Override
    public int getXSpeed() {
        return center.getXSpeed();
    }

    @Override
    public int getYSpeed() {
        return center.getYSpeed();
    }
}

class MovablesCollection {
    private Movable[] movables;
    private static int x_MAX;
    private static int y_MAX;
    private int brojac;

    public static void setxMax(int max) {
        x_MAX = max;
    }

    public static void setyMax(int max) {
        y_MAX = max;
    }

    public MovablesCollection(int x_MAX, int y_MAX) {
        this.y_MAX = y_MAX;
        this.x_MAX = x_MAX;
        brojac = 0;
        movables = new Movable[brojac];
    }

    public void addMovableObject(Movable m) throws ObjectCanNotBeMovedException, MovableObjectNotFittableException {

        if(m instanceof MovableCircle)
        {
            if((m.getCurrentXPosition() + ((MovableCircle) m).getRadius() ) > x_MAX ||
                    (m.getCurrentYPosition() + ((MovableCircle) m).getRadius() ) > y_MAX ||
                    (m.getCurrentXPosition() - ((MovableCircle) m).getRadius() ) < 0 ||
                    (m.getCurrentYPosition() - ((MovableCircle) m).getRadius() ) < 0)
                throw new MovableObjectNotFittableException((MovableCircle)m);
        }

        brojac++;
        movables = Arrays.copyOf(movables, brojac);
        movables[brojac-1] = m;

    }

    public void checkOutOfBounds(Movable m, DIRECTION direction) {
        if(direction.toString().equals("LEFT") && (m.getCurrentXPosition() - m.getXSpeed()) < 0)
            throw new ObjectCanNotBeMovedException(m.getCurrentXPosition() - m.getXSpeed(), m.getCurrentYPosition());
        else if(direction.toString().equals("RIGHT") && (m.getCurrentXPosition()+m.getXSpeed()) > x_MAX)
            throw new ObjectCanNotBeMovedException(m.getCurrentXPosition() + m.getXSpeed(), m.getCurrentYPosition());
        else if(direction.toString().equals("DOWN") && (m.getCurrentYPosition() - m.getYSpeed()) < 0)
            throw new ObjectCanNotBeMovedException(m.getCurrentXPosition(), m.getCurrentYPosition()-m.getYSpeed());
        else if(direction.toString().equals("UP") && ( m.getYSpeed() + m.getCurrentYPosition()) > y_MAX)
            throw new ObjectCanNotBeMovedException(m.getCurrentXPosition(), m.getYSpeed() + m.getCurrentYPosition());
    }

    public void moveObject(Movable m, DIRECTION direction) throws ObjectCanNotBeMovedException {

        checkOutOfBounds(m, direction);

        if(direction.toString().equals("UP"))
            m.moveUp();
        else if(direction.toString().equals("DOWN"))
            m.moveDown();
        else if(direction.toString().equals("LEFT"))
            m.moveLeft();
        else if(direction.toString().equals("RIGHT"))
            m.moveRight();
    }

    public void moveObjectsFromTypeWithDirection(TYPE type, DIRECTION direction) throws ObjectCanNotBeMovedException {

        boolean flagPoint = type.toString().equals("POINT");

        for(Movable m : movables) {

            try {
                if (flagPoint && (m instanceof MovablePoint))
                    moveObject(m, direction);
                else if (!flagPoint && (m instanceof MovableCircle))
                    moveObject(m, direction);
            }
            catch (ObjectCanNotBeMovedException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder finalString = new StringBuilder("Collection of movable objects with size " + movables.length + ":\n");

        for(Movable m : movables)
            finalString.append(m.toString()).append("\n");

        return finalString.toString();
    }
}

public class CirclesTest {

    public static void main(String[] args) throws ObjectCanNotBeMovedException, MovableObjectNotFittableException {

        System.out.println("===COLLECTION CONSTRUCTOR AND ADD METHOD TEST===");
        MovablesCollection collection = new MovablesCollection(100, 100);
        Scanner sc = new Scanner(System.in);
        int samples = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < samples; i++) {
            String inputLine = sc.nextLine();
            String[] parts = inputLine.split(" ");

            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            int xSpeed = Integer.parseInt(parts[3]);
            int ySpeed = Integer.parseInt(parts[4]);

            try {
                if (Integer.parseInt(parts[0]) == 0) { //point
                    collection.addMovableObject(new MovablePoint(x, y, xSpeed, ySpeed));
                } else { //circle
                    int radius = Integer.parseInt(parts[5]);
                    collection.addMovableObject(new MovableCircle(radius, new MovablePoint(x, y, xSpeed, ySpeed)));
                }
            }
            catch (MovableObjectNotFittableException ex) {
                System.out.println(ex.getMessage());
            }

        }

        System.out.println(collection.toString());


        System.out.println("MOVE POINTS TO THE LEFT");
        collection.moveObjectsFromTypeWithDirection(TYPE.POINT, DIRECTION.LEFT);

        System.out.println(collection.toString());

        System.out.println("MOVE CIRCLES DOWN");
        collection.moveObjectsFromTypeWithDirection(TYPE.CIRCLE, DIRECTION.DOWN);


        System.out.println(collection.toString());

        System.out.println("CHANGE X_MAX AND Y_MAX");
        MovablesCollection.setxMax(90);
        MovablesCollection.setyMax(90);

        System.out.println("MOVE POINTS TO THE RIGHT");
        collection.moveObjectsFromTypeWithDirection(TYPE.POINT, DIRECTION.RIGHT);

        System.out.println(collection.toString());

        System.out.println("MOVE CIRCLES UP");
        collection.moveObjectsFromTypeWithDirection(TYPE.CIRCLE, DIRECTION.UP);

        System.out.println(collection.toString());

    }

}

