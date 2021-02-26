package _Unique_Names;

import java.util.*;
import java.util.stream.Collectors;

class Name implements Comparable<Name>{
    private String name;
    private Integer noTimes;

    public Name(String name) {
        this.name = name;
        this.noTimes = 1;
    }

    public void increment() {
        this.noTimes++;
    }

    public String getName() {
        return name;
    }

    public Integer getNoTimes() {
        return noTimes;
    }

    public int getUniqueLetters() {
        StringBuilder finalString = new StringBuilder();
        boolean flag = false;
        char[] chars = this.getName().toCharArray();
        for(int i=0;i<chars.length;i++) {
            flag = false;
            for(int j=0;j<finalString.length();j++) {
                if(Character.toLowerCase(finalString.charAt(j))
                        == chars[i]) {
                    flag = true;
                    break;
                }
            }
            if(flag)
                continue;
            else
                finalString.append(chars[i]);
        }
        return finalString.length();
    }

    @Override
    public String toString() {
        return String.format("%s (%d) %d", this.getName(), this.noTimes, this.getUniqueLetters());
    }

    @Override
    public int compareTo(Name o) {
        return this.getName().compareTo(o.getName());
    }
}

class Names {
    private List<Name> names;

    public Names() {
        names = new ArrayList<>();
    }

    public void addName(String name) {

        if(names.stream().anyMatch(i -> i.getName().
                equals(name)))
            names.stream().filter(i -> i.getName().
                    equals(name)).findFirst().get().increment();
        else
            names.add(new Name(name));

    }

    public void printN(int n) {
        this.names.stream().filter(i -> i.getNoTimes() >= n).sorted()
                .forEach(System.out::println);
    }

    public String findName(int len, int index) {
        List<Name> filtered = this.names.stream().filter(i -> i.getName().length()
         < len).sorted().collect(Collectors.toList());
        return filtered.get(index % filtered.size()).getName();
    }
}

public class NamesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        Names names = new Names();
        for (int i = 0; i < n; ++i) {
            String name = scanner.nextLine();
            names.addName(name);
        }
        n = scanner.nextInt();
        System.out.printf("===== PRINT NAMES APPEARING AT LEAST %d TIMES =====\n", n);
        names.printN(n);
        System.out.println("===== FIND NAME =====");
        int len = scanner.nextInt();
        int index = scanner.nextInt();
        System.out.println(names.findName(len, index));
        scanner.close();

    }
}
