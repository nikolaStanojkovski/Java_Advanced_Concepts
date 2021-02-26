# Pizza Order

<p>Треба да се развие систем за електронска нарачка од пицерија. Менито на пицеријата се состои од следново:</p>

<ul>
<li><p>Pizza:</p>
<ul>
<li>Standard: 10<span>$</span>  </li>
<li>Pepperoni: 12<span>$</span></li>
<li>Vegetarian: 8<span>$</span>  </li>
</ul>
</li>
<li><p>Extra</p>
<ul>
<li>Ketchup 3<span>$</span></li>
<li>Coke 5<span>$</span></li>
</ul>
</li>
</ul>

<p>За да го претставите менито, секоја ставка треба да имплементира <code>interface Item</code> која опишува една ставка од менито и ги дефинира следниве методи:</p>

<ul>
<li><code>getPrice():int</code> - ја дава цената за конкретната ставка</li>
</ul>

<p>Следно, дефинирајте две класи <code>ExtraItem</code> и <code>PizzaItem</code> за да може да правите разлика меѓу пици и останатите работи во нарачката. И двете класи треба да имаат еден конструктор кој прима еден <code>String</code> аргумент. </p>

<ul>
<li><code>ExtraItem(String type)</code> - валидни вредности за <code>type</code> се <code>{ "Coke", "Ketchup" }</code>                  </li>
<li><code>PizzaItem(String type)</code> - валидни вредности за <code>type</code> се <code>{ Standard , Pepperoni , Vegetarian }</code>                  </li>
</ul>

<p>Ако за <code>type</code> се проследи некоја невалидна вредност (која ја нема на менито) треба да се фрли исклучок <code>InvalidExtraTypeException</code>, односно <code>InvalidPizzaTypeException</code>.</p>

<p>Последно имплементирајте ја класата <code>Order</code>. Таа треба да ги нуди следните функционалности:</p>

<ul>
<li><code>Order()</code> - креира нова празна нарачка</li>
<li><p><code>addItem(Item item, int count)</code> - соодветната ставка се додава во нарачката (<code>count</code> означува колку примероци сакаме од дадената ставка). Aко <code>count</code> е поголем од 10 се фрла исклучок <code>ItemOutOfStockException(item)</code>. Доколку во нарачката веќе ја има соодветната ставка <code>Item</code> тогаш истата се заменува со нова. Следниот код резултира со нарачка со една стандардна пица:</p>
<p>             Order order = new Order();
order.addItem(new PizzaItem("Standard"), 2); 
order.addItem(new PizzaItem("Standard"), 1);</p>
</li>
<li><p><code>getPrice():int</code> - ја враќа вкупната цена на нарачката</p>
</li>
<li><code>displayOrder()</code> - ја печати содржината на нарачката со соодветни редни броеви пред секоја ставка, името, количината и збирна сума на ставката, како и вкупна сума за целата нарачка. За редниот број се резервирани 3 места порамнети во десно, за имињата на ставките се резервирани 15 места со порамнување од лево, за кардиналноста две места порамнети во десно и за цената на една ставка 5 места порамнети во десно. За "Total:" се резервирани 22 места со порамнување од лево и за вкупната цена 5 места порамнети во десно. Пример:
<pre>  1.Standard       x 2   20$
  2.Vegetarian     x 1    8$
  3.Coke           x 3   15$
Total:                   43$
</pre></li>
</ul>

<p>Редоследот по кој се печатат ставките е оној по кој тие се внесувани во нарачката. Доколку некоја ставка се внесе повторно нејзиното место не се менува.          </p>

<ul>
<li><code>removeItem(int idx)</code> - се отстранува нарачката со даден индекс (сите нарачки со поголеми индекси се поместуваат во лево). Доколку не постои нарачка со таков индекс треба да се фрли исклучок <code>ArrayIndexOutOfBоundsException(idx)</code></li>
<li><code>lock()</code> - ја заклучува нарачката. За да може нарачката да се заклучи треба истата да има барем една ставка, во спротивно фрлете исклучок <code>EmptyOrderException</code>.</li>
</ul>

<p>Откако ќе се заклучи нарачката треба веќе да не може да се менува со методите <code>removeItem</code>, <code>addItem</code>. Повикот на овие методи резултира со исклучок од типот <code>OrderLockedException</code>.</p>