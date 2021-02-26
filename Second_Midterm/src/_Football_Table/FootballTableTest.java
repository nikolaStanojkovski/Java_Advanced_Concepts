package _Football_Table;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Team implements Comparable<Team> {
    private String name;

    private int noEqual, noWins, noLoses;

    private int accGoals;
    private int gotGoals;

    public Team(String name) {
        this.name = name;
        this.accGoals = 0;
        this.gotGoals = 0;
        this.noWins = 0;
        this.noLoses = 0;
        this.noEqual = 0;
    }

    public void incrementWins() {
        this.noWins++;
    }

    public void incrementLoses() {
        this.noLoses++;
    }

    public void incrementEqual() {
        this.noEqual++;
    }

    public void incrementAccGoals(int goals) {
        this.accGoals += goals;
    }

    public void incrementGotGoals(int goals) {
        this.gotGoals += goals;
    }

    public int getGoalDiff() {
        return accGoals - gotGoals;
    }

    public int calculatePoints() {
        return noWins*3 + noEqual;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("%-15s%5d%5d%5d%5d%5d", name,
                noEqual + noWins + noLoses, noWins, noEqual, noLoses, calculatePoints());
    }

    @Override
    public int compareTo(Team o) {
        int res = Integer.compare(this.calculatePoints(), o.calculatePoints());
        if(res == 0) {
            int res2 = Integer.compare(this.getGoalDiff(), o.getGoalDiff());
            if(res2 == 0)
                return this.getName().compareTo(o.getName());
            else
                return -res2;
        } else
            return -res;
    }
}

class FootballTable {
    
    private List<Team> teams;
    
    public FootballTable() {
        teams = new ArrayList<>();
    }

    private boolean hasTeam(String name) {
        return teams.stream().anyMatch(i -> i.getName().equals(name));
    }

    private Team getTeam(String name) {
        return teams.stream().filter(i -> i.getName().equals(name)).findFirst().get();
    }

    public void addGame(String homeTeam, String awayTeam, int homeGoals, int awayGoals) {
        if(!hasTeam(homeTeam))
            teams.add(new Team(homeTeam));
        if(!hasTeam(awayTeam))
            teams.add(new Team(awayTeam));

        if(homeGoals > awayGoals) {
            getTeam(homeTeam).incrementWins();
            getTeam(awayTeam).incrementLoses();
        } else if(homeGoals < awayGoals) {
            getTeam(homeTeam).incrementLoses();
            getTeam(awayTeam).incrementWins();
        } else {
            getTeam(homeTeam).incrementEqual();
            getTeam(awayTeam).incrementEqual();
        }

        getTeam(homeTeam).incrementAccGoals(homeGoals);
        getTeam(homeTeam).incrementGotGoals(awayGoals);
        getTeam(awayTeam).incrementAccGoals(awayGoals);
        getTeam(awayTeam).incrementGotGoals(homeGoals);
    }

    public void printTable() {

        teams = teams.stream().sorted().collect(Collectors.toList());

        IntStream.range(0, teams.size()).
                forEach(i -> System.out.println(
                        String.format("%2d. %s", i+1, teams.get(i))));

    }
}

public class FootballTableTest {
    public static void main(String[] args) throws IOException {
        FootballTable table = new FootballTable();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.lines()
                .map(line -> line.split(";"))
                .forEach(parts -> table.addGame(parts[0], parts[1],
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3])));
        reader.close();
        System.out.println("=== TABLE ===");
        System.out.printf("%-19s%5s%5s%5s%5s%5s\n", "Team", "P", "W", "D", "L", "PTS");
        table.printTable();
    }
}
