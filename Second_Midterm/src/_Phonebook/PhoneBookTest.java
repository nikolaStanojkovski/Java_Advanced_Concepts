package _Phonebook;

import java.util.*;

class DuplicateNumberException extends Exception {
    public DuplicateNumberException(String num) {
        super(String.format("Duplicate number: %s", num));
    }
}

class Contact implements Comparable<Contact> {
    private String name, number;

    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return String.format("%s %s", this.getName(), this.getNumber());
    }

    @Override
    public int compareTo(Contact o) {
        int res = this.getName().compareTo(o.getName());
        if(res == 0)
            return this.getNumber().compareTo(o.getNumber());
        else
            return res;
    }

    public static List<String> getSubnumbers(String number) {
        List<String> temp = new ArrayList<>();
        for(int i=0; i<number.length(); i++) {
            for(int j=i+3; j<=number.length(); j++) {
                StringBuilder t = new StringBuilder();
                for(int k=i;k<j;k++) {
                    t.append(number.charAt(k));
                }
                temp.add(t.toString());
            }
        }

        return temp;
    }
}

// Ashley 076522077

class PhoneBook {
    private Map<String, Set<Contact>> byName;
    private Map<String, Set<Contact>> bySubnumer;
    private Map<String, Contact> byNumber;

    public PhoneBook() {
        this.byName = new HashMap<>();
        this.byNumber = new HashMap<>();
        this.bySubnumer = new HashMap<>();
    }

    public void addContact(String name, String number) throws DuplicateNumberException {
        if(byNumber.containsKey(number))
            throw new DuplicateNumberException(number);

        Contact c = new Contact(name, number);

        byNumber.putIfAbsent(number, c);
        byName.putIfAbsent(name, new TreeSet<>());
        byName.get(name).add(c);

        List<String> subnumbers = Contact.getSubnumbers(number);
        subnumbers.forEach(i -> bySubnumer.putIfAbsent(i, new TreeSet<>()));
        subnumbers.forEach(i -> bySubnumer.get(i).add(c));
    }

    public void contactsByNumber(String part) {
        if(!this.bySubnumer.containsKey(part))
            System.out.println("NOT FOUND");
        else
            this.bySubnumer.get(part).forEach(System.out::println);
    }

    public void contactsByName(String part) {
        if(!this.byName.containsKey(part))
            System.out.println("NOT FOUND");
        else
            this.byName.get(part).forEach(System.out::println);
    }
}

public class PhoneBookTest {

    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            try {
                phoneBook.addContact(parts[0], parts[1]);
            } catch (DuplicateNumberException e) {
                System.out.println(e.getMessage());
            }
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println(line);
            String[] parts = line.split(":");
            if (parts[0].equals("NUM")) {
                phoneBook.contactsByNumber(parts[1]);
            } else {
                phoneBook.contactsByName(parts[1]);
            }
        }
    }

}