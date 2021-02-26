# Faculty

<p>Да се имплементира апликација за евидентирање на оценките на студентите на еден факултет. Студентите на факултетот може да бидат запишани на <strong>тригодишни</strong> или <strong>четиригодишни</strong> студии. Во текот на студиите, студентите имаат <strong>два</strong> семестри во секоја година и во секој од семестрите имаат по најмногу <strong>3 предмети</strong>. За таа цел, дефинирајте класа <code>Faculty</code> во која што ќе чувате информации за студентите и нивните оценки во сите семестри. За класата да се имплементираат: </p>

<ul>
<li>Default конструктор <code>Faculty()</code></li>
<li>метод <code>void addStudent(String id, int yearsOfStudies)</code>- за додавање на студент на факултетот со индекс <code>ID</code> и години на студии <code>yearsOfStudies</code>.</li>
<li>метод <code>void addGradeToStudent(String studentId, int term, String courseName, int grade)</code> - за додавање на оценка <code>grade</code> по предметот <code>courseName</code> на студентот со индекс <code>studentId</code> во семестар <code>term</code>.<ul>
<li>Со помош на исклучок од тип <code>OperationNotAllowedException</code> да се спречи додавање на повеќе од 3 оценки по семестар. Во таков случај да се испечати порака од формат <code>Student [studentID] already has 3 grades in term [term]</code>. Со истиот тип на исклучок да се спречи додавање на оценка во семестар поголем од 6 за тригодишни студии односно во семестар поголем од 8 за четиригодишни студии. Во овој случај да се испечати порака <code>Term [term] is not possible for student with ID [studentId]</code>.</li>
<li>Да се детектира дипломирање на студентот. Студентот дипломира тогаш кога ќе положи 18 или 24 предмети во зависност од тоа колку години студира. Во моментот на дипломирање на студентот истиот треба да се избрише од евиденцијата и да се зачува лог за него во формат <code>Student with ID [studentID] graduated with average grade [averageGrade] in [yearsOfStudies] years</code>.</li>
</ul>
</li>
<li>Метод <code>String getFacultyLogs ()</code> - што ќе ги врати логовите за дипломираните студенти. </li>
<li>Метод <code>String getDetailedReportForStudent (String id)</code> - метод што ќе врати детален извештај студентот со индекс id. Пристапот до студентот со индекс ИД да има комплексност <code>О(1)</code>! Деталниот извештај е во формат: </li>
</ul>

<blockquote>
<pre><code>Student: [id]
Term 1: 
Courses for term: [count]
Average grade for term: [average]
…
…..
Term n:
Courses: [count]
Average grade for term: [average]
Average grade: [average grade for student]
Courses attended: [all_attended_courses, comma-separated, сортирани лексикографски]
</code></pre>
</blockquote>

<ul>
<li>Метод <code>void printFirstNStudents (int n)</code> - метод којшто ќе ги испечати краток извештај за најдобрите <code>n</code> студенти (според бројот на положени предмети, а доколку е ист бројот на положени предмети според просечната оценка ), сортирани во опаѓачки редослед. Краткиот извештај е во формат <code>Student: [id] Courses passed: [coursesPassed] Average grade: [averageGrade]</code>.</li>
<li>Метод <code>void printCourses ()</code> - метод којшто ќе ги испечати сите предмети во формат <code>[course_name] [count_of_students] [average_grade]</code> на факултетот сортирани според бројот на слушатели на предметот, а доколку е ист според просечната оценка.</li>
</ul>