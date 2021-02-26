package _Fragments;

import java.util.Scanner;

class ZeroDenominatorException extends RuntimeException {
    public ZeroDenominatorException() {
        super("Denominator cannot be zero");
    }
}

class GenericFraction<T extends Number, U extends Number> {
    private T numerator;
    private U denominator;

    public GenericFraction(T numerator, U denominator) throws ZeroDenominatorException {
        if(denominator.intValue() == 0)
            throw new ZeroDenominatorException();
        
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public double toDouble() {
        return numerator.doubleValue() / denominator.doubleValue();
    }

    public GenericFraction<Double, Double> add(GenericFraction<? extends Number, ? extends Number> anotherDropka) {
        double nzs = getNZS(this.denominator.intValue(), anotherDropka.denominator.intValue());
        double first = getNumerator(nzs, this.denominator.doubleValue(), this.numerator.doubleValue());
        double second = getNumerator(nzs, anotherDropka.denominator.doubleValue(),
                anotherDropka.numerator.doubleValue());
        return checkDropka((first+second), nzs);
    }

    public GenericFraction<Double, Double> checkDropka(double num, double denom) {
        for(int i=2;i<denom;i++) {
            if(num%i == 0 && denom%i == 0) {
                num/=i;
                denom/=i;
                i=1;
            }
        }
        return new GenericFraction<>(num, denom);
    }

    public double getNumerator(double denom, double initialDenom, double numer) {
        return ( denom / initialDenom ) * numer;
    }

    public double getNZS(int first, int second) {
        int broj = 0;
        if(first > second)
            broj = first;
        else
            broj = second;
        while(true) {
            if(broj%first == 0 && broj%second == 0)
                return broj;
            broj++;
        }
    }

    @Override
    public String toString() {
        return String.format("%.2f / %.2f", numerator.doubleValue(), denominator.doubleValue());
    }
}

public class GenericFractionTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double n1 = scanner.nextDouble();
        double d1 = scanner.nextDouble();
        float n2 = scanner.nextFloat();
        float d2 = scanner.nextFloat();
        int n3 = scanner.nextInt();
        int d3 = scanner.nextInt();
        try {
            GenericFraction<Double, Double> gfDouble = new GenericFraction<Double, Double>(n1, d1);
            GenericFraction<Float, Float> gfFloat = new GenericFraction<Float, Float>(n2, d2);
            GenericFraction<Integer, Integer> gfInt = new GenericFraction<Integer, Integer>(n3, d3);
            System.out.printf("%.2f\n", gfDouble.toDouble());
            System.out.println(gfDouble.add(gfFloat));
            System.out.println(gfInt.add(gfFloat));
            System.out.println(gfDouble.add(gfInt));
            gfInt = new GenericFraction<Integer, Integer>(n3, 0);
        } catch(ZeroDenominatorException e) {
            System.out.println(e.getMessage());
        }

        scanner.close();
    }

}