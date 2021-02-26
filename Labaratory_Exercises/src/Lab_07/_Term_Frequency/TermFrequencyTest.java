package Lab_07._Term_Frequency;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class TermFrequencyTest {
    public static void main(String[] args) throws FileNotFoundException {
        String[] stop = new String[] { "во", "и", "се", "за", "ќе", "да", "од",
                "ги", "е", "со", "не", "тоа", "кои", "до", "го", "или", "дека",
                "што", "на", "а", "но", "кој", "ја" };
        TermFrequency tf = new TermFrequency(System.in,
                stop);
        System.out.println(tf.countTotal());
        System.out.println(tf.countDistinct());
        System.out.println(tf.mostOften(10));
    }
}

class TermFrequency {
    Map<String, Integer> frequency;
    Set<String> stop;

    public TermFrequency(InputStream inputStream, String[] stopWords) {
        frequency = new TreeMap<>();
        stop = new HashSet<>();
        stop.addAll(Arrays.asList(stopWords));
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            line = line.trim();
            if (line.length() > 0) {
                String[] words = line.split(" ");
                for (String word : words) {
                    String key = normalize(word);
                    if (key.isEmpty() || stop.contains(key)) {
                        continue;
                    }
                    if (frequency.containsKey(key)) {
                        Integer count = frequency.get(key);
                        frequency.put(key, count + 1);
                    } else {
                        frequency.put(key, 1);
                    }
                }
            }
        }
        scanner.close();
    }

    private static String normalize(String word) {
        return word.toLowerCase().replace(",", "").replace(".", "").trim();
    }

    public int countTotal() {
        int total = 0;
        for (Integer count : frequency.values()) {
            total += count;
        }
        return total;
    }

    public int countDistinct() {
        return frequency.keySet().size();
    }

    public List<String> mostOften(int k) {
        //todo: implement method

        return this.frequency.entrySet().stream().sorted(Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Entry::getKey).limit(k).collect(Collectors.toList());
    }
}
