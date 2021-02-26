# Fragments

<p>Треба да се развие генеричка класа за работа со дропки. Класата <code>GenericFraction</code> има два генерички параметри <code>T</code> и <code>U</code> кои мора да бидат од некоја класа која наследува од класата <code>Number</code>. <code>GenericFraction</code> има две променливи:</p>

<ul>
<li>numerator - броител</li>
<li>denominator - именител.</li>
</ul>

<p>Треба да се имплементираат следните методи:</p>

<ul>
<li><code>GenericFraction(T numerator, U denominator)</code> - конструктор кој ги иницијализира броителот и именителот на дропката. Ако се обидиме да иницијализираме дропка со 0 вредност за именителот треба да се фрли исклучок од тип <code>ZeroDenominatorException</code> 	</li>
<li><code>GenericFraction&lt;Double, Double&gt; add(GenericFraction&lt;? extends Number, ? extends Number&gt; gf)</code> - собирање на две дропки</li>
<li><code>double toDouble()</code> - враќа вредност на дропката како реален број</li>
<li><code>toString():String</code> - ја печати дропката во следниот формат <code>[numerator] / [denominator]</code>, скратена (нормализирана) и секој со две децимални места.</li>
</ul>