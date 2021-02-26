package Lab_03._Phonebook;

import java.io.*;
import java.util.*;

class InvalidNameException extends RuntimeException {

    public String name;

    public InvalidNameException(String name) {
        this.name = name;
    }
}

class InvalidNumberException extends RuntimeException {
    public InvalidNumberException() {
        super("InvalidNumberException");
    }
}

class MaximumSizeExceddedException extends RuntimeException {
    public MaximumSizeExceddedException() {
        super("MaximumSizeExceddedException");
    }
}

class InvalidFormatException extends RuntimeException {
    public InvalidFormatException() {
        super("InvalidFormatException");
    }
}

class Contact {
    private String name;
    private String[] phoneNumbers;

    public Contact() {
        this.name = "";
        phoneNumbers = null;
    }

    public void checkName(String toCompare) throws InvalidNameException {
        boolean flag = false;
        for(char c : toCompare.toCharArray()) {
            if(( (int)c < 97 && (int)c > 90) || (int)c > 122 ||
                    (int)c < 65) {
                flag = true;
                break;
            }
        }
        if(toCompare.length() < 4 || toCompare.length() > 10 || flag)
            throw new InvalidNameException(toCompare);
    }

    public void checkNumber(String phone) {
        String[] toCompare = { "070", "071",
        "072", "075", "076", "077", "078"};
        boolean flagOne = false, flagTwo = true;
        for(String s : toCompare) {
            if(phone.substring(0, 3).equals(s))
                flagOne = true;
            for(char ye : phone.toCharArray())
                if(!Character.isDigit(ye)) {
                    flagTwo=false; break;
                }
        }

        if(phone.length() != 9 || !flagOne || !flagTwo)
            throw new InvalidNumberException();
    }

    public void checkSizeNumber(String... phoneNumbers) {
        if(phoneNumbers.length > 5)
            throw new MaximumSizeExceddedException();
    }

    public Contact(String name, String... phoneNumbers) throws InvalidNameException, MaximumSizeExceddedException, InvalidNumberException{

            checkName(name);
            checkSizeNumber(phoneNumbers);
            this.name = name;
            this.phoneNumbers = new String[phoneNumbers.length];
            for (int i = 0; i < phoneNumbers.length; i++) {
                checkNumber(phoneNumbers[i]);
                this.phoneNumbers[i] = phoneNumbers[i];
            }

            LexigraphicSort();
    }

    public void LexigraphicSort() {
        for (int i = 0; i < phoneNumbers.length-1; i++)
            for (int j = 0; j < phoneNumbers.length-i-1; j++)
                if (phoneNumbers[j].compareTo(phoneNumbers[j+1]) > 0)
                {
                    String temp = phoneNumbers[j];
                    phoneNumbers[j] = phoneNumbers[j+1];
                    phoneNumbers[j+1] = temp;
                }
    }

    public String getName() {
        return name;
    }

    public String[] getNumbers() {
        return phoneNumbers;
    }

    public void addNumber(String number) {
        try {
            checkSizeNumber();
            checkNumber(number);
            phoneNumbers = Arrays.copyOf(phoneNumbers, phoneNumbers.length + 1);
            phoneNumbers[phoneNumbers.length-1] = number;

            LexigraphicSort();
        } catch (InvalidNumberException | MaximumSizeExceddedException ex) {
            ex.getMessage();
        }
    }

    @Override
    public String toString() {
        LexigraphicSort();

        StringBuilder fullString = new StringBuilder();
        fullString.append(name).append("\n").append(phoneNumbers.length).append("\n");
        for(String phone : phoneNumbers)
            fullString.append(phone).append("\n");

        return fullString.toString();
    }
}

class PhoneBook {
    private Contact[] contacts;
    private int size;

    public PhoneBook() {
        contacts = new Contact[0];
        size = 0;
    }

    public void checkUnique(String name) throws InvalidNameException {
        for(Contact c : contacts) {
            if(c.getName().equals(name))
                throw new InvalidNameException(name);
        }
    }

    public void addContact(Contact contact) throws InvalidNameException, MaximumSizeExceddedException {
        if ((size + 1) == 250)
            throw new MaximumSizeExceddedException();
        if(size != 0)
            checkUnique(contact.getName());
        contacts = Arrays.copyOf(contacts, size+1);
        contacts[size++] = contact;

        LexigraphicSort();
    }

