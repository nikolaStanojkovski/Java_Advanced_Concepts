package _Measuring_Station;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

class Measurement implements Comparable<Measurement> {
    private Float temperature, wind, humidity, visibility;
    private Date time;

    public Measurement(Float temperature, Float wind, Float humidity, Float visibility, Date time) {
        this.temperature = temperature;
        this.wind = wind;
        this.humidity = humidity;
        this.visibility = visibility;
        this.time = time;
    }

    public Float getTemperature() {
        return temperature;
    }

    public Float getWind() {
        return wind;
    }

    public Float getHumidity() {
        return humidity;
    }

    public Float getVisibility() {
        return visibility;
    }

    public Date getTime() {
        return time;
    }

    @Override
    public int compareTo(Measurement o) {
        return this.getTime().compareTo(o.getTime());
    }

    public static boolean checkInterval(Date fromM, Date toM) {
        long time = Math.abs(fromM.getTime() - toM.getTime());
        return time < 150000;
    }

    @Override
    public String toString() {
        return String.format("%.1f %.1f km/h %.1f%% %.1f km %s",
                this.getTemperature(), this.getWind(), this.getHumidity(),
                this.getVisibility(), this.getTime());
    }
}

class WeatherStation {
    private List<Measurement> measurements;
    private int days;

    public WeatherStation(int days) {
        this.days = days;
        this.measurements = new ArrayList<>();
    }

    public boolean checkInterval25(Date date) {
        for(Measurement m : measurements)
            if(Measurement.checkInterval(m.getTime(), date))
                return true;

        return false;
    }

    public void checkDelete(Date fromM) {
        long milisDays = (long) days * 24 * 60 * 60 * 1000;
        long milisM = fromM.getTime();

        for(int i=0; i<this.measurements.size();i++) {
            long toCompare = Math.abs(milisDays - milisM);
            if(measurements.get(i).getTime().getTime() <= toCompare) {
                measurements.remove(measurements.get(i));
            }

        }
    }

    public void addMeasurment(float temperature, float wind, float humidity, float visibility, Date date) {
        Measurement m = new Measurement(temperature, wind, humidity, visibility, date);
        if(!checkInterval25(date)) {
            checkDelete(m.getTime());
            this.measurements.add(m);
        }
    }

    public int total() {
        return this.measurements.size();
    }

    public void status(Date from, Date to) throws RuntimeException {
        List<Measurement> filtered =
                this.measurements.stream().filter(i -> (i.getTime().after(from) || i.getTime().equals(from)) &&
                        (i.getTime().before(to) || i.getTime().equals(to))).sorted().collect(Collectors.toList());

        if(filtered.size() == 0)
            throw new RuntimeException();

        filtered.forEach(System.out::println);
        System.out.printf("Average temperature: %.2f%n", filtered.stream().
                mapToDouble(Measurement::getTemperature).average().getAsDouble());
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