# Super String

<p>Треба да се напише класа <strong>SuperString</strong>. Класата во позадина претставува листа на стрингови <code>LinkedList&lt;String&gt;</code> и ги нуди следните методи:</p>

<ul>
<li><code>SuperString()</code> - креира празен стринг</li>
<li><code>append(String s)</code> - го додава стрингот на крајот во листата</li>
<li><code>insert(String s)</code> - го додава стрингот на почеток на листата</li>
<li><code>contains(String s):boolean</code> - враќа <code>true</code> доколку стрингот <code>s</code> се наоѓа во супер-стрингот. Стрингот <code>s</code> може да е разделен во повеќе подстрингови во листата. Пр: <code>list = [ "st" , "arz" , "andrej" ] , contains("tarzan") –&gt; true</code></li>
<li><code>reverse()</code> - го превртува стрингот на следниов начин. Ги превртува сите елементи во листата, а потоа и секој подстринг како елемент  посебно го превртува.
<code>list = [ "st" , "arz" , "andrej: ]; reverse(); list = [ "jerdna", "zra", "ts"]</code></li>
<li><code>toString():String</code> - ги враќа конкатенирани сите елементи во листата <code>list = [ "st" , "arz" , "andrej"];  toString() -&gt;  "starzandrej"</code></li>
<li><code>removeLast(int k)</code> – ги отстранува <strong>последнo додадените</strong> <code>k</code> подстрингови</li>
</ul>