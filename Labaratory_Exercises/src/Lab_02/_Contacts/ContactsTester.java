package Lab_02._Contacts;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Scanner;

abstract class Contact {

    private String date;

    public Contact(String date) {
        this.date = date;
    }

    public boolean newer(long daysThis, long daysContact) {
        if(daysContact < daysThis)
            return true;
        else
            return false;
    }

    public long daysCalculation(String[] parts) {
        return Integer.parseInt(parts[0])* 365L + Integer.parseInt(parts[1]) * 30L + Integer.parseInt(parts[2]);
    }

    public boolean isNewerThan(Contact c) {
        String[] partsThis = this.date.split("-");
        String[] partsContact = c.date.split("-");

        long daysThis = daysCalculation(partsThis);
        long daysContact = daysCalculation(partsContact);

        return newer(daysThis, daysContact);
    }

    public abstract String getType();

}

class EmailContact extends Contact {
    private String email;

    public EmailContact(String date, String email) {
        super(date);
        this.email = email;
    }

    @Override
    public String getType() {
        return "Email";
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "\"" + email + "\"";
    }
}

class PhoneContact extends Contact {
    private String phoneNumber;

    enum Operator {VIP, ONE, TMOBILE}

    public PhoneContact(String date, String phoneNumber) {
        super(date);
        this.phoneNumber = phoneNumber;
    }

    public Operator getOperator() {
        String[] parts = phoneNumber.split("/");

        if(parts[0].charAt(2) == '0' || parts[0].charAt(2) == '1' ||
                parts[0].charAt(2) == '2')
            return Operator.TMOBILE;
        else if(parts[0].charAt(2) == '5' || parts[0].charAt(2) == '6')
            return Operator.ONE;

        return Operator.VIP;

    }

    public String getPhone() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return "\"" + phoneNumber + "\"";
    }

    @Override
    public String getType() {
        return "Phone";
    }

}

class Student {
    private String firstName, lastName, city;
    private int age;
    private long index;
    private Contact[] contacts;

    private int brojac;

    public Student(String firstName, String lastName, String city, int age, long index) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.age = age;
        this.index = index;
        this.brojac = 0;
        contacts = new Contact[brojac];
    }

    public void addEmailContact(String date, String email) {
        brojac++;
        contacts = Arrays.copyOf(contacts, brojac);
        contacts[brojac-1] = new EmailContact(date, email);
    }

    public void addPhoneContact(String date, String phone) {
        brojac++;
        contacts = Arrays.copyOf(contacts, brojac);
        contacts[brojac-1] = new PhoneContact(date, phone);
    }

    public int getNumberContacts() {
        return contacts.length;
    }

    public Contact getLatestContact() {
        Contact newestContact = contacts[0];
        for (int i=1;i<contacts.length;i++)
        {
            if(contacts[i].isNewerThan(newestContact))
                newestContact = contacts[i];
        }
        return newestContact;
    }

    public String getCity() {
        return city;
    }

    public long getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return "{" +
                "\"ime\":\"" + firstName + '\"' +
                ", \"prezime\":\"" + lastName + '\"' +
                ", \"vozrast\":" + age +
                ", \"grad\":\"" + city + '\"' +
                ", \"indeks\":" + index +
                ", \"telefonskiKontakti\":" + Arrays.toString(getPhoneContacts()) +
                ", \"emailKontakti\":" + Arrays.toString(getEmailContacts()) +
                '}';
    }

    public Contact[] getPhoneContacts() {
        int number = 0;
        for(Contact c : contacts)
            if(c.getType().equals("Phone"))
                number++;

        Contact[] phoneContacts = new Contact[number];
        number=0;
        for(Contact c : contacts)
            if(c.getType().equals("Phone"))
                phoneContacts[number++] = c;

        return phoneContacts;
    }

    public Contact[] getEmailContacts() {
        int number = 0;
        for(Contact c : contacts)
            if(c.getType().equals("Email"))
                number++;

        Contact[] mailContacts = new Contact[number];
        number=0;
        for(Contact c : contacts)
            if(c.getType().equals("Email"))
                mailContacts[number++] = c;

        return mailContacts;
    }
}

class Faculty {

    private Student[] students;
    private String name;

    public Faculty(String name, Student[] students) {
        this.students = Arrays.copyOf(students, students.length);
        this.name=name;
    }

    public int countStudentsFromCity(String cityName) {
        int brojac = 0;
        for(Student s : students)
            if(s.getCity().equals(cityName))
                brojac++;
        return brojac;
    }

    public Student getStudent(long index) {
        for(Student s : students) {
            if(s.getIndex() == index)
                return s;
        }
        return null;
    }

