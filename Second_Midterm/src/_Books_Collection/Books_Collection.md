# Books Collection

<p>Да се напише класа за книга <strong>Book</strong> во која се чува:</p>

<ul>
<li>наслов</li>
<li>категорија</li>
<li>цена. </li>
</ul>

<p>Да се имплементира конструктор со следните аргументи <code>Book(String title, String category, float price)</code>.</p>

<p>Потоа да се напише класа <strong>BookCollection</strong> во која се чува колекција од книги. Во оваа класа треба да се имплментираат следните методи:</p>

<ul>
<li><code>public void addBook(Book book)</code> - додавање книга во колекцијата </li>
<li><code>public void printByCategory(String category)</code> - ги печати сите книги од проследената категорија (се споредува стрингот без разлика на мали и големи букви), сортирани според насловот на книгата (ако насловот е ист, се сортираат според цената). </li>
<li><code>public List&lt;Book&gt; getCheapestN(int n)</code> - враќа листа на најевтините N книги (ако има помалку од N книги во колекцијата, ги враќа сите).</li>
</ul>