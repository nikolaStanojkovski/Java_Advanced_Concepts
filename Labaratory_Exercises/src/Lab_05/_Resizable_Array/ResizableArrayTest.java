package Lab_05._Resizable_Array;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.LinkedList;

class ResizableArray<T> {

    private T[] elements;
    private int size;

    public ResizableArray() {
        this.size = 0;
        this.elements = (T[]) new Object[0];
    }

    public void addElement(T element) {
        if((size+1) > elements.length)
            elements = Arrays.copyOf(this.elements, size+1);
        this.elements[size++] = element;
    }

    public boolean removeElement(T element) {
        if(Arrays.stream(this.elements).noneMatch(i -> i.equals(element)))
            return false;

        for(int i=0; i<this.elements.length;i++) {
            if(this.elements[i].equals(element)) {
                this.elements[i] = this.elements[this.elements.length-1];
                break;
            }
        }

        this.elements = Arrays.copyOf(this.elements, this.elements.length-1);
        size--;
        return true;
    }

    public boolean contains(T element) {
        return Arrays.asList(this.elements).contains(element);
    }

    public Object[] toArray() {
        return this.elements;
    }

    public boolean isEmpty() {
        return this.elements.length == 0;
    }

    public int count() {
        return this.elements.length;
    }

    public T elementAt(int index) {
        if(index < 0 || index > elements.length)
            throw new ArrayIndexOutOfBoundsException(index);

        return this.elements[index];
    }

    public void clear() {
        this.elements = (T[]) new Object[0];
        size = 0;
    }

    public static<T> void copyAll(ResizableArray<? super T> dest, ResizableArray<? extends T> src) {
        ResizableArray<? super T> temp = new ResizableArray<>();

        for(int i=0;i<dest.toArray().length;i++)
            if(dest.elementAt(i) != null)
                temp.addElement((T) dest.elementAt(i));

        for(int i=0;i<src.toArray().length;i++)
            if(src.elementAt(i) != null)
                temp.addElement(src.elementAt(i));

        dest.clear();
        for(Object elem : temp.toArray())
            dest.addElement((T)elem);

    }

    public Object[] withoutDuplicates() {
        return Arrays.stream(this.elements).distinct().toArray();
    }

    public void setElements(T[] elems) {
        this.elements = Arrays.copyOf(elems, elems.length);
    }
}

class IntegerArray extends ResizableArray<Integer> {

    public IntegerArray() {
        super();
    }

    public double sum() {
        return Arrays.stream(toArray()).filter(Objects::nonNull).mapToDouble(i ->
                Double.parseDouble(i + "")).sum();
    }

    public double mean() {
        return Arrays.stream(toArray()).filter(Objects::nonNull).mapToDouble(i ->
                Double.parseDouble(i + "")).average().getAsDouble();
    }

    public IntegerArray distinct() {
        IntegerArray to_return = new IntegerArray();
        Object[] temp = super.withoutDuplicates();
        Arrays.stream(temp).filter(Objects::nonNull).
                forEach(i -> to_return.addElement(Integer.parseInt(i + "")));
        return to_return;
    }

    public int countNonZero() {
        return (int) Arrays.stream(toArray()).filter(i -> !i.equals(0)).count();
    }

    public IntegerArray increment(int offset) {
        IntegerArray to_return = new IntegerArray();
        Arrays.stream(super.toArray()).forEach(i -> to_return.addElement(Integer.parseInt(i + "") + offset));
        return to_return;
    }
}

public class ResizableArrayTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int test = jin.nextInt();
        if ( test == 0 ) { //test ResizableArray on ints
            ResizableArray<Integer> a = new ResizableArray<Integer>();
            System.out.println(a.count());
            int first = jin.nextInt();
            a.addElement(first);
            System.out.println(a.count());
            int last = first;
            while ( jin.hasNextInt() ) {
                last = jin.nextInt();
                a.addElement(last);
            }
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(a.removeElement(first));
            System.out.println(a.contains(first));
            System.out.println(a.count());
        }
        if ( test == 1 ) { //test ResizableArray on strings
            ResizableArray<String> a = new ResizableArray<String>();
            System.out.println(a.count());
            String first = jin.next();
            a.addElement(first);
            System.out.println(a.count());
            String last = first;
            for ( int i = 0 ; i < 4 ; ++i ) {
                last = jin.next();
                a.addElement(last);
            }
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(a.removeElement(first));
            System.out.println(a.contains(first));
            System.out.println(a.count());
            ResizableArray<String> b = new ResizableArray<String>();
            ResizableArray.copyAll(b, a);
            System.out.println(b.count());
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(b.contains(first));
            System.out.println(b.contains(last));
            ResizableArray.copyAll(b, a);
            System.out.println(b.count());
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(b.contains(first));
            System.out.println(b.contains(last));
            System.out.println(b.removeElement(first));
            System.out.println(b.contains(first));
            System.out.println(b.removeElement(first));
            System.out.println(b.contains(first));

            System.out.println(a.removeElement(first));
            ResizableArray.copyAll(b, a);
            System.out.println(b.count());
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(b.contains(first));
            System.out.println(b.contains(last));
        }
        if ( test == 2 ) { //test IntegerArray
            IntegerArray a = new IntegerArray();
            System.out.println(a.isEmpty());
            while ( jin.hasNextInt() ) {
                a.addElement(jin.nextInt());
            }
            jin.next();
            System.out.println(a.sum());
            System.out.println(a.mean());
            System.out.println(a.countNonZero());
            System.out.println(a.count());
            IntegerArray b = a.distinct();
            System.out.println(b.sum());
            IntegerArray c = a.increment(5);
            System.out.println(c.sum());
            if ( a.sum() > 100 )
                ResizableArray.copyAll(a, a);
            else
                ResizableArray.copyAll(a, b);
            System.out.println(a.sum());
            System.out.println(a.removeElement(jin.nextInt()));
            System.out.println(a.sum());
            System.out.println(a.removeElement(jin.nextInt()));
            System.out.println(a.sum());
            System.out.println(a.removeElement(jin.nextInt()));
            System.out.println(a.sum());
            System.out.println(a.contains(jin.nextInt()));
            System.out.println(a.contains(jin.nextInt()));
        }
        if ( test == 3 ) { //test insanely large arrays
            LinkedList<ResizableArray<Integer>> resizable_arrays = new LinkedList<ResizableArray<Integer>>();
            for ( int w = 0 ; w < 500 ; ++w ) {
                ResizableArray<Integer> a = new ResizableArray<Integer>();
                int k =  2000;
                int t =  1000;
                for ( int i = 0 ; i < k ; ++i ) {
                    a.addElement(i);
                }

                a.removeElement(0);
                for ( int i = 0 ; i < t ; ++i ) {
                    a.removeElement(k-i-1);
                }
                resizable_arrays.add(a);
            }
            System.out.println("You implementation finished in less then 3 seconds, well done!");
        }
    }

}
