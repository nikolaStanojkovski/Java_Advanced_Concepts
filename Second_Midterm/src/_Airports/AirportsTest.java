package _Airports;

import java.util.*;

class Airport {
    private String name,country,code;
    private int passengers;

    public Airport(String name, String country, String code, int passengers) {
        this.name = name;
        this.country = country;
        this.code = code;
        this.passengers = passengers;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getCode() {
        return code;
    }

    public int getPassengers() {
        return passengers;
    }
}

class Flight implements Comparable<Flight> {
    private String from, to;
    private int duration, time;

    public Flight(String from, String to, int time, int duration) {
        this.from = from;
        this.to = to;
        this.duration = duration;
        this.time = time;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public int getDuration() {
        return duration;
    }

    public int getTime() {
        return time;
    }

    public int getFromHours() {
        return this.getTime()/60;
    }

    public int getToHours() {
        return (this.getTime() + this.getDuration())/60;
    }

    public int getFromMinutes() {
        return this.getTime()%60;
    }

    public int getToMinutes() {
        return (this.getTime() + this.getDuration())%60;
    }

    public int getDurationHours() {
        return this.getDuration()/60;
    }

    public int getDurationMinutes() {
        return this.getDuration()%60;
    }

    @Override
    public String toString() {
        String isHiger = "";
        if(getToHours() >= 24)
            isHiger += "+1d ";
        return String.format("%s-%s %02d:%02d-%02d:%02d %s%dh%02dm", getFrom(), getTo(),
                getFromHours()%24, getFromMinutes(), getToHours()%24, getToMinutes(), isHiger,
                getDurationHours(), getDurationMinutes());
    }

    @Override
    public int compareTo(Flight o) {
        int res = this.getTo().compareTo(o.getTo());
        if(res == 0) {
            int res2 = Integer.compare(this.getTime(), o.getTime());
            if (res2 == 0)
                return Integer.compare(this.getDuration(), o.getDuration());
            else return res2;
        } else
            return res;
    }
}

class Airports {

    private Set<Airport> airports;

    // code, set
    private Map<Airport, Set<Flight>> toFlights;

    // code, set
    private Map<Airport, Set<Flight>> fromFlights;

    // code:code, set
    private Map<String, Set<Flight>> fromToFlights;

    public Airports() {
        this.airports = new HashSet<>();
        this.toFlights = new HashMap<>();
        this.fromFlights = new HashMap<>();
        this.fromToFlights = new HashMap<>();
    }

    public void addAirport(String name, String country, String code, int passengers) {
        Airport a = new Airport(name, country, code, passengers);

        this.airports.add(a);
        this.toFlights.putIfAbsent(a, new TreeSet<>());
        this.fromFlights.putIfAbsent(a, new TreeSet<>());
    }

    public void addFlights(String from, String to, int time, int duration) {
        Flight f = new Flight(from, to, time, duration);
        Airport fromAirport = this.airports.stream().filter(i ->
                i.getCode().equals(from)).findAny().get();
        Airport toAirport = this.airports.stream().filter(i ->
                i.getCode().equals(to)).findAny().get();

        this.fromFlights.get(fromAirport).add(f);
        this.toFlights.get(toAirport).add(f);
        this.fromToFlights.putIfAbsent(from+":"+to, new TreeSet<>());
        this.fromToFlights.get(from+":"+to).add(f);
    }

    public void printFlights(Airport from) {
        int brojac = 1;
        for(Flight f : this.fromFlights.get(from)) {
            System.out.println(brojac + ". " + f);
            brojac++;
        }
    }

    public void showFlightsFromAirport(String from) {
        Airport toPrint = this.airports.stream().filter(i -> i.getCode().equals(from)).findAny().get();
        System.out.printf("%s (%s)\n%s\n%d\n", toPrint.getName(),
                toPrint.getCode(), toPrint.getCountry(), toPrint.getPassengers());

        printFlights(toPrint);
    }

    public void showDirectFlightsTo(String to) {
        Airport toFind = this.airports.stream().filter(i ->
                i.getCode().equals(to)).findAny().get();

        this.toFlights.get(toFind).forEach(System.out::println);
    }

    public void showDirectFlightsFromTo(String from, String to) {
        if(!this.fromToFlights.containsKey(from+":"+to))
            System.out.printf("No flights from %s to %s\n", from, to);
        else
            this.fromToFlights.get(from+":"+to).forEach(System.out::println);
    }
}

public class AirportsTest {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Airports airports = new Airports();
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] codes = new String[n];
        for (int i = 0; i < n; ++i) {
            String al = scanner.nextLine();
            String[] parts = al.split(";");
            airports.addAirport(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]));
            codes[i] = parts[2];
        }
        int nn = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < nn; ++i) {
            String fl = scanner.nextLine();
            String[] parts = fl.split(";");
            airports.addFlights(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
        }
        int f = scanner.nextInt();
        int t = scanner.nextInt();
        String from = codes[f];
        String to = codes[t];
        System.out.printf("===== FLIGHTS FROM %S =====\n", from);
        airports.showFlightsFromAirport(from);
        System.out.printf("===== DIRECT FLIGHTS FROM %S TO %S =====\n", from, to);
        airports.showDirectFlightsFromTo(from, to);
        t += 5;
        t = t % n;
        to = codes[t];
        System.out.printf("===== DIRECT FLIGHTS TO %S =====\n", to);
        airports.showDirectFlightsTo(to);
    }
}