package Lab_01._Integer_Array;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

final class IntegerArray{

    private final List<Integer> niza;

    public IntegerArray(int a[])
    {
        List<Integer> temp = new ArrayList<>();
        for(int i : a)
        {
            temp.add(i);
        }
        niza = Collections.unmodifiableList(temp);
    }

    public int length() {
        return niza.size();
    }

    public int getElementAt(int i)
    {
        return niza.get(i);
    }

    public boolean equals(IntegerArray toCompare) {
        if(toCompare.length() != niza.size())
            return false;
        for(int i=0;i<toCompare.length();i++)
        {
            if(niza.get(i) != toCompare.getElementAt(i))
                return false;
        }
        return true;
    }

    public int sum() {
        int suma=0;
        for(int i : niza)
        {
            suma+=i;
        }
        return suma;
    }

    public double average()
    {
        return (double)sum()/length();
    }

    public IntegerArray getSorted() { // RASTECHKI REDOSLED

        List<Integer> tempNiza = new ArrayList<>(niza);
        for(int i=0;i<tempNiza.size()-1;i++)
        {
            for(int j=0;j<tempNiza.size()-i-1;j++)
            {
                if(tempNiza.get(j) > tempNiza.get(j+1))
                {
                    int temp = tempNiza.get(j);
                    tempNiza.set(j, tempNiza.get(j+1));
                    tempNiza.set(j+1, temp);
                }
            }
        }
        int a[] = new int[tempNiza.size()];
        for(int i=0;i<tempNiza.size();i++)
        {
            a[i] = tempNiza.get(i);
        }
        return new IntegerArray(a);
    }

    public IntegerArray concat(IntegerArray ia)
    {
        int number = niza.size() + ia.length();
        int[] a = new int[number];
        for(int i=0;i<niza.size();i++)
            a[i] = niza.get(i);

        int j=0;
        for(int i=niza.size();i<(niza.size()+ia.length());i++)
        {
            a[i] = ia.getElementAt(j);
            j++;
        }
        return new IntegerArray(a);
    }

    @Override
    public String toString() {
        StringBuilder finalString = new StringBuilder("[");
        for(int i : niza)
        {
            finalString.append(i).append(", ");
        }
        finalString = new StringBuilder(finalString.substring(0, finalString.length() - 2));
        finalString.append("]");
        return finalString.toString();
    }
}

class ArrayReader {
    public static IntegerArray readIntegerArray(InputStream input) {
        Scanner in = new Scanner(input);
        int n = in.nextInt();
        int[] a = new int[n];
        for(int i=0;i<n;i++)
        {
            a[i] = in.nextInt();
        }
        return new IntegerArray(a);
    }
}


public class IntegerArrayTester {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        IntegerArray ia = null;
        switch (s) {
            case "testSimpleMethods":
                ia = new IntegerArray(generateRandomArray(scanner.nextInt()));
                testSimpleMethods(ia);
                break;
            case "testConcat":
                testConcat(scanner);
                break;
            case "testEquals":
                testEquals(scanner);
                break;
            case "testSorting":
                testSorting(scanner);
                break;
            case "testReading":
                testReading(new ByteArrayInputStream(scanner.nextLine().getBytes()));
                break;
            case "testImmutability":
                int a[] = generateRandomArray(scanner.nextInt());
                ia = new IntegerArray(a);
                testSimpleMethods(ia);
                testSimpleMethods(ia);
                IntegerArray sorted_ia = ia.getSorted();
                testSimpleMethods(ia);
                testSimpleMethods(sorted_ia);
                sorted_ia.getSorted();
                testSimpleMethods(sorted_ia);
                testSimpleMethods(ia);
                a[0] += 2;
                testSimpleMethods(ia);
                ia = ArrayReader.readIntegerArray(new ByteArrayInputStream(integerArrayToString(ia).getBytes()));
                testSimpleMethods(ia);
                break;
        }
        scanner.close();
    }

    static void testReading(InputStream in) {
        IntegerArray read = ArrayReader.readIntegerArray(in);
        System.out.println(read);
    }

    static void testSorting(Scanner scanner) {
        int[] a = readArray(scanner);
        IntegerArray ia = new IntegerArray(a);
        System.out.println(ia.getSorted());
    }

    static void testEquals(Scanner scanner) {
        int[] a = readArray(scanner);
        int[] b = readArray(scanner);
        int[] c = readArray(scanner);
        IntegerArray ia = new IntegerArray(a);
        IntegerArray ib = new IntegerArray(b);
        IntegerArray ic = new IntegerArray(c);
        System.out.println(ia.equals(ib));
        System.out.println(ia.equals(ic));
        System.out.println(ib.equals(ic));
    }

    static void testConcat(Scanner scanner) {
        int[] a = readArray(scanner);
        int[] b = readArray(scanner);
        IntegerArray array1 = new IntegerArray(a);
        IntegerArray array2 = new IntegerArray(b);
        IntegerArray concatenated = array1.concat(array2);
        System.out.println(concatenated);
    }

    static void testSimpleMethods(IntegerArray ia) {
        System.out.print(integerArrayToString(ia));
        System.out.println(ia);
        System.out.println(ia.sum());
        System.out.printf("%.2f\n", ia.average());
    }


    static String integerArrayToString(IntegerArray ia) {
        StringBuilder sb = new StringBuilder();
        sb.append(ia.length()).append('\n');
        for (int i = 0; i < ia.length(); ++i)
            sb.append(ia.getElementAt(i)).append(' ');
        sb.append('\n');
        return sb.toString();
    }

    static int[] readArray(Scanner scanner) {
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; ++i) {
            a[i] = scanner.nextInt();
        }
        return a;
    }


    static int[] generateRandomArray(int k) {
        Random rnd = new Random(k);
        int n = rnd.nextInt(8) + 2;
        int a[] = new int[n];
        for (int i = 0; i < n; ++i) {
            a[i] = rnd.nextInt(20) - 5;
        }
        return a;
    }

}