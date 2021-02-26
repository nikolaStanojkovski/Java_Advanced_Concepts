package _Labaratoriski_Vezhbi;

import java.util.*;
import java.util.stream.Collectors;

class Student {
    private String index;
    List<Integer> points;

    public Student(String index, List<Integer> points) {
        this.index = index;
        this.points = points;
    }

    public String getIndex() {
        return index;
    }

    public boolean isFailed() {
        return points.size() < 8;
    }

    public double averagePoints() {
        if(points.size() == 0)
            return 0;
        return this.points.stream().mapToDouble(Integer::doubleValue)
                .sum() / 10.0;
    }

    @Override
    public String toString() {
        StringBuilder yesNo = new StringBuilder();
        if(this.isFailed())
            yesNo.append("NO");
        else
            yesNo.append("YES");
        return String.format("%s %s %.2f", this.getIndex(),
                yesNo,this.averagePoints());
    }
}

// 135042 NO 0.00

class LabExercises {
    private List<Student> students;

    public LabExercises() {
        this.students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void printByAveragePoints(boolean ascending, int n) {
        if(ascending)
            this.students.stream().sorted(Comparator.comparing(Student::averagePoints)
                    .thenComparing(Student::getIndex)).limit(n)
                    .forEach(System.out::println);
        else
            this.students.stream().sorted(Comparator.comparing(Student::averagePoints)
                    .thenComparing(Student::getIndex).reversed())
                    .limit(n).forEach(System.out::println);
    }

    public List<Student> failedStudents() {
        return this.students.stream().filter(Student::isFailed)
                .sorted(Comparator.comparing(Student::getIndex)
                .thenComparing(Student::averagePoints))
                .collect(Collectors.toList());
    }
//
    public Map<Integer, Double> getStatisticsByYear() {
        return this.students.stream().filter(i -> !i.isFailed())
                .collect(Collectors.groupingBy(
                        i -> 10 - Integer.parseInt(String.valueOf(i.getIndex().charAt(1))),
                        TreeMap::new,
                        Collectors.averagingDouble(Student::averagePoints)
                ));
    }
}

public class LabExercisesTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LabExercises labExercises = new LabExercises();
        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            String[] parts = input.split("\\s+");
            String index = parts[0];
            List<Integer> points = Arrays.stream(parts).skip(1)
                    .mapToInt(Integer::parseInt)
                    .boxed()
                    .collect(Collectors.toList());

            labExercises.addStudent(new Student(index, points));
        }

        System.out.println("===printByAveragePoints (ascending)===");
        labExercises.printByAveragePoints(true, 100);
        System.out.println("===printByAveragePoints (descending)===");
        labExercises.printByAveragePoints(false, 100);
        System.out.println("===failed students===");
        labExercises.failedStudents().forEach(System.out::println);
        System.out.println("===statistics by year");
        labExercises.getStatisticsByYear().entrySet().stream()
                .map(entry -> String.format("%d : %.2f", entry.getKey(), entry.getValue()))
                .forEach(System.out::println);

    }
}
