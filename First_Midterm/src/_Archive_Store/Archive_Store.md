# Arhive Store

Да се имплементира класа <code>ArchiveStore</code> во која се чува листа на архиви (елементи за архивирање).

<p>Секој елемент за архивирање <code>Archive</code> има:</p>

<ul>
<li>id - цел број</li>
<li>dateArchived - датум на архивирање.</li>
</ul>

<p>Постојат два видови на елементи за архивирање, <code>LockedArchive</code> за кој дополнително се чува датум до кој не смее да се отвори <code>dateToOpen</code> и <code>SpecialArchive</code> за кој се чуваат максимален број на дозволени отварања <code>maxOpen</code>. За елементите за архивирање треба да се обезбедат следните методи:</p>

<ul>
<li><code>LockedArchive(int id, Date dateToOpen) </code> - конструктор за заклучена архива</li>
<li><code>SpecialArchive(int id, int maxOpen)</code> - конструктор за специјална архива</li>
</ul>

<p>За класата <code>ArchiveStore</code> да се обезбедат следните методи:</p>

<ul>
<li><code>ArchiveStore()</code> - default конструктор</li>
<li><code>void archiveItem(Archive item, Date date)</code> - метод за архивирање елемент item на одреден датум date</li>
<li><code>void openItem(int id, Date date)</code> - метод за отварање елемент од архивата со зададен id и одреден датум date. Ако не постои елемент со даденото id треба да се фрли исклучок од тип <code>NonExistingItemException</code> со порака <code>Item with id [id] doesn't exist</code>.</li>
<li><code>String getLog()</code> - враќа стринг со сите пораки запишани при архивирањето и отварањето архиви во посебен ред.</li>
</ul>

<p>За секоја акција на архивирање во текст треба да се додаде следната порака <code>Item [id] archived at [date]</code>, додека за секоја акција на отварање архива треба да се додаде <code>Item [id] opened at [date]</code>. При отварање ако се работи за <code>LockedArhive</code> и датумот на отварање е пред датумот кога може да се отвори, да се додаде порака <code>Item [id] cannot be opened before [date]</code>. Ако се работи за <code>SpecialArhive</code> и се обидиеме да ја отвориме повеќе пати од дозволениот број (maxOpen) да се додаде порака <code>Item [id] cannot be opened more than [maxOpen] times</code>.</p>