    public void LexigraphicSort() {
        for (int i = 0; i < contacts.length-1; i++)
            for (int j = 0; j < contacts.length-i-1; j++)
                if (contacts[j].getName().compareTo(contacts[j+1].getName()) > 0)
                {
                    Contact temp = contacts[j];
                    contacts[j] = contacts[j+1];
                    contacts[j+1] = temp;
                }
    }

    public Contact getContactForName(String name) {
        for(Contact c : contacts) {
            if(c.getName().equals(name))
                return c;
        }
        return null;
    }

    public int numberOfContacts() {
        return contacts.length;
    }

    public Contact[] getContacts() {
        return contacts;
    }

    public boolean removeContact(String name) {
        Contact[] tmpList = new Contact[size-1];
        int brojac = 0;
        for(int i=0;i<size;i++) {
            if(!contacts[i].getName().equals(name)) {
                tmpList[brojac++] = contacts[i];
            }
        }
        if(size == brojac)
            return false;
        else {
            size--; contacts = tmpList;
            return true;
        }
    }

    public static String write(PhoneBook pb) {
        StringBuilder fulLString = new StringBuilder();
        for(Contact c : pb.contacts)
            fulLString.append(c.toString()).append("\n");

        return fulLString.toString();
    }

    @Override
    public String toString() {
        LexigraphicSort();

        return write(this);
    }

    public static boolean saveAsTextFile(PhoneBook book, String path) {
        try {
            File file = new File(path);
            if(!file.exists())
                file.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(write(book));
            return true;
        } catch (IOException ex) {
            ex.getMessage();
            return false;
        }
    }

    public static PhoneBook loadFromTextFile(String path) throws IOException {
        File file = new File(path);
        if(!file.exists())
            throw new IOException();
        BufferedReader br = new BufferedReader(new FileReader(file));
        PhoneBook phoneBook = new PhoneBook();
        try {
            String nowName = "", line = "";
            String[] nowContacts = new String[0];
            int noNumbers = 0, brojac = 0, innerBrojac = 0;
            while ((line = br.readLine()) != null) {
                if (brojac == 0) {
                    nowName = line;
                } else if (brojac == 1) {
                    noNumbers = Integer.parseInt(line);
                    nowContacts = new String[noNumbers];
                } else {
                    nowContacts[innerBrojac++] = line;
                }

                if (innerBrojac == noNumbers) {
                    Contact c = new Contact(nowName, nowContacts);
                    phoneBook.addContact(c);
                    nowContacts = new String[0];
                    nowName = "";
                    noNumbers = 0;
                    innerBrojac = 0;
                    brojac = 0;
                }
                brojac++;
            }
        } catch (InvalidFormatException | InvalidNameException ex) {
            ex.getMessage();
        }
        return phoneBook;
    }

    public Contact[] getContactsForNumber(String number_prefix) {
        Contact[] contactsToBack = new Contact[0];
        int brojac=0;
        for(Contact c : contacts) {
            for(String phones : c.getNumbers()) {
                if(phones.startsWith(number_prefix)) {
                    contactsToBack = Arrays.copyOf(contactsToBack, brojac+1);
                    contactsToBack[brojac++] = c;
                }
            }
        }
        return contactsToBack;
    }
}

public class PhonebookTester {

    public static void main(String[] args) throws Exception {
        Scanner jin = new Scanner(System.in);
        String line = jin.nextLine();
        switch( line ) {
            case "test_contact":
                testContact(jin);
                break;
            case "test_phonebook_exceptions":
                testPhonebookExceptions(jin);
                break;
            case "test_usage":
                testUsage(jin);
                break;
            case "test_File":
                testFile(jin);
        }
    }

    private static void testFile(Scanner jin) throws Exception {
        PhoneBook phonebook = new PhoneBook();
        while ( jin.hasNextLine() )
            phonebook.addContact(new Contact(jin.nextLine(),jin.nextLine().split("\\s++")));
        String text_file = "phonebook.txt";
        PhoneBook.saveAsTextFile(phonebook,text_file);
        PhoneBook pb = PhoneBook.loadFromTextFile(text_file);
        if ( ! pb.equals(phonebook) ) System.out.println("Your file saving and loading doesn't seem to work right");
        else System.out.println("Your file saving and loading works great. Good job!");
    }

