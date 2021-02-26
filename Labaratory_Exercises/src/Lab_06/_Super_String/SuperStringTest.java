package Lab_06._Super_String;

import java.util.*;
import java.util.stream.Collectors;

class TimeString {
    private long time;
    private String string;

    public TimeString(long time, String string) {
        this.time = time;
        this.string = string;
    }

    public long getTime() {
        return time;
    }

    public String getString() {
        return string;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setString(String string) {
        this.string = string;
    }
}

class SuperString {

    private final List<TimeString> list;

    public SuperString() {
        list = new LinkedList<>();
    }

    public void append(String s) {
        list.add(new TimeString(list.size(), s));
    }

    public void insert(String s) {
        list.add(0, new TimeString(list.size(), s));
    }

    public boolean contains(String s) {
        return list.stream().map(TimeString::getString).collect(Collectors.joining()).contains(s);
    }

    public void reverse() {
        Collections.reverse(list);
        for(TimeString s : list)
            s.setString(new StringBuilder(s.getString()).
                    reverse().toString());
    }

    @Override
    public String toString() {
        return list.stream().map(TimeString::getString).collect(Collectors.joining());
    }

    private List<TimeString> sortByTime(List<TimeString> list) {
        return list.stream().sorted(Comparator.comparing(TimeString::getTime).reversed()).
                collect(Collectors.toList());
    }

    public void removeLast(int k) {
        List<TimeString> tempList = sortByTime(list);
        for(int i=0; i<k&&i<tempList.size(); i++)
            list.remove(tempList.get(i));
    }
}

public class SuperStringTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (  k == 0 ) {
            SuperString s = new SuperString();
            while ( true ) {
                int command = jin.nextInt();
                if ( command == 0 ) {//append(String s)
                    s.append(jin.next());
                }
                if ( command == 1 ) {//insert(String s)
                    s.insert(jin.next());
                }
                if ( command == 2 ) {//contains(String s)
                    System.out.println(s.contains(jin.next()));
                }
                if ( command == 3 ) {//reverse()
                    s.reverse();
                }
                if ( command == 4 ) {//toString()
                    System.out.println(s);
                }
                if ( command == 5 ) {//removeLast(int k)
                    s.removeLast(jin.nextInt());
                }
                if ( command == 6 ) {//end
                    break;
                }
            }
        }
    }

}

