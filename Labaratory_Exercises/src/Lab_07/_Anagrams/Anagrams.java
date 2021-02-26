package Lab_07._Anagrams;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.TreeMap;

public class Anagrams {

    public static void main(String[] args) {
        findAll(System.in);
    }

    public static boolean checkAnagram(String line, String fromMap) {
        String tmp = fromMap;

        for(char c : line.toCharArray()) {
            if(!tmp.contains(c + ""))
                return false;

            tmp = tmp.replaceFirst(c + "", "");
        }

        return tmp.length() == 0;
    }

    public static TreeMap<String, ArrayList<String>> readResults(TreeMap<String, ArrayList<String>> wordMap, InputStream inputStream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        br.lines().forEach(i -> {

            wordMap.forEach((key, value) -> {
                if (checkAnagram(i, key)) {
                    wordMap.get(key).add(i);
                }
            });

            if(wordMap.values().stream().noneMatch(j ->
                    j.contains(i))) {
                wordMap.put(i, new ArrayList<>());
                wordMap.get(i).add(i);
            }

        });

        return wordMap;
    }

    public static void printResults(TreeMap<String, ArrayList<String>> wordMap) {
        wordMap.entrySet().stream().filter(i -> i.getValue().size() >= 5)
                .forEach(i -> System.out.println(String.join(" ", i.getValue())));
    }

    public static void findAll(InputStream inputStream) {
        // Vasiod kod ovde

        TreeMap<String, ArrayList<String>> wordMap = new TreeMap<>();

        readResults(wordMap, inputStream);

        printResults(wordMap);


    }
}

