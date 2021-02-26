# Movable Objects

<p>Да се дефинира интерфејс <code>Movable</code> што ќе ги дефинира основните својства на еден движечки објект:</p>

<ul>
<li>движење нагоре (<code>void moveUp()</code>)</li>
<li>движење надолу (<code>void moveLeft()</code>)</li>
<li>движење надесно (<code>void moveRight()</code>)</li>
<li>движење налево (<code>void moveLeft()</code>)</li>
<li>пристап до моменталните <strong>x,y</strong> координати на објектот (<code>int getCurrentXPosition()</code>  и <code>int getCurrentYPosition()</code>).</li>
</ul>

<p>Постојат два типа на движечки објекти: движечка точка (<code>MovingPoint</code>) и движечки круг (<code>MovingCircle</code>). Да се дефинираат овие две класи коишто го имплементираат интерфејсот <code>Movable</code>. </p>

<p>Во класата <code>MovingPoint</code> се чуваат информации за: </p>

<ul>
<li>x и y координати (цели броеви)</li>
<li><code>xSpeed</code> и <code>ySpeed</code> : степенот на поместување на движечката точка во x насока и y насока (цели броеви)</li>
</ul>

<p>За класата да се имплементираат:</p>

<ul>
<li> конструктор со аргументи: <code>MovablePoint(int x, int y, int xSpeed, int ySpeed)</code>,</li>
<li> методите наведени во интерфејсот <code>Movable</code></li>
<li><code>toString</code> метод кој дава репрезентација на објектите во следнот формат <code>Movable point with coordinates (5,35)</code></li>
</ul>

<p>Во класата <code>MovingCircle</code> се чуваат информации за: </p>

<ul>
<li>радиусот на движечкиот круг (цел број)</li>
<li>центарот на движечкиот круг (објект од класата <code>MovingPoint</code>).</li>
</ul>

<p>За класата да се имплементираат: </p>

<ul>
<li>конструктор со аргументи: <code>MovableCircle(int radius, MovablePoint center)</code></li>
<li>методите наведени во интерфејсот <code>Movable</code></li>
<li><code>toString</code> метод којшто дава репрезентација на објектите во следниот формат <code>Movable circle with center coordinates (48,21) and radius 3</code></li>
</ul>

<p>Првите четири методи од <code>Movable</code> (<code>moveUp</code>, <code>modeDown</code>, <code>moveRight</code>, <code>moveLeft</code>) треба да фрлат исклучок од тип <code>ObjectCanNotBeMovedException</code> доколку придвижувањето во соодветната насока не е возможно, односно со придвижувањето се излегува од дефинираниот простор во класата <code>MovablesCollection</code>. Справете се со овие исклучоци на соодветните места. _Погледнете во тест примерите какви пораки треба да се печатат кога ќе се фати исклучок од овој тип и имплементирајте го истото._</p>

<p>Да се дефинира класа <code>MovablesCollection</code> во која што ќе се чуваат информации за:</p>

<ul>
<li>низа од движечки објекти (<code>Movable [] movable</code>)</li>
<li>статичка променлива за максималната вредност на координатата X (минималната е предодредена на 0)</li>
<li>статичка променлива за максималната вредност на координатата Y (минималната е предодредена на 0)</li>
</ul>

<p>За класата да се имплементираат следните методи: </p>

<ul>
<li>конструктор <code>MovablesCollection(int x_MAX, int y_MAX)</code></li>
<li><code>void addMovableObject(Movable m)</code> - метод за додавање на движечки објект во колекцијата од сите движечки објекти. Пред да се додади објектот, мора да се провери дали истиот е може да се вклопи во дефинираниот простор, односно истиот да не излегува од границите <code>0-X_MAX</code> за x координатата и <code>0-Y_MAX</code> за y координатата. Доколку станува збор за движечки круг, потребно е целиот круг да се наоѓа во наведениот интервал на вредности. Доколку движечкиот објект не може да биде вклопен во просторот, да се фрли исклучок од тип <code>MovableObjectNotFittableException</code>. Потребно е да се справите со исклучокот на соодветното место во main методот. _Погледнете во тест примерите какви пораки треба да се печатат кога ќе се фати исклучок од овој тип и имплементирајте го истото._</li>
<li><code>void moveObjectsFromTypeWithDirection (TYPE type, DIRECTION direction) </code>- метод за придвижување на движечките објекти од тип <code>type</code> во насока <code>direction</code>. <code>TYPE</code> и <code>DIRECTION</code> се енумерации кои се задедени во почетниот код. Во зависност од насоката зададена во аргументот, да се повика соодветниот метод за придвижување.</li>
<li><code>toString()</code> - метод кој дава репрезентација на колекцијата од движечки објекти во следниот формат: 
<code>Collection of movable objects with size [големина на колекцијата]: </code> , по што во нов ред следуваат информации за сите движечки објекти во колекцијата.</li>
</ul>