    private static void testUsage(Scanner jin) throws Exception {
        PhoneBook phonebook = new PhoneBook();
        while ( jin.hasNextLine() ) {
            String command = jin.nextLine();
            switch ( command ) {
                case "add":
                    phonebook.addContact(new Contact(jin.nextLine(),jin.nextLine().split("\\s++")));
                    break;
                case "remove":
                    phonebook.removeContact(jin.nextLine());
                    break;
                case "print":
                    System.out.println(phonebook.numberOfContacts());
                    System.out.println(Arrays.toString(phonebook.getContacts()));
                    System.out.println(phonebook.toString());
                    break;
                case "get_name":
                    System.out.println(phonebook.getContactForName(jin.nextLine()));
                    break;
                case "get_number":
                    System.out.println(Arrays.toString(phonebook.getContactsForNumber(jin.nextLine())));
                    break;
            }
        }
    }

    private static void testPhonebookExceptions(Scanner jin) {
        PhoneBook phonebook = new PhoneBook();
        boolean exception_thrown = false;
        try {
            while ( jin.hasNextLine() ) {
                phonebook.addContact(new Contact(jin.nextLine()));
            }
        }
        catch ( InvalidNameException e ) {
            System.out.println(e.name);
            exception_thrown = true;
        }
        catch ( Exception e ) {}
        if ( ! exception_thrown ) System.out.println("Your addContact method doesn't throw InvalidNameException");
        /*
		exception_thrown = false;
		try {
		phonebook.addContact(new Contact(jin.nextLine()));
		} catch ( MaximumSizeExceddedException e ) {
			exception_thrown = true;
		}
		catch ( Exception e ) {}
		if ( ! exception_thrown ) System.out.println("Your addContact method doesn't throw MaximumSizeExcededException");
        */
    }

    private static void testContact(Scanner jin) throws Exception {
        boolean exception_thrown = true;
        String names_to_test[] = { "And\nrej","asd","AAAAAAAAAAAAAAAAAAAAAA","Ð�Ð½Ð´Ñ€ÐµÑ˜A123213","Andrej#","Andrej<3"};
        for ( String name : names_to_test ) {
            try {
                new Contact(name);
                exception_thrown = false;
            } catch (InvalidNameException e) {
                exception_thrown = true;
            }
            if ( ! exception_thrown ) System.out.println("Your Contact constructor doesn't throw an InvalidNameException");
        }
        String numbers_to_test[] = { "+071718028","number","078asdasdasd","070asdqwe","070a56798","07045678a","123456789","074456798","073456798","079456798" };
        for ( String number : numbers_to_test ) {
            try {
                new Contact("Andrej",number);
                exception_thrown = false;
            } catch (InvalidNumberException e) {
                exception_thrown = true;
            }
            if ( ! exception_thrown ) System.out.println("Your Contact constructor doesn't throw an InvalidNumberException");
        }
        String nums[] = new String[10];
        for ( int i = 0 ; i < nums.length ; ++i ) nums[i] = getRandomLegitNumber();
        try {
            new Contact("Andrej",nums);
            exception_thrown = false;
        } catch (MaximumSizeExceddedException e) {
            exception_thrown = true;
        }
        if ( ! exception_thrown ) System.out.println("Your Contact constructor doesn't throw a MaximumSizeExceddedException");
        Random rnd = new Random(5);
        Contact contact = new Contact("Andrej",getRandomLegitNumber(rnd),getRandomLegitNumber(rnd),getRandomLegitNumber(rnd));
        System.out.println(contact.getName());
        System.out.println(Arrays.toString(contact.getNumbers()));
        System.out.println(contact.toString());
        contact.addNumber(getRandomLegitNumber(rnd));
        System.out.println(Arrays.toString(contact.getNumbers()));
        System.out.println(contact.toString());
        contact.addNumber(getRandomLegitNumber(rnd));
        System.out.println(Arrays.toString(contact.getNumbers()));
        System.out.println(contact.toString());
    }

    static String[] legit_prefixes = {"070","071","072","075","076","077","078"};
    static Random rnd = new Random();

    private static String getRandomLegitNumber() {
        return getRandomLegitNumber(rnd);
    }

    private static String getRandomLegitNumber(Random rnd) {
        StringBuilder sb = new StringBuilder(legit_prefixes[rnd.nextInt(legit_prefixes.length)]);
        for ( int i = 3 ; i < 9 ; ++i )
            sb.append(rnd.nextInt(10));
        return sb.toString();
    }


}

