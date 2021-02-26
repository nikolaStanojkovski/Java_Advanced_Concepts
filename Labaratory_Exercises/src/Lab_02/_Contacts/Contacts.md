# Contacts

<p>Се со цел да се подобри комуникацијата на факултетот потребно е да се направи систем за чување на контакти за секој студент.</p>

<p>Да се креира класа <code>Contact</code>. За потребите на оваа класа да се дефинираат следниве методи:</p>

<ul>
<li><code>Contact(String date)</code> - конструктор каде што <code>date</code> е датумот кога е креиран контактот даден во следниов формат <code>YYYY-MM-DD</code></li>
<li><code>isNewerThan(Contact c):boolean</code> - метод кој враќа <code>true</code> доколку контактот е креиран подоцна од контактот <code>c</code> и обратно</li>
<li><code>getType():String</code> - метод кој враќа вредност "Email" или "Phone" во зависност од типот на контактот</li>
</ul>

<p>Од класата <code>Contact</code> не треба да може директно да се инстанцира објект.</p>

<p>Од оваа класа се изведуваат класите <code>EmailContact</code> и <code>PhoneContact</code>.</p>

<p>За класата <code>EmailContact</code> дополнително се чува e-маил кој што е од типот <code>String</code>. Да се дефинираат следниве методи:</p>

<ul>
<li><code>EmailContact(String date, String email)</code> - конструктор</li>
<li><code>getEmail():String</code> - метод кој што го враќа е-маилот</li>
<li><code>getType():String</code>- имплементација на методот од класата Contact</li>
</ul>

<p>За класата <code>PhoneContact</code> дополнително се чува телефонски број кој што е од типот <code>String</code> и оператор кој што е енумерација и се дефинира на следниов начин <code>enum Operator { VIP, ONE, TMOBILE }</code>. За оваа класа да се дефинираат следниве методи:</p>

<ul>
<li><code>PhoneContact(String date, String phone)</code> - конструктор</li>
<li><code>getPhone():String</code> - метод кој што го враќа телефонскиот број</li>
<li><code>getOperator():Operator</code> - метод кој што го враќа операторот (070, 071, 072 – TMOBILE, 075,076 – ONE, 077, 078 – VIP)</li>
<li><code>getType():String</code>- имплементација на методот од класата Contact</li>
</ul>

<p>*Забелешка: Сите телефонски броеви се во формат <code>07X/YYY-ZZZ</code> каде што <code>X</code> има вредност <code>{0,1,2,5,6,7,8}</code></p>

<p>Потоа да се дефинира класата <code>Student</code> каде што се чува низа на контакти за секој студент</p>

<ul>
<li><code>Student(String firstName, String lastName, String city, int age, long index)</code> – конструктор</li>
<li> <code>addEmailContact(String date, String email):void</code> – метод што додава е-маил контакт во низата на контакти</li>
<li> <code>addPhoneContact(String date, String phone):void</code> – метод што додава телефонски контакт во низата на контакти</li>
<li><code>getEmailContacts():Contact[]</code> – враќа низа на <code>email</code> контактите на студентот</li>
<li><code>getPhoneContacts():Contact[]</code> – враќа низа на <code>phone</code> контактите на студентот</li>
<li><code>getCity():String</code> - метод кој го враќа градот</li>
<li><code>getFullName():String</code> - метод кој го враќа целосното име на студентот во формат <code>IME PREZIME</code></li>
<li><code>getIndex():long</code> - метод кој го враќа индексот на студентот</li>
<li><code>getLatestContact():Contact</code> – го враќа најновиот контакт (според датум) од студентот</li>
<li><code>toString()</code> – претставува <code>JSON</code> репрезентација на класата студент пр. <code>{"ime":"Jovan", "prezime":"Jovanov", "vozrast":20, "grad":"Skopje", "indeks":101010, </code> <code>"telefonskiKontakti":["077/777-777", "078/888-888"], "emailKontakti":["jovan.jovanov@example.com", "jovanov@jovan.com", "jovan@jovanov.com"]}</code></li>
</ul>

<p>*Забелешка: Во класата <code>Student</code> да се чува само една низа од контакти <code>Contact[]</code>, а не две низи одделно (<code>PhoneContact[]</code> и <code>EmailContact[]</code>)</p>

<p>*Напомена да не се користи <code>instanceOf</code> или <code>getClass</code> при имплементација на овие методи</p>

<p>Дополнително да се дефинира класа <code>Faculty</code>. За оваа класа да се дефинираат следниве методи:</p>

<ul>
<li><code>Faculty(String name, Student [] students)</code> – конструктор</li>
<li><code>countStudentsFromCity(String cityName):int</code> – враќа колку студенти има од даден град</li>
<li><code>getStudent(long index):Student</code> – метод кој го враќа студентот кој го има дадениот индекс</li>
<li><code>getAverageNumberOfContacts():double</code> – враќа просечен број на контакти по студент</li>
<li><code>getStudentWithMostContacts():Student</code> – метод кој го враќа студентот со најмногу контакти (доколку има повеќе студенти со ист број на контакти да го врати студентот со најголем индекс)</li>
<li><code>toString()</code> – претставува <code>JSON</code> репрезентација на класата <code>Faculty</code> пример: <code>{"fakultet":"FINKI", "studenti":[STUDENT1, STUDENT2, ...]}</code>  каде што треба да има целосни информации за секој студент.</li>
</ul>