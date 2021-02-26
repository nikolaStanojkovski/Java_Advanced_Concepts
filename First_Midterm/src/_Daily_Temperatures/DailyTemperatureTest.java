package _Daily_Temperatures;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;

abstract class Stepen {
    Double stepen;
    boolean flag;

    public Stepen(double stepen) {
        this.stepen = stepen;
        flag = false;
    }

    public abstract Double getFarenheit();
    public abstract Double getCelsius();
}

class StepenFarenheit extends Stepen {

    public StepenFarenheit(double stepen) {
        super(stepen);
    }

    @Override
    public Double getFarenheit() {
        return stepen;
    }

    @Override
    public Double getCelsius() {
        return ((this.stepen-32) * 5) / 9;
    }

}

class StepenCelsius extends Stepen {

    public StepenCelsius(double stepen) {
        super(stepen);
    }

    @Override
    public Double getFarenheit() {
        return ((this.stepen * 9) / 5) + 32;
    }

    @Override
    public Double getCelsius() {
        return stepen;
    }
}

class Den implements Comparable<Den> {
    List<Stepen> stepeni;
    int day;
    char currentStepen;

    public Den(int day) {
        stepeni = new ArrayList<>();
        this.day = day;
    }

    public void setCurrentStepen(char currentStepen) {
        this.currentStepen = currentStepen;
    }

    public int getDay() {
        return day;
    }

    @Override
    public String toString() {
        DoubleSummaryStatistics statistics = this.stepeni
                .stream().mapToDouble(i -> {
                    if( currentStepen == 'C')
                        return i.getCelsius();
                    else
                        return i.getFarenheit();
                }).summaryStatistics();
        return String.format("%3d: Count: %3d Min: %6.2f%s Max: %6.2f%s Avg: %6.2f%s",
                day, statistics.getCount(), statistics.getMin(), currentStepen,
                statistics.getMax(), currentStepen, statistics.getAverage(), currentStepen);
    }

    @Override
    public int compareTo(Den o) {
        return Integer.compare(this.getDay(), o.getDay());
    }
}

class DailyTemperatures {

    List<Den> days;

    public DailyTemperatures() {
        days = new ArrayList<>();
    }

    public void read(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line = "";
        while((line = br.readLine()) != null) {

            if(line.equals(""))
                break;

            String[] parts = line.split("\\s+");
            Den newDay = new Den(Integer.parseInt(parts[0]));
            for(int i=1; i<parts.length;i++) {
                char type = parts[i].charAt(parts[i].length()-1);
                int temperatura = Integer.parseInt(parts[i].substring(0, parts[i].length()-1));
                if(type == 'C')
                    newDay.stepeni.add(new StepenCelsius(temperatura));
                else
                    newDay.stepeni.add(new StepenFarenheit(temperatura));
            }
            this.days.add(newDay);
        }
    }

    public void readTemperatures(InputStream in) {
        try {
            read(in);
        } catch (IOException ex) {}
    }

    public List<Den> transferToCelsius() {
        List<Den> novaLista = new ArrayList<>();
        for(Den d : days) {
            Den den = new Den(d.getDay());
            for(Stepen s : d.stepeni) {
                den.stepeni.add(new StepenCelsius(s.getCelsius()));
            }
            novaLista.add(den);
        }
        return novaLista;
    }

    public List<Den> transferToFarenheit() {
        List<Den> novaLista = new ArrayList<>();
        for(Den d : days) {
            Den den = new Den(d.getDay());
            for(Stepen s : d.stepeni) {
                den.stepeni.add(new StepenFarenheit(s.getFarenheit()));
            }
            novaLista.add(den);
        }
        return novaLista;
    }

    public String formatAll(char f, List<Den> daysToSort) {
        StringBuilder sb = new StringBuilder();
        for(Den day : daysToSort) {
            day.setCurrentStepen(f);
            sb.append(day.toString() + "\n");
        }
        return sb.toString();
    }

    public void writeDailyStats(PrintStream out, char f) {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
        List<Den> toPrint = new ArrayList<>();
        if(f == 'C')  {
            toPrint = transferToCelsius();
        } else if (f == 'F'){
            toPrint = transferToFarenheit();
        }
        toPrint.sort(Comparator.naturalOrder());
        try {
            bw.write(formatAll(f, toPrint));
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class DailyTemperatureTest {
    public static void main(String[] args) {
        DailyTemperatures dailyTemperatures = new DailyTemperatures();
        dailyTemperatures.readTemperatures(System.in);
        System.out.println("=== Daily temperatures in Celsius (C) ===");
        dailyTemperatures.writeDailyStats(System.out, 'C');
        System.out.println("=== Daily temperatures in Fahrenheit (F) ===");
        dailyTemperatures.writeDailyStats(System.out, 'F');
    }
}