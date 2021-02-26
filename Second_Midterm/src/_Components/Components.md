# Components

<p>Да се дефинира класа <code>Component</code> во која се чуваат:</p>

<ul>
<li>бојата</li>
<li>тежината</li>
<li>колекција од внатрешни компоненти (референци од класата <code>Component</code>).</li>
</ul>

<p>Во оваа класа да се дефинираат методите:</p>

<ul>
<li><code>Component(String color, int weight)</code> - конструктор со аргументи боја и тежина</li>
<li><code>void addComponent(Component component)</code> - за додавање нова компонента во внатрешната колекција (во оваа колекција компонентите секогаш се подредени според тежината во растечки редослед, ако имаат иста тежина подредени се алфабетски според бојата).</li>
</ul>

<p>Да се дефинира класа <code>Window</code> во која се чуваат:</p>

<ul>
<li>име</li>
<li>компоненти.</li>
</ul>

<p>Во оваа класа да се дефинираат следните методи:</p>

<ul>
<li><code>Window(String)</code> - конструктор</li>
<li><code>void addComponent(int position, Component component)</code> - додава нова компонента на дадена позиција (цел број). На секоја позиција може да има само една компонента, ако се обидеме да додадеме компонента на зафатена позиција треба да се фрли исклучок од класата <code>InvalidPositionException</code> со порака <code>Invalid position [pos], alredy taken!</code>. Компонентите се подредени во растечки редослед според позицијата.</li>
<li> <code>String toString()</code> - враќа стринг репрезентација на објектот (дадена во пример излезот)</li>
<li><code>void changeColor(int weight, String color)</code> - ја менува бојата на сите компоненти со тежина помала од проследената</li>
<li><code>void swichComponents(int pos1, int pos2)</code> - ги заменува компонените од проследените позиции.</li>
</ul>