package _Faculty;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class OperationNotAllowedException extends Exception {

    public OperationNotAllowedException(String message) {
        super(message);
    }

}

class Student implements Comparable<Student> {
    private final String id;
    private final Integer yearOfStudies;
    private int[] grades;
    private long noPassed;

    public Student(String id, Integer yearOfStudies) {
        this.id = id;
        this.yearOfStudies = yearOfStudies;
        this.noPassed = 0;
        this.grades = new int[0];
    }

    public void setGrades(int[] grades) {
        this.grades = grades;
    }

    public void setNoPassed(long noPassed) {
        this.noPassed = noPassed;
    }

    public String getId() {
        return id;
    }

    public Integer getYearOfStudies() {
        return yearOfStudies;
    }

    public long getNoPassed() {
        return noPassed;
    }

    public Double getAverageGrade() {
        if(grades.length == 0)
            return 5.00;
        return Arrays.stream(grades).average().getAsDouble();
    }

    @Override
    public String toString() {
        return String.format("Student: %s Courses passed: %d Average grade: %.2f", id,
                getNoPassed(), getAverageGrade());
    }

    @Override
    public int compareTo(Student o) {
        int result = Long.compare(this.getNoPassed(), o.getNoPassed());
        if(result == 0) {
            int result2 = this.getAverageGrade().compareTo(o.getAverageGrade());
            if(result2 == 0)
                return -this.getId().compareTo(o.getId());
            else
                return -result2;
        } else {
            return -result;
        }
    }
}

class Course implements Comparable<Course> {

    private final String courseName;
    private final Integer grade;
    private final List<Integer> grades;
    private final Set<String> currentStudents;

    public Course(String courseName, Integer grade) {
        this.courseName = courseName;
        this.grade = grade;
        this.grades = new ArrayList<>();
        this.currentStudents = new HashSet<>();
    }

    public String getCourseName() {
        return courseName;
    }

    public Integer getGrade() {
        return grade;
    }

    public void incrementNoStudents(String id, Course course) {
        if(!this.currentStudents.contains(id)) {
            this.currentStudents.add(id);
            this.grades.add(course.getGrade());
        }
    }

    public int getNoStudents() {
        return this.currentStudents.size();
    }

    public double getAverageGrade() {
        if(getNoStudents() == 0)
            return 5.00;
        return grades.stream().mapToDouble(Integer::doubleValue).average().getAsDouble();
    }

    @Override
    public String toString() {
        return String.format("%s %d %.2f", courseName, getNoStudents(), getAverageGrade());
    }

    @Override
    public int compareTo(Course o) {
        int result = Integer.compare(this.getNoStudents(), o.getNoStudents());
        if(result == 0) {
            int result2 = Double.compare(this.getAverageGrade(), o.getAverageGrade());
            return result2 == 0 ? this.getCourseName().
                    compareTo(o.getCourseName()) : result2;
        }
        else
            return result;
    }
}

class Faculty {

    private Map<String, Map<Integer, Set<Course>>> studentGrades;
    private Map<String, Student> students;
    private Set<Course> courseSetMap;

    private StringBuilder graduateLog;

    public Faculty() {
        this.studentGrades = new HashMap<>();
        this.students = new HashMap<>();
        this.graduateLog = new StringBuilder();
        this.courseSetMap = new HashSet<>();
    }

    void addStudent(String id, int yearsOfStudies) {
        Student student = new Student(id, yearsOfStudies);
        this.students.putIfAbsent(id, student);
        this.studentGrades.putIfAbsent(id, new TreeMap<>());
    }

    public double getAverageGrade(String studentId) {
        return studentGrades.get(studentId).values().stream().flatMap(Collection::stream)
                .mapToInt(Course::getGrade).average().getAsDouble();
    }

    public void graduateCheck(String studentId) {
        if((students.get(studentId).getYearOfStudies() == 3 &&studentGrades.get(studentId).size() == 6 &&
                studentGrades.get(studentId).values().stream().mapToLong(Set::size).sum() == 18) ||
                (students.get(studentId).getYearOfStudies() == 4 &&
                        studentGrades.get(studentId).size() == 8 &&
                        studentGrades.get(studentId).values().stream().mapToLong(Set::size).sum() == 24)) {
            this.graduateLog.append(String.format("Student with ID %s graduated with average grade %.2f in %d years.\n",
                    studentId, getAverageGrade(studentId), students.get(studentId).getYearOfStudies()));

            this.studentGrades.remove(studentId);
            this.students.remove(studentId);
        }
    }

    void addGradeToStudent(String studentId, int term, String courseName, int grade) throws OperationNotAllowedException {
        if(this.studentGrades.get(studentId).get(term) != null &&
                this.studentGrades.get(studentId).get(term).size() == 3)
            throw new OperationNotAllowedException("Student " + studentId + " already has 3 grades in term " + term);
        if((this.students.get(studentId).getYearOfStudies() == 3 &&
                term > 6) || (this.students.get(studentId).getYearOfStudies() == 4 &&
                    term > 8))
            throw new OperationNotAllowedException("Term " + term + " is not possible for student with ID " + studentId);

        Course course = new Course(courseName, grade);
        this.studentGrades.get(studentId).putIfAbsent(term, new HashSet<>());
        this.studentGrades.get(studentId).get(term).add(course);

        if(this.courseSetMap.stream().noneMatch(i -> i.getCourseName().
                equals(courseName)))
            this.courseSetMap.add(course);

        updateCourses();
        updateStudents();
        graduateCheck(studentId);
    }

