package _My_DDV;

import java.io.*;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

class AmountNotAllowedException extends RuntimeException {
    public AmountNotAllowedException(int sum) {
        super("Receipt with amount " + sum + " is not allowed to be scanned");
    }
}

class Item {
    private Double price;
    private char type;

    public Item(Double price, char type) {
        this.price = price;
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public Double getDanok() {
        if(type == 'A')
            return price*0.18*0.15;
        else if(type == 'B')
            return price*0.05*0.15;
        else
            return 0.0;
    }

    public char getType() {
        return type;
    }
}

class FiskalnaSmetka {
    String id;
    List<Item> items;

    public FiskalnaSmetka(String id) {
        this.id = id;
        this.items = new ArrayList<>();
    }

    public Double getIznos() {
        return items.stream().mapToDouble(i -> i.getPrice()).sum();
    }

    public Double getPovratok() {
        return items.stream().mapToDouble(i -> i.getDanok()).sum();
    }

    public static FiskalnaSmetka read(String line) throws AmountNotAllowedException {
        String[] parts = line.split(" ");
        FiskalnaSmetka fs = new FiskalnaSmetka(parts[0]);
        for(int i=1;i<parts.length;i+=2) {
            Item newItem = new Item(Double.parseDouble(parts[i]),
                    parts[i+1].charAt(0));
            fs.items.add(newItem);
        }
        if(fs.getIznos() > 30000)
            throw new AmountNotAllowedException(fs.getIznos().intValue());
        else
            return fs;
    }

    @Override
    public String toString() {
        return String.format("%10s\t%10d\t%10.5f\n", id,
                getIznos().intValue(), getPovratok());
    }
}

class MojDDV {

    List<FiskalnaSmetka> fiskalnaSmetkas;

    public MojDDV() {
        fiskalnaSmetkas = new ArrayList<>();
    }

    public void readRecords(InputStream in) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        fiskalnaSmetkas = br.lines().map(line -> {
            try{
                return FiskalnaSmetka.read(line);
            } catch (AmountNotAllowedException ex) {
                System.out.println(ex.getMessage());
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public void printTaxReturns(PrintStream out) throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
        bw.write(writeOut());
        bw.flush();
    }

    private String writeOut() {
         return fiskalnaSmetkas.stream().map(i -> i.toString()).collect(Collectors.joining());
    }

    public void printStatistics(PrintStream out) throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
        DoubleSummaryStatistics summaryStatistics =
                fiskalnaSmetkas.stream().mapToDouble(i -> i.getPovratok()).summaryStatistics();
        bw.write(printSummary(summaryStatistics));
        bw.flush();
    }

    public String printSummary(DoubleSummaryStatistics summaryStatistics) {
        return String.format("min:\t%5.3f\nmax:\t%5.3f\nsum:\t%5.3f\ncount:\t%-5d\navg:\t%5.3f\n",
                summaryStatistics.getMin(), summaryStatistics.getMax(), summaryStatistics.getSum(),
                summaryStatistics.getCount(), summaryStatistics.getAverage());
    }
}

public class MojDDVTest {

    public static void main(String[] args) throws IOException{

        MojDDV mojDDV = new MojDDV();

        System.out.println("===READING RECORDS FROM INPUT STREAM===");
        mojDDV.readRecords(System.in);

        System.out.println("===PRINTING TAX RETURNS RECORDS TO OUTPUT STREAM ===");
        mojDDV.printTaxReturns(System.out);

        System.out.println("===PRINTING SUMMARY STATISTICS FOR TAX RETURNS TO OUTPUT STREAM===");
        mojDDV.printStatistics(System.out);

    }
}