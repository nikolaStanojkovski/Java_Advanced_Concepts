# File System

<p>Да се имплементира класа <code>FileSystem</code> за едноставен податочен систем. За вашиот податочен систем треба да имплементирате сопствена класа за датотека <code>File</code> со податоци за име (<code>String</code>), големина (<code>Integer</code>) и време на креирање (<code>LocalDateTime</code>) Класата треба да ги овозможува следните функционалности:</p>

<ul>
<li><code>public void addFile(char folder, String name, int size, LocalDateTime createdAt)</code> - метод за додавање нова датотека <code>File</code> во фолдер со даденото име (името на фолдерот е еден знак, може да биде <code>.</code> или голема буква)</li>
<li><code>public List&lt;File&gt; findAllHiddenFilesWithSizeLessThen(int size)</code> - враќа листа на сите скриени датотеки (тоа се датотеки чие што име започнува со знакот за точка <code>.</code>) со големина помала од <code>size</code>.</li>
<li><code>public int totalSizeOfFilesFromFolders(List&lt;Character&gt; folders)</code> - враќа вкупна големина на сите датотеки кои се наоѓаат во фолдерите кои се зададени во листата <code>folders</code></li>
<li><code>public Map&lt;Integer, Set&lt;File&gt;&gt; byYear()</code> - враќа мапа <code>Map</code> во која за датотеките се групирани според годината на креирање.</li>
<li><code>public Map&lt;String, Long&gt; sizeByMonthAndDay()</code> - враќа мапа <code>Map</code> во која за секој месец и ден (независно од годината) се пресметува вкупната големина на сите датотеки креирани во тој месец и тој ден. Месецот се добива со повик на методот <code>getMonth()</code>, а денот <code>getDayOfMonth()</code>.</li>
</ul>

<p>Датотеките во секој фолдер се подредени според датумот на креирање во растечки редослед, потоа според името лексикографски и на крај според големината. Да се имплементира ваков компаратор во самата класа <code>File</code>. Исто така да се имплементира и <code>toString</code> репрезентација во следниот формат:</p>

<p><code>%-10[name] %5[size]B %[createdAt]</code></p>