    String getFacultyLogs() {
        return this.graduateLog.substring(0, graduateLog.length()-1);
    }

    String getStringPerTerm(int i, Map<Integer, Set<Course>> courses) {
        StringBuilder sb = new StringBuilder();
        sb.append("Term ").append(i).append("\n");
        if(courses.get(i) != null) {
            sb.append(String.format("Courses: %d\nAverage grade for term: %.2f\n",
                    courses.get(i).size(),courses.get(i).stream().
                            mapToInt(Course::getGrade).average().getAsDouble()));
        } else {
            sb.append("Courses: 0\nAverage grade for term: 5.00\n");
        }
        return sb.toString();
    }

    String getTerms(String id) {
        Map<Integer, Set<Course>> courses = this.studentGrades.get(id);
        StringBuilder sb = new StringBuilder();
        IntStream.range(1, 7).forEach(i -> sb.append(getStringPerTerm(i, courses)));
        if(this.students.get(id).getYearOfStudies() == 4) {
            IntStream.range(7, 9).forEach(i -> sb.append(getStringPerTerm(i, courses)));
        }
        return sb.substring(0, sb.length()-1);
    }

    String getDetailedReportForStudent(String id) {
        return String.format("Student: %s\n%s\nAverage grade: %.2f\nCourses attended: %s", id, getTerms(id), getAverageGrade(id),
                studentGrades.get(id).values().stream().flatMap(Collection::stream)
                        .sorted(Comparator.comparing(Course::getCourseName))
                        .map(i -> i.getCourseName() + "").collect(Collectors.joining(",")));

    }

    void updateStudents() {
        this.students.values().forEach(i -> {
            if(this.studentGrades.containsKey(i.getId())) {
                i.setGrades(this.studentGrades.get(i.getId()).values()
                        .stream().flatMap(Collection::stream).mapToInt(Course::getGrade)
                        .toArray());
                i.setNoPassed(this.studentGrades.get(i.getId()).values()
                        .stream().mapToLong(Collection::size).sum());
            }
        });
    }

    void updateCourses() {
        this.courseSetMap.forEach(i -> this.studentGrades.forEach((key, value) -> {
            if(value.values().stream().flatMap(Collection::stream)
                    .anyMatch(k -> k.getCourseName().equals(i.getCourseName()))) {
                i.incrementNoStudents(this.students.get(key).getId(), value.values().stream()
                        .flatMap(Collection::stream).filter(k -> k.getCourseName().equals(i.getCourseName()))
                        .findAny().get());
            }
        }));
    }

    void printFirstNStudents(int n) {
        this.students.values().stream().sorted()
                .limit(n).forEach(System.out::println);
    }

    void printCourses() {
        this.courseSetMap.stream().sorted().forEach(System.out::println);
    }
}

