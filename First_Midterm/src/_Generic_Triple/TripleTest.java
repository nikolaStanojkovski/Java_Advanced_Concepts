package _Generic_Triple;

import java.util.*;

class Triple<T extends Number> {
    private T a, b, c;
    private List<T> sorted;

    public Triple(T a, T b, T c) {
        this.a = a;
        this.b = b;
        this.c = c;
        sorted = Arrays.asList(a, b, c);
    }

    public double max() {
        return sorted.stream().mapToDouble(i -> i.doubleValue()).max().getAsDouble();
    }

    public double avarage() {
        return sorted.stream().mapToDouble(i -> i.doubleValue()).sum() /
                sorted.stream().mapToDouble(i -> i.doubleValue()).count();
    }

    public void sort() {
        sorted.sort(Comparator.comparing(i -> i.doubleValue()));
    }

    @Override
    public String toString() {
        return String.format("%.2f %.2f %.2f", sorted.get(0).doubleValue(),
                sorted.get(1).doubleValue(),
                sorted.get(2).doubleValue());
    }
}

public class TripleTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();
        Triple<Integer> tInt = new Triple<Integer>(a, b, c);
        System.out.printf("%.2f\n", tInt.max());
        System.out.printf("%.2f\n", tInt.avarage());
        tInt.sort();
        System.out.println(tInt);
        float fa = scanner.nextFloat();
        float fb = scanner.nextFloat();
        float fc = scanner.nextFloat();
        Triple<Float> tFloat = new Triple<Float>(fa, fb, fc);
        System.out.printf("%.2f\n", tFloat.max());
        System.out.printf("%.2f\n", tFloat.avarage());
        tFloat.sort();
        System.out.println(tFloat);
        double da = scanner.nextDouble();
        double db = scanner.nextDouble();
        double dc = scanner.nextDouble();
        Triple<Double> tDouble = new Triple<Double>(da, db, dc);
        System.out.printf("%.2f\n", tDouble.max());
        System.out.printf("%.2f\n", tDouble.avarage());
        tDouble.sort();
        System.out.println(tDouble);
    }
}