package Lab_05._Integer_List;

import java.util.*;
import java.util.stream.Collectors;

class IntegerList {

    private List<Integer> list;

    public IntegerList() {
        list = new LinkedList<>();
    }

    public IntegerList(Integer... numbers) {
        list = new LinkedList<>();
        this.list.addAll(Arrays.asList(numbers));
    }

    public void add(int element, int index) {

        if(index < list.size())
            list.add(index, element);
        else {
            int initialSize = list.size();
            for(int i = initialSize; i<index; i++)
                list.add(0);
            list.add(element);
        }

    }

    public int remove(int index) {
        return list.remove(index);
    }

    public void set(int element, int index) {
        list.set(index, element);
    }

    public int get(int index) {
        return list.get(index);
    }

    public int size() {
        return list.size();
    }

    public int count(int element) {
        return (int) list.stream().filter(i -> i == element).count();
    }

    public void removeDuplicates() {
        Collections.reverse(list);
        this.list = this.list.stream().distinct().collect(Collectors.toList());
        Collections.reverse(list);
    }

    public int sumFirst(int k) {
        return this.list.stream().limit(k).mapToInt(Integer::intValue).sum();
    }

    public int sumLast(int k) {
        int sum = 0;
        for(int i=list.size()-1; i>=list.size()-k;i--)
            sum += list.get(i);
        return sum;
    }

    public void shiftRight(int index, int k) {
        int element = list.remove(index);
        this.list.add((index+k)%(list.size()+1), element);
    }

    public void shiftLeft(int index, int k) {
        int element = list.remove(index);
        if(index-k < 0) {
            k = list.size() - Math.abs(index-k)%list.size();
            this.list.add((index+k-1)%list.size(), element);
        } else {
            this.list.add((index-k)%list.size(), element);
        }
    }

    public IntegerList addValue(int value) {
        IntegerList newList = new IntegerList();
        for(int i =0; i<list.size();i++)
            newList.add(list.get(i) + value, i);

        return newList;
    }

}

public class IntegerListTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if ( k == 0 ) { //test standard methods
            int subtest = jin.nextInt();
            if ( subtest == 0 ) {
                IntegerList list = new IntegerList();
                while ( true ) {
                    int num = jin.nextInt();
                    if ( num == 0 ) {
                        list.add(jin.nextInt(), jin.nextInt());
                    }
                    if ( num == 1 ) {
                        list.remove(jin.nextInt());
                    }
                    if ( num == 2 ) {
                        print(list);
                    }
                    if ( num == 3 ) {
                        break;
                    }
                }
            }
            if ( subtest == 1 ) {
                int n = jin.nextInt();
                Integer a[] = new Integer[n];
                for ( int i = 0 ; i < n ; ++i ) {
                    a[i] = jin.nextInt();
                }
                IntegerList list = new IntegerList(a);
                print(list);
            }
        }
        if ( k == 1 ) { //test count,remove duplicates, addValue
            int n = jin.nextInt();
            Integer a[] = new Integer[n];
            for ( int i = 0 ; i < n ; ++i ) {
                a[i] = jin.nextInt();
            }
            IntegerList list = new IntegerList(a);
            while ( true ) {
                int num = jin.nextInt();
                if ( num == 0 ) { //count
                    System.out.println(list.count(jin.nextInt()));
                }
                if ( num == 1 ) {
                    list.removeDuplicates();
                }
                if ( num == 2 ) {
                    print(list.addValue(jin.nextInt()));
                }
                if ( num == 3 ) {
                    list.add(jin.nextInt(), jin.nextInt());
                }
                if ( num == 4 ) {
                    print(list);
                }
                if ( num == 5 ) {
                    break;
                }
            }
        }
        if ( k == 2 ) { //test shiftRight, shiftLeft, sumFirst , sumLast
            int n = jin.nextInt();
            Integer a[] = new Integer[n];
            for ( int i = 0 ; i < n ; ++i ) {
                a[i] = jin.nextInt();
            }
            IntegerList list = new IntegerList(a);
            while ( true ) {
                int num = jin.nextInt();
                if ( num == 0 ) { //count
                    list.shiftLeft(jin.nextInt(), jin.nextInt());
                }
                if ( num == 1 ) {
                    list.shiftRight(jin.nextInt(), jin.nextInt());
                }
                if ( num == 2 ) {
                    System.out.println(list.sumFirst(jin.nextInt()));
                }
                if ( num == 3 ) {
                    System.out.println(list.sumLast(jin.nextInt()));
                }
                if ( num == 4 ) {
                    print(list);
                }
                if ( num == 5 ) {
                    break;
                }
            }
        }
    }

    public static void print(IntegerList il) {
        if ( il.size() == 0 ) System.out.print("EMPTY");
        for ( int i = 0 ; i < il.size() ; ++i ) {
            if ( i > 0 ) System.out.print(" ");
            System.out.print(il.get(i));
        }
        System.out.println();
    }

}