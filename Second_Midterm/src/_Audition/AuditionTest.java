package _Audition;

import java.util.*;

class Candidate implements Comparable<Candidate> {
    private String city, code, name;
    private int age;

    public Candidate(String city, String code, String name, int age) {
        this.city = city;
        this.code = code;
        this.name = name;
        this.age = age;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public int compareTo(Candidate o) {
        int res = this.getName().compareTo(o.getName());
        if(res == 0) {
            int res2 = Integer.compare(this.getAge(), o.getAge());
            if(res2 == 0)
                return this.getCode().compareTo(o.getCode());
            else
                return res2;
        } else
            return res;
    }

    @Override
    public String toString() {
        return String.format("%s %s %d", this.getCode(),
                this.getName(), this.getAge());
    }
}

class Audition {

    private Map<String, TreeSet<Candidate>> byCity;

    public Audition() {
        this.byCity = new HashMap<>();
    }

    public void addParticpant(String city, String code, String name, int age) {
        if(byCity.containsKey(city) &&
                byCity.get(city).stream().anyMatch(i -> i.getCode().equals(code)))
            return;

        Candidate c = new Candidate(city, code, name, age);

        byCity.putIfAbsent(city, new TreeSet<>());
        byCity.get(city).add(c);
    }

    public void listByCity(String city) {
        this.byCity.get(city).forEach(System.out::println);
    }
}

public class AuditionTest {
    public static void main(String[] args) {
        Audition audition = new Audition();
        List<String> cities = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            if (parts.length > 1) {
                audition.addParticpant(parts[0], parts[1], parts[2],
                        Integer.parseInt(parts[3]));
            } else {
                cities.add(line);
            }
        }
        for (String city : cities) {
            System.out.printf("+++++ %s +++++\n", city);
            audition.listByCity(city);
        }
        scanner.close();
    }
}