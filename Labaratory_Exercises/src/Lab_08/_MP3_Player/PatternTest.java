package Lab_08._MP3_Player;

import java.util.ArrayList;
import java.util.List;

class Song {
    private String title, artist;

    public Song(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "Song{" +
                "title=" + title +
                ", artist=" + artist + "}";
    }
}

class MP3Player {
    private List<Song> songs;
    private int currentSong;
    private boolean wasStopped, wasPlayed, wasAlreadyStopped;

    public MP3Player(List<Song> songs) {
        this.songs = songs;
        this.currentSong = 0;
        this.wasStopped = false;
        this.wasAlreadyStopped = false;
    }

    public void pressPlay() {
        this.wasStopped = false;
        this.wasAlreadyStopped = false;
        if(wasPlayed) {
            System.out.printf("Song is already playing%n", currentSong);
        } else {
            System.out.printf("Song %d is playing%n", currentSong);
            wasPlayed = true;
        }
    }

    public void printCurrentSong() {
        System.out.println(songs.get(currentSong));
    }

    public void pressStop() {

        if(wasStopped && wasAlreadyStopped)
            System.out.println("Songs are already stopped");
        else if(wasStopped) {
            currentSong = 0;
            wasAlreadyStopped=true;
            System.out.println("Songs are stopped");
        } else {
            System.out.printf("Song %d is paused%n", currentSong);
            this.wasStopped = true;
        }
        this.wasPlayed = false;
    }

    public void pressFWD() {
        wasStopped = true;
        wasPlayed = false;
        wasAlreadyStopped = false;
        System.out.println("Forward...");
        currentSong = (currentSong+1)%songs.size();
    }

    public void pressREW() {
        wasStopped = true;
        wasPlayed = false;
        wasAlreadyStopped = false;
        System.out.println("Reward...");
        if((currentSong-1) < 0)
            currentSong = songs.size()-1;
        else
            currentSong = (currentSong-1)%songs.size();
    }

    @Override
    public String toString() {
        return "MP3Player{" +
                "currentSong = " + currentSong +
                ", songList = " + songs + "}" ;
    }
}

public class PatternTest {
    public static void main(String args[]) {
        List<Song> listSongs = new ArrayList<>();
        listSongs.add(new Song("first-title", "first-artist"));
        listSongs.add(new Song("second-title", "second-artist"));
        listSongs.add(new Song("third-title", "third-artist"));
        listSongs.add(new Song("fourth-title", "fourth-artist"));
        listSongs.add(new Song("fifth-title", "fifth-artist"));
        MP3Player player = new MP3Player(listSongs);


        System.out.println(player.toString());
        System.out.println("First test");


        player.pressPlay();
        player.printCurrentSong();
        player.pressPlay();
        player.printCurrentSong();

        player.pressPlay();
        player.printCurrentSong();
        player.pressStop();
        player.printCurrentSong();

        player.pressPlay();
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressPlay();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();


        System.out.println(player.toString());
        System.out.println("Second test");


        player.pressStop();
        player.printCurrentSong();
        player.pressStop();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressPlay();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();


        System.out.println(player.toString());
        System.out.println("Third test");


        player.pressFWD();
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressPlay();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressStop();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();


        System.out.println(player.toString());
    }
}
