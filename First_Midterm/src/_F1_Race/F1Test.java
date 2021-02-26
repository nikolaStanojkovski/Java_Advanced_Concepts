package _F1_Race;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class Time {
    private int minutes,seconds,miliseconds;

    public static Time read(String part) {
        String[] parts = part.split(":");
        int minutes = Integer.parseInt(parts[0]);
        int seconds = Integer.parseInt(parts[1]);
        int miliseconds = Integer.parseInt(parts[2]);
        return new Time(minutes, seconds, miliseconds);
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public int getMiliseconds() {
        return miliseconds;
    }

    public int returnToMilis() {
        return miliseconds + (seconds*1000) + (minutes*60000);
    }

    public Time(int minutes, int seconds, int miliseconds) {
        this.minutes = minutes;
        this.seconds = seconds;
        this.miliseconds = miliseconds;
    }

    @Override
    public String toString() {
        return String.format("%d:%02d:%03d", minutes,seconds,miliseconds);
    }
}

class Driver implements Comparable<Driver> {
    private String name;
    List<Time> times;

    public Driver(String name) {
        times = new ArrayList<>();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Time getBestLap() {
        times.sort(Comparator.comparing(Time::returnToMilis));
        return times.get(0);
    }

    public static Driver readLine(String line) {
        String[] parts = line.split("\\s+");
        Driver newDriver = new Driver(parts[0]); // prviot element e imeto
        newDriver.times.add(Time.read(parts[1]));
        newDriver.times.add(Time.read(parts[2]));
        newDriver.times.add(Time.read(parts[3]));
        return newDriver;
    }

    @Override
    public int compareTo(Driver o) {
        return Integer.compare(this.getBestLap().returnToMilis(),
                o.getBestLap().returnToMilis());
    }
}

class F1Race {

    List<Driver> drivers;

    public F1Race() {
        drivers = new ArrayList<>();
    }

    public void readResults(InputStream in) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        drivers = br.lines().map(Driver::readLine).collect(Collectors.toList());
    }

    public void printSorted(OutputStream outputStream) throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));
        drivers = drivers.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        bw.write(print(drivers));
        bw.flush();
    }

    public String print(List<Driver> driverList) {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<driverList.size();i++) {
            sb.append(String.format("%d. %-10s%10s\n", i+1,
                    driverList.get(i).getName(), driverList.get(i).getBestLap()));
        }
        return sb.toString();
    }

}

public class F1Test {

    public static void main(String[] args) throws IOException {
        F1Race f1Race = new F1Race();
        f1Race.readResults(System.in);
        f1Race.printSorted(System.out);
    }

}