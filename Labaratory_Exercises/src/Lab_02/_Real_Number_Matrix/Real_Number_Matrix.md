# Real Number Matrix

<p>Да се напише класа која чува матрица од <code>double</code> вредности <code>DoubleMatrix</code>. Класата треба да е <code>immutable</code>, односно откако еднаш ќе се инстанцира да не може да се менува состојбата на објектот, односно да не може да се менуваат податоците зачувани во него. За потребите на класата треба да се имплементираат следните методи:</p>

<ul>
<li><code>DoubleMatrix(double a[], int m, int n)</code> - конструктор кој прима низа од реални броеви каде што <code>m</code> и <code>n</code> се димензиите на матрицата. Од елементите на низата треба да се креира матрица. Доколку нема доволно елементи во низата тогаш да се фрли исклучок <code>InsufficientElementsException</code>, а доколку има повеќе елементи да се земат последните <code>m x n</code> вредности и со нив се потполнува матрицата, т.е. да се игнорираат вишокот на броеви од почетокот на низата</li>
<li><code>getDimensions():String</code> - метод кој враќа стринг во формат <code>[m x n]</code></li>
<li><code>rows():int</code> - метод кој враќа број на редови</li>
<li><code>columns():int</code> - метод кој враќа број на колони</li>
<li><code>maxElementAtRow(int row):double</code>- метод кој го враќа максималниот елемент во дадениот ред, доколку вредноста е ред кој не постои да се фрли исклучок <code>InvalidRowNumberException</code> (row има вредност [1, m])</li>
<li><code>maxElementAtColumn(int column):double</code>- метод кој го враќа максималниот елемент во дадената колона, доколку вредноста е колона кој не постои да се фрли исклучок <code>InvalidColumnNumberException</code> (column има вредност [1, n])</li>
<li><code> sum() : double</code> - метод кој ја враќа сумата на сите елементи од матрицата</li>
<li><code>toSortedArray():double[]</code> – метод кој враќа еднодимензионална низа
каде вредностите се сортирани во опаѓачки редослед</li>
<li><code>toString()</code> - методот, каде броевите се заокружени на 2 децимални
места, меѓу себе се одделени со табулаторско место <code>\t</code> а редовите на
матрицата се одделени со нов ред</li>
<li>да се преоптоварат <code>equals()</code> и <code>hashCode()</code> методите</li>
</ul>

<p><strong>Забелешка</strong>: Исклучоците не треба да се фаќаат, треба само да се фрлаат</p>

<p>Да се дефинира класа<code>InsufficientElementsException</code> која што наследува од класата <code>Exception</code> и при фрлање на исклучок се добива порака<code>"Insufficient number of elements"</code></p>

<p>Да се дефинира класа <code>InvalidRowNumberException</code>која што наследува од класата <code>Exception</code> и при фрлање на исклучок се добива порака <code>"Invalid row number"</code></p>

<p>Да се дефинира класа <code>InvalidColumnNumberException</code> која што наследува од класата <code>Exception</code> и при фрлање на исклучок се добива порака <code>"Invalid column number"</code></p>

<p>Покрај класата <code>DoubleMatrix</code> треба да напишете дополнително уште една класа која ќе служи за вчитување на матрица од реални броеви од влезен тек на податоци. Оваа класа треба да се вика <code>MatrixReader</code> и во неа треба да имате еден <code>public static</code> метод за вчитување на матрица од реални броеви од <code>InputStream</code></p>

<ul>
<li><code>read(InputStream input):DoubleMatrix</code> - вчитува матрица од реални броеви од <code>input</code> зададена во следниот формат: Во првата линија има два цели броеви кои кажуваат колку редови и колони има матрицата, а во наредните редови се дадени елементите на матрицата по редови, одделени со едно или повеќе празни места</li>
</ul>