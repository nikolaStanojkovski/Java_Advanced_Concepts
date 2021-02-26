package _Measuring_Station;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

class DayMeasurement {
    private float temperature, wind, humidity, visibility;
    private Date date;

    public DayMeasurement(float temperature, float wind, float humidity, float visibility, Date date) {
        this.temperature = temperature;
        this.wind = wind;
        this.humidity = humidity;
        this.date = date;
        this.visibility = visibility;
    }

    public float getTemperature() {
        return temperature;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return temperature + " " + wind + " km/h " + humidity + "% " +
                visibility + " km " + date.toString();
    }
}

class WeatherStation {
    private List<DayMeasurement> Days;
    private int noDays;

    public WeatherStation(int days) {
        this.noDays = days;
        Days = new ArrayList<>();
    }

    public Date wantedDate() {
        Date temp = Days.stream().map(day -> day.getDate()).max(Date::compareTo).get();
        Date maxDay = new Date();
        maxDay.setTime(temp.getTime() - (noDays*24*60*60*1000));
        return maxDay;
    }

    public void filterFirst() {
        Days = Days.stream().filter(date -> date.getDate().after(wantedDate())).
                collect(Collectors.toList());
    }


    public void addMeasurment(float temperature, float wind, float humidity, float visibility, Date date) {
        DayMeasurement toAdd = new DayMeasurement(temperature, wind, humidity, visibility, date);

        if(Days.size() != 0)
            filterFirst();

        Date first = new Date(); Date second = new Date();
        first.setTime(toAdd.getDate().getTime() - 150000);
        second.setTime(toAdd.getDate().getTime() + 150000);
        if(filterStatus(first, second) == 0)
            Days.add(toAdd);

    }

    public int total() {
        return Days.size();
    }

    public long filterStatus(Date from, Date to) {
        return Days.stream().filter(day -> day.getDate().after(from)).
                filter(day -> day.getDate().before(to)).count();
    }

    public void status(Date from, Date to) throws RuntimeException {
        StringBuilder sb = new StringBuilder();
        List<DayMeasurement> toPrint = Days.stream().filter(day -> ( day.getDate().after(from) || day.getDate().equals(from))).
                filter(day -> ( day.getDate().before(to) || day.getDate().equals(to)) ).sorted(Comparator.comparing(o -> o.getDate())).collect(Collectors.toList());
        toPrint.forEach(day -> sb.append(day.toString() + "\n"));
        if(Float.isNaN(averageTemperature(toPrint)))
            throw new RuntimeException();
        else
            sb.append(String.format("Average temperature: %.2f", averageTemperature(toPrint)));
        System.out.println(sb.toString());
    }

    public float averageTemperature(List<DayMeasurement> list) {
        float average = 0;
        for(DayMeasurement day : list) {
            average += day.getTemperature();
        }
        return average/list.size();
    }
}

public class WeatherStationTest {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        int n = scanner.nextInt();
        scanner.nextLine();
        WeatherStation ws = new WeatherStation(n);
        while (true) {
            String line = scanner.nextLine();
            if (line.equals("=====")) {
                break;
            }
            String[] parts = line.split(" ");
            float temp = Float.parseFloat(parts[0]);
            float wind = Float.parseFloat(parts[1]);
            float hum = Float.parseFloat(parts[2]);
            float vis = Float.parseFloat(parts[3]);
            line = scanner.nextLine();
            Date date = df.parse(line);
            ws.addMeasurment(temp, wind, hum, vis, date);
        }
        String line = scanner.nextLine();
        Date from = df.parse(line);
        line = scanner.nextLine();
        Date to = df.parse(line);
        scanner.close();
        System.out.println(ws.total());
        try {
            ws.status(from, to);
        } catch (RuntimeException e) {
            System.out.println(e);
        }
    }
}