    public double getAverageNumberOfContacts() {
        double prosek =0.0;
        for(Student s : students)
            prosek += s.getNumberContacts();
        return prosek/students.length;
    }

    public Student getStudentWithMostContacts() {
        Student mostContacts = students[0];
        for(int i=1;i<students.length;i++)
        {
            if(students[i].getNumberContacts() >= mostContacts.getNumberContacts()) {

                if(students[i].getNumberContacts() == mostContacts.getNumberContacts())
                {
                    if (students[i].getIndex() >= mostContacts.getIndex())
                        mostContacts = students[i];
                }
                else {
                    mostContacts = students[i];
                }

            }
        }
        return mostContacts;
    }

    @Override
    public String toString() {
        return "{\"fakultet\":\"" + name + "\", " +
                "\"studenti\":" + Arrays.toString(students) +
                '}';
    }
}

public class ContactsTester {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();
        Faculty faculty = null;

        int rvalue = 0;
        long rindex = -1;

        DecimalFormat df = new DecimalFormat("0.00");

        for (int t = 0; t < tests; t++) {

            rvalue++;
            String operation = scanner.next();

            switch (operation) {
                case "CREATE_FACULTY": {
                    String name = scanner.nextLine().trim();
                    int N = scanner.nextInt();

                    Student[] students = new Student[N];

                    for (int i = 0; i < N; i++) {
                        rvalue++;

                        String firstName = scanner.next();
                        String lastName = scanner.next();
                        String city = scanner.next();
                        int age = scanner.nextInt();
                        long index = scanner.nextLong();

                        if ((rindex == -1) || (rvalue % 13 == 0))
                            rindex = index;

                        Student student = new Student(firstName, lastName, city,
                                age, index);
                        students[i] = student;
                    }

                    faculty = new Faculty(name, students);
                    break;
                }

                case "ADD_EMAIL_CONTACT": {
                    long index = scanner.nextInt();
                    String date = scanner.next();
                    String email = scanner.next();

                    rvalue++;

                    if ((rindex == -1) || (rvalue % 3 == 0))
                        rindex = index;

                    faculty.getStudent(index).addEmailContact(date, email);
                    break;
                }

                case "ADD_PHONE_CONTACT": {
                    long index = scanner.nextInt();
                    String date = scanner.next();
                    String phone = scanner.next();

                    rvalue++;

                    if ((rindex == -1) || (rvalue % 3 == 0))
                        rindex = index;

                    faculty.getStudent(index).addPhoneContact(date, phone);
                    break;
                }

                case "CHECK_SIMPLE": {
                    System.out.println("Average number of contacts: "
                            + df.format(faculty.getAverageNumberOfContacts()));

                    rvalue++;

                    String city = faculty.getStudent(rindex).getCity();
                    System.out.println("Number of students from " + city + ": "
                            + faculty.countStudentsFromCity(city));

                    break;
                }

                case "CHECK_DATES": {

                    rvalue++;

                    System.out.print("Latest contact: ");
                    Contact latestContact = faculty.getStudent(rindex)
                            .getLatestContact();
                    if (latestContact.getType().equals("Email"))
                        System.out.println(((EmailContact) latestContact)
                                .getEmail());
                    if (latestContact.getType().equals("Phone"))
                        System.out.println(((PhoneContact) latestContact)
                                .getPhone()
                                + " ("
                                + ((PhoneContact) latestContact).getOperator()
                                .toString() + ")");

                    if (faculty.getStudent(rindex).getEmailContacts().length > 0
                            && faculty.getStudent(rindex).getPhoneContacts().length > 0) {
                        System.out.print("Number of email and phone contacts: ");
                        System.out
                                .println(faculty.getStudent(rindex)
                                        .getEmailContacts().length
                                        + " "
                                        + faculty.getStudent(rindex)
                                        .getPhoneContacts().length);

                        System.out.print("Comparing dates: ");
                        int posEmail = rvalue
                                % faculty.getStudent(rindex).getEmailContacts().length;
                        int posPhone = rvalue
                                % faculty.getStudent(rindex).getPhoneContacts().length;

                        System.out.println(faculty.getStudent(rindex)
                                .getEmailContacts()[posEmail].isNewerThan(faculty
                                .getStudent(rindex).getPhoneContacts()[posPhone]));
                    }

                    break;
                }

                case "PRINT_FACULTY_METHODS": {
                    System.out.println("Faculty: " + faculty.toString());
                    System.out.println("Student with most contacts: "
                            + faculty.getStudentWithMostContacts().toString());
                    break;
                }

            }

        }

        scanner.close();
    }
}