public class FacultyTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int testCase = sc.nextInt();

        if (testCase == 1) {
            System.out.println("TESTING addStudent AND printFirstNStudents");
            Faculty faculty = new Faculty();
            for (int i = 0; i < 10; i++) {
                faculty.addStudent("student" + i, (i % 2 == 0) ? 3 : 4);
            }
            faculty.printFirstNStudents(10);

        } else if (testCase == 2) {
            System.out.println("TESTING addGrade and exception");
            Faculty faculty = new Faculty();
            faculty.addStudent("123", 3);
            faculty.addStudent("1234", 4);
            try {
                faculty.addGradeToStudent("123", 7, "NP", 10);
            } catch (OperationNotAllowedException e) {
                System.out.println(e.getMessage());
            }
            try {
                faculty.addGradeToStudent("1234", 9, "NP", 8);
            } catch (OperationNotAllowedException e) {
                System.out.println(e.getMessage());
            }
        } else if (testCase == 3) {
            System.out.println("TESTING addGrade and exception");
            Faculty faculty = new Faculty();
            faculty.addStudent("123", 3);
            faculty.addStudent("1234", 4);
            for (int i = 0; i < 4; i++) {
                try {
                    faculty.addGradeToStudent("123", 1, "course" + i, 10);
                } catch (OperationNotAllowedException e) {
                    System.out.println(e.getMessage());
                }
            }
            for (int i = 0; i < 4; i++) {
                try {
                    faculty.addGradeToStudent("1234", 1, "course" + i, 10);
                } catch (OperationNotAllowedException e) {
                    System.out.println(e.getMessage());
                }
            }
        } else if (testCase == 4) {
            System.out.println("Testing addGrade for graduation");
            Faculty faculty = new Faculty();
            faculty.addStudent("123", 3);
            faculty.addStudent("1234", 4);
            int counter = 1;
            for (int i = 1; i <= 6; i++) {
                for (int j = 1; j <= 3; j++) {
                    try {
                        faculty.addGradeToStudent("123", i, "course" + counter, (i % 2 == 0) ? 7 : 8);
                    } catch (OperationNotAllowedException e) {
                        System.out.println(e.getMessage());
                    }
                    ++counter;
                }
            }
            counter = 1;
            for (int i = 1; i <= 8; i++) {
                for (int j = 1; j <= 3; j++) {
                    try {
                        faculty.addGradeToStudent("1234", i, "course" + counter, (j % 2 == 0) ? 7 : 10);
                    } catch (OperationNotAllowedException e) {
                        System.out.println(e.getMessage());
                    }
                    ++counter;
                }
            }
            System.out.println("LOGS");
            System.out.println(faculty.getFacultyLogs());
            System.out.println("PRINT STUDENTS (there shouldn't be anything after this line!");
            faculty.printFirstNStudents(2);
        } else if (testCase == 5 || testCase == 6 || testCase == 7) {
            System.out.println("Testing addGrade and printFirstNStudents (not graduated student)");
            Faculty faculty = new Faculty();
            for (int i = 1; i <= 10; i++) {
                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
                int courseCounter = 1;
                for (int j = 1; j < ((i % 2 == 1) ? 6 : 8); j++) {
                    for (int k = 1; k <= ((j % 2 == 1) ? 3 : 2); k++) {
                        try {
                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), i % 5 + 6);
                        } catch (OperationNotAllowedException e) {
                            System.out.println(e.getMessage());
                        }
                        ++courseCounter;
                    }
                }
            }
            if (testCase == 5)
                faculty.printFirstNStudents(10);
            else if (testCase == 6)
                faculty.printFirstNStudents(3);
            else
                faculty.printFirstNStudents(20);
        } else if (testCase == 8 || testCase == 9) {
            System.out.println("TESTING DETAILED REPORT");
            Faculty faculty = new Faculty();
            faculty.addStudent("student1", ((testCase == 8) ? 3 : 4));
            int grade = 6;
            int counterCounter = 1;
            for (int i = 1; i < ((testCase == 8) ? 6 : 8); i++) {
                for (int j = 1; j < 3; j++) {
                    try {
                        faculty.addGradeToStudent("student1", i, "course" + counterCounter, grade);
                    } catch (OperationNotAllowedException e) {
                        e.printStackTrace();
                    }
                    grade++;
                    if (grade == 10)
                        grade = 5;
                    ++counterCounter;
                }
            }
            System.out.println(faculty.getDetailedReportForStudent("student1"));
        } else if (testCase==10) {
            System.out.println("TESTING PRINT COURSES");
            Faculty faculty = new Faculty();
            for (int i = 1; i <= 10; i++) {
                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
                int courseCounter = 1;
                for (int j = 1; j < ((i % 2 == 1) ? 6 : 8); j++) {
                    for (int k = 1; k <= ((j % 2 == 1) ? 3 : 2); k++) {
                        int grade = sc.nextInt();
                        try {
                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), grade);
                        } catch (OperationNotAllowedException e) {
                            System.out.println(e.getMessage());
                        }
                        ++courseCounter;
                    }
                }
            }
            faculty.printCourses();
        } else if (testCase==11) {
            System.out.println("INTEGRATION TEST");
            Faculty faculty = new Faculty();
            for (int i = 1; i <= 10; i++) {
                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
                int courseCounter = 1;
                for (int j = 1; j <= ((i % 2 == 1) ? 6 : 8); j++) {
                    for (int k = 1; k <= ((j % 2 == 1) ? 2 : 3); k++) {
                        int grade = sc.nextInt();
                        try {
                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), grade);
                        } catch (OperationNotAllowedException e) {
                            System.out.println(e.getMessage());
                        }
                        ++courseCounter;
                    }
                }

            }

            for (int i=11;i<15;i++) {
                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
                int courseCounter = 1;
                for (int j = 1; j <= ((i % 2 == 1) ? 6 : 8); j++) {
                    for (int k = 1; k <= 3; k++) {
                        int grade = sc.nextInt();
                        try {
                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), grade);
                        } catch (OperationNotAllowedException e) {
                            System.out.println(e.getMessage());
                        }
                        ++courseCounter;
                    }
                }
            }
            System.out.println("LOGS");
            System.out.println(faculty.getFacultyLogs());
            System.out.println("DETAILED REPORT FOR STUDENT");
            System.out.println(faculty.getDetailedReportForStudent("student2"));
            try {
                System.out.println(faculty.getDetailedReportForStudent("student11"));
                System.out.println("The graduated students should be deleted!!!");
            } catch (NullPointerException e) {
                System.out.println("The graduated students are really deleted");
            }
            System.out.println("FIRST N STUDENTS");
            faculty.printFirstNStudents(10);
            System.out.println("COURSES");
            faculty.printCourses();
        }
    }
}