# Online Shop

<p>Да се имплементира апликација за чување на продукти во една online продавница. За таа цел, дефинирајте класа <code>OnlineShop</code> во која што ќе ги чувате сите продукти во продавниците и ќе нуди функционалности за листање на продуктите и нивно купување. За класата да се имплементираат:</p>

<ul>
<li>Default конструктор <code>OnlineShop()</code></li>
<li>Метод <code>void addProduct(String category, String id, String name, LocalDateTime createdAt, double price)</code> - метод за додавање на производ во онлајн продавницата. Секој производ е дефиниран со категорија, ИД, име, датум кога се додава во продавницата и негова цена.</li>
<li>метод <code>double buyProduct(String id, int quantity)</code> - што ќе имплементира купување на <code>quantity</code> количина на производот со ИД <code>id</code>. Методот да врати колку пари се потрошени за оваа трансакција. Да се фрли исклучок од тип <code>ProductNotFoundException</code> доколку не постои производот. Методот да има комплексност <code>О(1)</code>.</li>
<li>метод <code>List&lt;List&lt;Product&gt;&gt; listProducts(String category, COMPARATOR_TYPE comparatorType, int pageSize)</code> којшто ќе ги излиста сите производи од категоријата <code>category</code> сортирани според компараторот <code>comparatorType</code> групирани во страници со големина <code>pageSize</code> (пагинација). <code>Category</code> може да биде и <code>null</code> па во тој случај се листаат сите продукти во онлајн продавницата.</li>
</ul>

<p><code>COMPARATOR_TYPE</code> е еnum којшто ви е даден во почетниот код. За печатење на продуктите користете ја вградената toString нотација во IDE-то (запазете го редоследот и имињата на променливите).</p>