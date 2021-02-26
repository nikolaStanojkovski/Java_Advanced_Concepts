package _Blokovska_Struktura;

import java.util.*;
import java.util.stream.Collectors;

class Block<T> implements Comparable<T> {

    private Set<T> elements;
    private int size;

    public Block(int n) {
        this.elements = new TreeSet<>();
        this.size = n;
    }

    public Set<T> getElements() {
        return elements;
    }

    public boolean addElement(T e) {
        if(this.size == this.elements.size())
            return false;
        return this.elements.add(e);
    }

    public boolean removeElement(T e) {
        return this.elements.remove(e);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Block)) return false;
        Block<?> block = (Block<?>) o;
        return Objects.equals(elements, block.elements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(elements);
    }

    @Override
    public String toString() {
        return elements.toString();
    }

    @Override
    public int compareTo(T o) {
        return Integer.compare(this.hashCode(), o.hashCode());
    }

    public int getSize() {
        return this.elements.size();
    }

    public int emptyOut() {
        int length = this.elements.size();
        this.elements.clear();
        return length;
    }
}

class BlockContainer<T> {
    private List<Block<T>> blocks;
    private Block<T> current;
    private int size;

    public BlockContainer(int n) {
        blocks = new ArrayList<>();
        this.size = n;
        current = new Block<>(this.size);
        this.blocks.add(this.current);
    }

    public void add(T a) {
        boolean ifNew = this.current.addElement(a);
        if(!ifNew) {
            this.current = new Block<>(this.size);
            this.current.addElement(a);
            this.blocks.add(this.current);
        }
    }

    public boolean remove(T a) {
        boolean ifDeleted = this.current.removeElement(a);
        if(this.current.getSize() == 0)
            this.blocks.remove(current);
        return ifDeleted;
    }

    public List<T> getAsList() {
        return this.blocks.stream().flatMap(i -> i.getElements().stream()).collect(Collectors.toList());
    }

    public void recharge(List<T> lista) {
        int brojac = 0;
        for(Block b : this.blocks) {
            int size = b.emptyOut();
            for(int i = 0; i<size;i++) {
                b.addElement(lista.get(brojac++));
            }
        }
    }

    public void sort() {
        List<T> lista = getAsList().stream().sorted().collect(Collectors.toList());
        recharge(lista);
    }

    @Override
    public String toString() {
        return this.blocks.stream().map(Block::toString).
                collect(Collectors.joining(","));
    }
}

public class BlockContainerTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int size = scanner.nextInt();
        BlockContainer<Integer> integerBC = new BlockContainer<Integer>(size);
        scanner.nextLine();
        Integer lastInteger = null;
        for(int i = 0; i < n; ++i) {
            int element = scanner.nextInt();
            lastInteger = element;
            integerBC.add(element);
        }
        System.out.println("+++++ Integer Block Container +++++");
        System.out.println(integerBC);
        System.out.println("+++++ Removing element +++++");
        integerBC.remove(lastInteger);
        System.out.println("+++++ Sorting container +++++");
        integerBC.sort();
        System.out.println(integerBC);
        BlockContainer<String> stringBC = new BlockContainer<String>(size);
        String lastString = null;
        for(int i = 0; i < n; ++i) {
            String element = scanner.next();
            lastString = element;
            stringBC.add(element);
        }
        System.out.println("+++++ String Block Container +++++");
        System.out.println(stringBC);
        System.out.println("+++++ Removing element +++++");
        stringBC.remove(lastString);
        System.out.println("+++++ Sorting container +++++");
        stringBC.sort();
        System.out.println(stringBC);
    }
}