# Integer Array

<p>Да се напише класа која чува низа на цели броеви <code>IntegerArray</code>. Класата треба да е <strong>immutable</strong>. Тоа значи дека, откако еднаш ќе се инстанцира да не може да се менува состојбата на објектот, односно да не може да се менуваат податоците зачувани во него и да не може да се наследува од неа <code>final</code>.
За потребите на класата треба да се имплементираат следните <code>public</code> методи:</p>

<ul>
<li><code>IntegerArray(int a[])</code> - конструктор кој прима низа од цели броеви</li>
<li><code>length():int</code> - метод кој ја враќа должината на низата</li>
<li><code>getElementAt(int i):int</code> - го враќа елементот на позиција <code>i</code>, може да претпоставите дека индекост <code>i</code> секогаш ќе има вредност во интервалот <code>[0,length()-1]</code></li>
<li><code>sum():int</code> -  метод кој ја враќа сумата на сите елемeнти во низата</li>
<li><code>average():double</code>  - метод кој ја враќа средната вредност на елементите во низата - аритметичка средина</li>
<li><code>getSorted():IntegerArray</code>- враќа нов објект од истата класа кој ги содржи истите вредности од тековниот објект, но сортирани во растечки редослед</li>
<li><code>concat(IntegerArray ia):IntegerArray</code> - враќа нов објект од истата класа во кој се содржат сите елементи од <code>this</code> објектот и по нив сите елементи од <code>ia</code> објектот притоа запазувајќи го нивниот редослед</li>
<li><code>toString():String</code> - враќа текстуална репрезентација на објектот каде елементите се одделени со запиркa и едно празно место после запирката и на почетокот и крајот на стрингот има средни загради. Пример за низа која ги содржи боревите 2,1 и 4 враќа "[2, 1, 4]".</li>
</ul>

<p>Покрај класата <code>IntegerArray</code> треба да напишете дополнително уште една класа која ќе служи за вчитување на низа од цели броеви од влезен тек на податоци. Оваа класа треба да се вика <code>ArrayReader</code> и во неа треба да имате еден <code>public static</code> метод за вчитување на низа од цели броеви од <code>InputStream</code>.</p>

<ul>
<li><code>readIntegerArray(InputStream input):IntegerArray</code> - вчитува низа од цели броеви од <code>input</code> зададена во следниот формат: Во првата линија има еден цел борј кој кажува колку елементи има во низата, а во наредниот ред се дадени елементите на низата одделени со едно или повеќе празни места. Помош, искористете ја класата <code>java.util.Scanner</code>.</li>
</ul>

<p>Секогаш кога работите со низи во Java можете да искористите дел од методите во класата Arrays.
За да пристапите до класата најпрвин треба да ја импортирате со следнава наредба</p>

<pre><code>import java.util.Arrays;
</code></pre>

<p>Во продолжение се дадени дел од методите кои можат да ви помогнат. За тоа како работат консултираје ја нивната документација.</p>

<p><a href="http://docs.oracle.com/javase/7/docs/api/java/util/Arrays.html#copyOf%28int%5B%5D,%20int%29"><code>copyOf(int[] original, int newLength)</code></a></p>

<p><a href="http://docs.oracle.com/javase/7/docs/api/java/util/Arrays.html#equals%28int%5B%5D,%20int%5B%5D%29"><code>equals(int[] a, int[] a2)</code></a></p>

<p><a href="http://docs.oracle.com/javase/7/docs/api/java/util/Arrays.html#sort%28int%5B%5D%29"><code>sort(int[] a)</code></a></p>