package _Stadium;

import java.util.*;

class SeatTakenException extends Exception {
    public SeatTakenException() {
        super("SeatTakenException");
    }
}

class SeatNotAllowedException extends  Exception {
    public SeatNotAllowedException() {
        super("SeatNotAllowedException");
    }
}

class Sector implements Comparable<Sector> {

    private String name;
    private int noSeats;
    private int whichType;

    private Map<Integer, Boolean> seats;

    public Sector(String name, int noSeats) {
        this.name = name;
        this.noSeats = noSeats;
        this.whichType = 0;
        seats = new TreeMap<>();

        fillSeats();
    }

    public void fillSeats() {
        for(int i=1;i<=noSeats;i++)
            seats.putIfAbsent(i, false);
    }

    public String getName() {
        return name;
    }

    public int getNoSeats() {
        return this.noSeats;
    }

    public long getFreeSeats() {
        return this.seats.entrySet().stream()
                .filter(i -> !i.getValue())
                .count();
    }

    public double getPercentage() {
        return 100.0 - ( (getFreeSeats() / (double) getNoSeats()) * 100 );
    }

    public void buy(int seat, int type) throws SeatTakenException, SeatNotAllowedException {
        if(this.seats.get(seat))
            throw new SeatTakenException();

        if(this.whichType != type && type != 0 &&
            this.whichType != 0)
            throw new SeatNotAllowedException();

        if(this.whichType == 0)
            this.whichType = type;

        this.seats.put(seat, true);
    }

    @Override
    public String toString() {
        return String.format("%s\t%d/%d\t%.1f%%",
                this.getName(), this.getFreeSeats(), this.getNoSeats(),
                this.getPercentage());
    }

    @Override
    public int compareTo(Sector o) {
        int res = Double.compare(this.getPercentage(), o.getPercentage());
        if(res == 0) {
            return this.getName().compareTo(o.getName());
        } else
            return res;
    }
}

class Stadium {
    private String name;
    private Set<Sector> sectors;

    public Stadium(String name) {
        this.name = name;
        this.sectors = new HashSet<>();
    }

    public void createSectors(String[] sectorNames, int[] sizes) {
        for(int i=0;i<sectorNames.length;i++) {
            sectors.add(new Sector(sectorNames[i],
                    sizes[i]));
        }
    }

    public void buyTicket(String sectorName, int seat, int type) throws SeatTakenException, SeatNotAllowedException {
        this.sectors.stream().filter(i -> i.getName().equals(sectorName))
            .findAny().get().buy(seat, type);
    }

    public void showSectors() {
        this.sectors.stream().sorted().forEach(System.out::println);
    }
}

public class StaduimTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] sectorNames = new String[n];
        int[] sectorSizes = new int[n];
        String name = scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            sectorNames[i] = parts[0];
            sectorSizes[i] = Integer.parseInt(parts[1]);
        }
        Stadium stadium = new Stadium(name);
        stadium.createSectors(sectorNames, sectorSizes);
        n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            try {
                stadium.buyTicket(parts[0], Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2]));
            } catch (SeatNotAllowedException e) {
                System.out.println("SeatNotAllowedException");
            } catch (SeatTakenException e) {
                System.out.println("SeatTakenException");
            }
        }
        stadium.showSectors();
    }
}