package _Time_Table;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

enum TimeFormat {
    FORMAT_24, FORMAT_AMPM
}

class UnsupportedFormatException extends  RuntimeException {
    public UnsupportedFormatException(String line) {
        super(line);
    }
}

class InvalidTimeException extends RuntimeException {
    public InvalidTimeException(int line) {
        super(""+line);
    }
}

abstract class Time implements Comparable<Time> {
    private int hours, minutes;

    public Time(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    private static void checkParts(String line) {
        for(char c : line.toCharArray()) {
            if (!Character.isDigit(c)) {
                throw new UnsupportedFormatException(line);
            }
        }
    }

    public int getTime() {
        return (hours*60) + minutes;
    }

    @Override
    public int compareTo(Time o) {
        return Integer.compare(this.getTime(), o.getTime());
    }

    public static Time readFromInput(String line) throws InvalidTimeException, UnsupportedFormatException {
        String[] parts = line.split("[:.]");
        checkParts(parts[0]);
        checkParts(parts[1]);
        checkNumber(Integer.parseInt(parts[0]));
        checkNumber(Integer.parseInt(parts[1]));
        return new Time24(Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1]));
    }

    private static void checkNumber(int parseInt) {
        if(parseInt >= 60 || parseInt < 0)
            throw new InvalidTimeException(parseInt);
    }

    public abstract String getToPMAM();
    public abstract String getTo24Hour();
}

class Time24 extends Time {

    public Time24(int hours, int minutes) {
        super(hours, minutes);
    }

    @Override
    public String getToPMAM() {
        int h = 0;
        if(getHours() == 0) {
            h = 12;
            return String.format("%2d:%02d AM\n", h, getMinutes());
        }
        else if(getHours() >= 1 && getHours() <= 11) {
            h = getHours();
            return String.format("%2d:%02d AM\n", h, getMinutes());
        } else {
            if(getHours() == 12)
                h = 12;
            else
                h = getHours()-12;
            return String.format("%2d:%02d PM\n", h, getMinutes());
        }
    }

    @Override
    public String getTo24Hour() {
        return String.format("%2d:%02d\n", getHours(), getMinutes());
    }
}

class TimeTable {

    List<Time> times;

    public TimeTable() {
        times = new ArrayList<>();
    }

    public void readTimes(InputStream in) throws IOException, InvalidTimeException, UnsupportedFormatException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line = "";
        while((line = br.readLine()) != null) {
            if(line.equals(""))
                break;

            String[] parts = line.split("\\s+");
            for(String p : parts) {
                times.add(Time.readFromInput(p));
            }
        }
    }

    public String convertToFormat24() {
        StringBuilder sb = new StringBuilder();
        for(Time t : times) {
            sb.append(t.getTo24Hour());
        }
        return sb.toString();
    }

    public String convertToAMPM() {
        StringBuilder sb = new StringBuilder();
        for(Time t : times) {
            sb.append(t.getToPMAM());
        }
        return sb.toString();
    }

    public void writeTimes(PrintStream out, TimeFormat format24) throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
        times.sort(Comparator.naturalOrder());
        String toPrint = "";
        if(format24 == TimeFormat.FORMAT_24)
            toPrint = convertToFormat24();
        else
            toPrint = convertToAMPM();
        bw.write(toPrint);
        bw.flush();
    }
}

public class TimesTest {

    public static void main(String[] args) throws IOException {
        TimeTable timeTable = new TimeTable();
        try {
            timeTable.readTimes(System.in);
        } catch (UnsupportedFormatException e) {
            System.out.println("UnsupportedFormatException: " + e.getMessage());
        } catch (InvalidTimeException e) {
            System.out.println("InvalidTimeException: " + e.getMessage());
        }
        System.out.println("24 HOUR FORMAT");
        timeTable.writeTimes(System.out, TimeFormat.FORMAT_24);
        System.out.println("AM/PM FORMAT");
        timeTable.writeTimes(System.out, TimeFormat.FORMAT_AMPM);
    }

}