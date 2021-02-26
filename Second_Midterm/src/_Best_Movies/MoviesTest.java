package _Best_Movies;

import java.util.*;
import java.util.stream.Collectors;

class Movie {

    private String title;
    private int[] ratings;

    public Movie(String title, int[] ratings) {
        this.title = title;
        this.ratings = ratings;
    }

    public String getTitle() {
        return title;
    }

    public double getAverageRating() {
        return Arrays.stream(this.ratings)
                .average().getAsDouble();
    }

    public double getCoef() {
        return getAverageRating() * this.ratings.length;
    }

    @Override
    public String toString() {
        return String.format("%s (%.2f) of %d ratings",
                this.title, this.getAverageRating(), this.ratings.length);
    }
}

class MoviesList {
    private List<Movie> movies;
    private int maxNumber;

    public MoviesList() {
        this.movies = new ArrayList<>();
        this.maxNumber = 0;
    }

    public void addMovie(String title, int[] ratings) {
        this.movies.add(new Movie(title, ratings));
        this.maxNumber += ratings.length;
    }

    public List<Movie> top10ByAvgRating() {
        return this.movies.stream().sorted(Comparator
                .comparing(Movie::getAverageRating).reversed()
                .thenComparing(Movie::getTitle))
                .limit(10).collect(Collectors.toList());
    }

    public List<Movie> top10ByRatingCoef() {
        return this.movies.stream().sorted(Comparator
                .comparing(i -> ((Movie)i).getCoef() / maxNumber).reversed()
                .thenComparing(i -> ((Movie)i).getTitle()))
                .limit(10).collect(Collectors.toList());
    }
}

public class MoviesTest {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MoviesList moviesList = new MoviesList();
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            int x = scanner.nextInt();
            int[] ratings = new int[x];
            for (int j = 0; j < x; ++j) {
                ratings[j] = scanner.nextInt();
            }
            scanner.nextLine();
            moviesList.addMovie(title, ratings);
        }
        scanner.close();
        List<Movie> movies = moviesList.top10ByAvgRating();
        System.out.println("=== TOP 10 BY AVERAGE RATING ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
        movies = moviesList.top10ByRatingCoef();
        System.out.println("=== TOP 10 BY RATING COEFFICIENT ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }
}