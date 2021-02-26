package _Min_Max;

import java.util.Scanner;

class MinMax<T extends Comparable<T>> {

    private T min, max;
    private int numberElements, numberMax, numberMin;

    public MinMax() {
        min = null;
        max = null;
        numberElements = 0;
        numberMax = 0;
        numberMin = 0;
    }

    public void update(T element) {
        if(numberElements == 0) {
            max = element;
            min = element;
        }
        numberElements++;

        if(element.compareTo(min) < 0) {
            min = element;
            numberMin = 1;
        } else {
            if(element.compareTo(min) == 0) {
                numberMin++;
            }
        }

        if(element.compareTo(max) > 0) {
            max = element;
            numberMax = 1;
        } else {
            if(element.compareTo(max) == 0) {
                numberMax++;
            }
        }
    }

    public T max() {
        return max;
    }

    public T min() {
        return min;
    }

    @Override
    public String toString() {
        return String.format("%s %s %d\n", min().toString(), max().toString(), (numberElements-numberMax-numberMin));
    }
}

public class MinAndMax {
    public static void main(String[] args) throws ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        MinMax<String> strings = new MinMax<>();
        for(int i = 0; i < n; ++i) {
            String s = scanner.next();
            strings.update(s);
        }
        System.out.println(strings);
        MinMax<Integer> ints = new MinMax<>();
        for(int i = 0; i < n; ++i) {
            int x = scanner.nextInt();
            ints.update(x);
        }
        System.out.println(ints);
    }
}
