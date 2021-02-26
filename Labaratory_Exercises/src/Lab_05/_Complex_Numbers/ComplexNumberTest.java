package Lab_05._Complex_Numbers;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

class ComplexNumber<T extends Number, U extends Number>  implements Comparable<ComplexNumber> {
    T real_part;
    U imaginary_part;

    public ComplexNumber(T real, U imaginary) {
        this.real_part = real;
        this.imaginary_part = imaginary;
    }

    public T getReal() {
        return real_part;
    }

    public U getImaginary() {
        return imaginary_part;
    }

    public double modul() {
        return (Math.sqrt(Math.pow(this.real_part.doubleValue(), 2) +
                Math.pow(this.imaginary_part.doubleValue(), 2)));
    }

    @Override
    public int compareTo(ComplexNumber o) {
        return Double.compare(this.modul(), o.modul());
    }

    @Override
    public String toString() {
        String operator = "+";
        if(this.imaginary_part.doubleValue() < 0)
            operator = "-";
        return String.format("%.2f%s%.2fi", this.real_part.doubleValue(),
                operator, Math.abs(this.imaginary_part.doubleValue()));
    }
}

public class ComplexNumberTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if ( k == 0 ) { //test simple functions int
            int r = jin.nextInt();int i = jin.nextInt();
            ComplexNumber<Integer, Integer> c = new ComplexNumber<Integer, Integer>(r, i);
            System.out.println(c);
            System.out.println(c.getReal());
            System.out.println(c.getImaginary());
            System.out.println(c.modul());
        }
        if ( k == 1 ) { //test simple functions float
            float r = jin.nextFloat();
            float i = jin.nextFloat();
            ComplexNumber<Float, Float> c = new ComplexNumber<Float, Float>(r, i);
            System.out.println(c);
            System.out.println(c.getReal());
            System.out.println(c.getImaginary());
            System.out.println(c.modul());
        }
        if ( k == 2 ) { //compareTo int
            LinkedList<ComplexNumber<Integer,Integer>> complex = new LinkedList<ComplexNumber<Integer,Integer>>();
            while ( jin.hasNextInt() ) {
                int r = jin.nextInt(); int i = jin.nextInt();
                complex.add(new ComplexNumber<Integer, Integer>(r, i));
            }
            System.out.println(complex);
            Collections.sort(complex);
            System.out.println(complex);
        }
        if ( k == 3 ) { //compareTo double
            LinkedList<ComplexNumber<Double,Double>> complex = new LinkedList<ComplexNumber<Double,Double>>();
            while ( jin.hasNextDouble() ) {
                double r = jin.nextDouble(); double i = jin.nextDouble();
                complex.add(new ComplexNumber<Double, Double>(r, i));
            }
            System.out.println(complex);
            Collections.sort(complex);
            System.out.println(complex);
        }
        if ( k == 4 ) { //compareTo mixed
            LinkedList<ComplexNumber<Double,Integer>> complex = new LinkedList<ComplexNumber<Double,Integer>>();
            while ( jin.hasNextDouble() ) {
                double r = jin.nextDouble(); int i = jin.nextInt();
                complex.add(new ComplexNumber<Double, Integer>(r, i));
            }
            System.out.println(complex);
            Collections.sort(complex);
            System.out.println(complex);
        }
    }
}