# XML

<p><code>XML</code> форматот е еден од најупотребуваните формати за полуструктурирани податоци. Потребно е со помош на <code>Composite</code> шаблонот за развој на софтвер да дефинирате класи што ќе овозможат претставување на едноставни и посложени XML елементи. </p>

<p>Еден XML елемент е претставен на следниот начин: </p>

<pre><code>&lt;tag attribute1="value1" attribute2="value2", ...&gt; value &lt;/tag&gt;

</code></pre>

<p>пример: </p>

<pre><code>&lt;student type="redoven" smer="KNI"&gt; Trajce Trajkov &lt;/student&gt;
</code></pre>

<p>Но XML може да има и посложена структура, односно наместо вредност (value) да содржи други XML елементи. Тие XML (под)елементи може да содржат други XML елементи итн. Пример:</p>

<pre><code>&lt;student type="redoven"&gt;
    &lt;name&gt;
        &lt;first-name&gt;Trajce&lt;/first-name&gt;
        &lt;last-name&gt;Trajkov&lt;/last-name&gt;
    &lt;/name&gt;
&lt;/student&gt;
</code></pre>

<p>За таа цел потребно е да дефинирате интерфејст <code>XMLComponent</code> и од истиот да креирате две класи <code>XMLLeaf</code> и <code>XMLComposite</code>. </p>

<p>Да се дополни главната класа според барањето во коментарите.</p>