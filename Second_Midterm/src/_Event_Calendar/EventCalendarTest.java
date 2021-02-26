package _Event_Calendar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.IntStream;

class WrongDateException extends Exception {
    public WrongDateException(Date date) {
        super(String.format("Wrong date: %s", date.toString()));
    }
}

class Event implements Comparable<Event> {
    private String name, location;
    private Date date;

    public Event(String name, String location, Date date) {
        this.name = name;
        this.location = location;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public int compareTo(Event o) {
        int res = this.getDate().compareTo(o.getDate());
        if(res == 0)
            return this.getName().compareTo(o.getName());
        else
            return res;
    }

    @Override
    public String toString() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, YYY HH:mm");
        return String.format("%s at %s, %s",
                sdf.format(this.getDate()), this.getLocation(), this.getName());
    }
}

class EventCalendar {

    private int year;
    private Map<String, Set<Event>> events;
    private Map<Integer, Set<Event>> calendar;

    public EventCalendar(int year) {
        this.events = new HashMap<>();
        this.calendar = new HashMap<>();
        this.year = year;

        fillCalendar();
    }

    public void fillCalendar() {
        IntStream.range(1, 13).forEach(i -> calendar.putIfAbsent(i, new HashSet<>()));
    }

    public void addEvent(String name, String location, Date date) throws WrongDateException {
        if(date.getYear() + 1900 != this.year)
            throw new WrongDateException(date);
        else {
            Event e = new Event(name, location, date);

            this.events.putIfAbsent(date.getDate() + ":" + date.getMonth(), new HashSet<>());
            this.events.get(date.getDate() + ":" + date.getMonth()).add(e);

            this.calendar.putIfAbsent(date.getMonth()+1, new HashSet<>());
            this.calendar.get(date.getMonth()+1).add(e);
        }
    }

    public void listEvents(Date date) {
        if(events.get(date.getDate() + ":" + date.getMonth()) == null)
            System.out.println("No events on this day!");
        else
            this.events.get(date.getDate() + ":" + date.getMonth()).stream().
                    sorted().forEach(System.out::println);
    }

    public long getNumberFromMonth(int month) {
        if(this.calendar.get(month).isEmpty())
            return 0;
        return this.calendar.get(month).stream().count();
    }

    public void listByMonth() {
        IntStream.range(1,13).forEach(i -> System.out.printf("%d : %d\n",
                i, this.getNumberFromMonth(i)));
    }
}

public class EventCalendarTest {

    public static void main(String[] args) throws ParseException {

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        int year = scanner.nextInt();
        scanner.nextLine();
        EventCalendar eventCalendar = new EventCalendar(year);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            String name = parts[0];
            String location = parts[1];
            Date date = df.parse(parts[2]);
            try {
                eventCalendar.addEvent(name, location, date);
            } catch (WrongDateException e) {
                System.out.println(e.getMessage());
            }
        }
        Date date = df.parse(scanner.nextLine());
        eventCalendar.listEvents(date);
        eventCalendar.listByMonth();
    }
}