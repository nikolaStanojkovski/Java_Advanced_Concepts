# My DDV

<p>Да се имплементира класа <code>MojDDV</code> која што од влезен тек ќе чита информации за скенирани фискални сметки од страна на еден корисник на истоимената апликација. Податоците за фискалните сметки се во следниот формат: </p>

<p><code>ID item_price1 item_tax_type1 item_price2 item_tax_type2 … item_price-n item_tax_type-n </code></p>

<p>На пример:
<code>12334 1789 А 1238 B 1222 V 111 V</code></p>

<p>Постојат три типа на данок на додадена вредност и тоа:</p>

<ul>
<li><code>А</code> (18% од вредноста)</li>
<li><code>B</code> (5% од вредноста)</li>
<li><code>V</code> (0% од вредноста)</li>
</ul>

<p>Повратокот на ДДВ изнесува 15% од данокот на додадената вредност за артикалот.</p>

<p>Да се имплементираат методите:</p>

<ul>
<li><code>void readRecords (InputStream inputStream) </code>- метод којшто ги чита од влезен тек податоците за фискалните сметки. Доколку е скенирана фискална сметка со износ поголем од 30000 денари потребно е да се фрли исклучок од тип <code>AmountNotAllowedException</code>. Дефинирајте каде ќе се фрла исклучокот, и каде ќе биде фатен, на начин што оваа функција, ќе може да ги прочита сите фискални коишто се скенирани.  Исклучокот треба да испечати порака <code>“Receipt with amount [сума на сите артикли] is not allowed to be scanned”</code>. </li>
<li><code>void printTaxReturns (OutputStream outputStream)</code> - метод којшто на излезен тек ги печати сите скенирани фискални сметки во формат <code>ID SUM_OF_AMOUNTS TAX_RETURN</code>, каде што <code>SUM_OF_AMOUNTS</code> e збирот на сите артикли во фискалната сметка, а <code>TAX_RETURN</code> е пресметаниот повраток на ДДВ за таа фискална сметка.</li>
<li><code>void printStatistics (OutputStream outputStream)</code> - метод којшто на излезен тек печати статистики за повратокот на ДДВ од сите скенирани фискални сметки во формат <code>min: MIN max: MAX sum: SUM count: COUNT average: AVERAGE</code>, при што секоја од статистиките е во нов ред, а пак вредноста на статистиката е оддалечена со таб од името на статистиката (погледнете тест пример). Децималните вредности се печатат со 5 места, од кои 3 се за цифрите после децималата. Целите вредности се пишуваат со 5 места (порамнети на лево). </li>
<li>печатењето на вредностите во методот <code>printTaxReturns</code> се врши на тој начин што:<ul>
<li>сите информации се одвоени со таб</li>
<li>id-то i сумата на фискалната сметка се печатат со 10 места</li>
<li>повратокот на ДДВ со 10 места, од кои 5 се за цифрите после децималата.</li>
</ul>
</li>
</ul>