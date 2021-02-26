package _File_System;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

class File implements Comparable<File> {

    private String name;
    private Integer size;
    private LocalDateTime timestamp;

    public File(String name, Integer size, LocalDateTime timestamp) {
        this.name = name;
        this.size = size;
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public Integer getSize() {
        return size;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public int compareTo(File o) {
        int res = this.getTimestamp().compareTo(o.getTimestamp());
        if(res == 0) {
            int res2 = this.getName().compareTo(o.getName());
            if(res2 == 0)
                return Integer.compare(this.getSize(), o.getSize());
            else
                return res2;
        } else return res;
    }

    @Override
    public String toString() {
        return String.format("%-10s %5dB %s", this.getName(),
                this.getSize(), this.getTimestamp());
    }
}

class FileSystem {

    private Map<Character, Set<File>> files;

    public FileSystem() {
        this.files = new HashMap<>();
    }

    public void addFile(char folder, String name, int size, LocalDateTime createdAt) {
        File f = new File(name, size, createdAt);
        
        this.files.putIfAbsent(folder, new TreeSet<>());
        this.files.get(folder).add(f);
    }

    public List<File> findAllHiddenFilesWithSizeLessThen(int size) {
        return this.files.entrySet().stream()
                .flatMap(i -> i.getValue().stream())
                .filter(i -> i.getName().charAt(0) == '.' &&
                        i.getSize() < size)
                .collect(Collectors.toList());
    }

    public int totalSizeOfFilesFromFolders(List<Character> folders) {
        return (int) this.files.entrySet().stream().filter(i ->
                folders.contains(i.getKey())).flatMap(i -> i.getValue().stream())
                .mapToDouble(File::getSize).sum();
    }

    public Map<String, Long> sizeByMonthAndDay() {
        return this.files.entrySet().stream().flatMap(i -> i.getValue().stream())
                .collect(Collectors.groupingBy(
                        i -> String.format("%s-%d", i.getTimestamp().getMonth(),
                                i.getTimestamp().getDayOfMonth()),
                        TreeMap::new,
                        Collectors.summingLong(File::getSize)
                ));
    }

    public Map<Integer, Set<File>> byYear() {
        return this.files.entrySet().stream().flatMap(i -> i.getValue().stream())
            .collect(Collectors.groupingBy(
                i -> i.getTimestamp().getYear(),
                Collectors.toCollection(TreeSet::new)
            ));
    }
}

public class FileSystemTest {
    public static void main(String[] args) {
        FileSystem fileSystem = new FileSystem();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            fileSystem.addFile(parts[0].charAt(0), parts[1],
                    Integer.parseInt(parts[2]),
                    LocalDateTime.of(2016, 12, 29, 0, 0, 0).minusDays(Integer.parseInt(parts[3]))
            );
        }
        int action = scanner.nextInt();
        if (action == 0) {
            scanner.nextLine();
            int size = scanner.nextInt();
            System.out.println("== Find all hidden files with size less then " + size);
            List<File> files = fileSystem.findAllHiddenFilesWithSizeLessThen(size);
            files.forEach(System.out::println);
        } else if (action == 1) {
            scanner.nextLine();
            String[] parts = scanner.nextLine().split(":");
            System.out.println("== Total size of files from folders: " + Arrays.toString(parts));
            int totalSize = fileSystem.totalSizeOfFilesFromFolders(Arrays.stream(parts)
                    .map(s -> s.charAt(0))
                    .collect(Collectors.toList()));
            System.out.println(totalSize);
        } else if (action == 2) {
            System.out.println("== Files by year");
            Map<Integer, Set<File>> byYear = fileSystem.byYear();
            byYear.keySet().stream().sorted()
                    .forEach(key -> {
                        System.out.printf("Year: %d\n", key);
                        Set<File> files = byYear.get(key);
                        files.stream()
                                .sorted()
                                .forEach(System.out::println);
                    });
        } else if (action == 3) {
            System.out.println("== Size by month and day");
            Map<String, Long> byMonthAndDay = fileSystem.sizeByMonthAndDay();
            byMonthAndDay.keySet().stream().sorted()
                    .forEach(key -> System.out.printf("%s -> %d\n", key, byMonthAndDay.get(key)));
        }
        scanner.close();
    }
}