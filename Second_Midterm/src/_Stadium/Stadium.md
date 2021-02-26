# Stadium

<p>Да се имплементира систем за билети за стадион. За таа цел треба да се имплементираат класите:</p>

<ol>
<li><code>Sector</code> во која се чуват информации за:<ul>
<li>кодот на секторот <code>String</code></li>
<li>бројот на места за седење <code>int</code></li>
<li>информации за зафатеност на местата за седење <code>?</code></li>
</ul>
</li>
<li><code>Stadium</code> во која се чуваат информации за:<ul>
<li>името на стадионот <code>String</code></li>
<li>и сите сектори во стадионот <code>?</code></li>
</ul>
</li>
</ol>

<p>Во класата <code>Stadium</code> треба да се имплементираат следните методи:</p>

<ul>
<li><code>Stadium(String name)</code> конструктор со аргумент име на стадионот</li>
<li><code>void createSectors(String[] sectorNames, int[] sizes)</code> креирање на сектори со имиња <code>String[] sectorNames</code> и број на места <code>int[] sizes</code> (двете низи се со иста големина)</li>
<li><code>void buyTicket(String sectorName, int seat, int type)</code> за купување билет од проследениот тип (<code>type</code>, 0 - неутрален, 1 - домашен, 2 - гостински), во секторот <code>sectorName</code> со број на место <code>seat</code> (местото секогаш е со вредност во опсег <code>1 - size</code>). Ако местото е зафатено (претходно е купен билет на ова место) се фрла исклучок од вид <code>SeatTakenException</code>. Исто така ако се обидеме да купиме билет од тип 1, во сектор во кој веќе има купено билет од тип 2 (и обратно) се фрла исклучок од вид <code>SeatNotAllowedException</code>.</li>
<li><code>void showSectors()</code> ги печати сите сектори сортирани според бројот на слободни места во опаѓачки редослед (ако повеќе сектори имаат ист број на слободни места, се подредуваат според името).</li>
</ul>