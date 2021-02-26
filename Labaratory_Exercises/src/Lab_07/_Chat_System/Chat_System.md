# Chat System

<p>Треба&nbsp;да&nbsp;се&nbsp;развие&nbsp;класа&nbsp;за&nbsp;администрација&nbsp;на&nbsp;чет‐систем(chat).&nbsp;Системот&nbsp;се&nbsp;состои&nbsp;од&nbsp;повеќе&nbsp;чет‐соби,&nbsp;објекти&nbsp;од&nbsp;класата <strong>ChatRoom</strong>. Во&nbsp;<strong>ChatRoom</strong> ги&nbsp;чуваме името&nbsp;на собата&nbsp;и&nbsp;имињата&nbsp;на&nbsp;корисниците&nbsp;кои&nbsp;тековно&nbsp;се&nbsp;наоѓаат&nbsp;во&nbsp;таа&nbsp;соба&nbsp;(за&nbsp;корисниците&nbsp;да&nbsp;се&nbsp;користи&nbsp;соодветен&nbsp;<strong>Set</strong>).</p>

<ul>
<li><code>ChatRoom(String&nbsp;name)</code>&nbsp;‐&nbsp;креира&nbsp;нова&nbsp;празна&nbsp;соба&nbsp;за&nbsp;чет&nbsp;(празна&nbsp;значи&nbsp;без&nbsp;корисници).&nbsp;</li>
<li><code>addUser(String&nbsp;username)</code>&nbsp;-&nbsp;го&nbsp;додава&nbsp;корисникот&nbsp;со&nbsp;тоа&nbsp;име&nbsp;во&nbsp;собата.&nbsp;</li>
<li><code>removeUser(String&nbsp;username)</code>&nbsp;-&nbsp;го&nbsp;отстранува&nbsp;корисникот&nbsp;со&nbsp;тоа&nbsp;име&nbsp;од&nbsp;собата&nbsp;доколку&nbsp;има&nbsp;таков,&nbsp;во&nbsp;спротивно&nbsp;не&nbsp;прави&nbsp;ништо.&nbsp;</li>
<li><code>toString():String</code>&nbsp;- враќа&nbsp;стринг&nbsp;кои&nbsp;ги&nbsp;содржи&nbsp;името&nbsp;на&nbsp;собата&nbsp;и&nbsp;сите&nbsp;корисници&nbsp;кои&nbsp;се&nbsp;во собата&nbsp;секој&nbsp;одделен&nbsp;со&nbsp;нов&nbsp;ред. Корисниците&nbsp;се&nbsp;подредени&nbsp;алфабетски. Ако собата е празна се враќа името на собата во еден ред,&nbsp;а&nbsp;во&nbsp;вториот&nbsp;ред&nbsp;стрингот&nbsp;"EMPTY"&nbsp;(наводници&nbsp;само&nbsp;за&nbsp;појаснување).&nbsp;</li>
<li><code>hasUser(String&nbsp;username):boolean</code>&nbsp;- враќа&nbsp;true&nbsp;ако&nbsp;постои&nbsp;корисник&nbsp;со&nbsp;тоа&nbsp;име&nbsp;во&nbsp;собата.&nbsp;</li>
<li><code>numUsers():int</code>&nbsp;- го&nbsp;враќа&nbsp;бројот&nbsp;на&nbsp;корисници&nbsp;во&nbsp;собата.&nbsp;</li>
</ul>

<p>Главната&nbsp; класа&nbsp;<strong>ChatSystem</strong>&nbsp; ги&nbsp; содржи&nbsp; сите&nbsp; соби&nbsp; и&nbsp; сите&nbsp; орисници.&nbsp;Корисниците&nbsp;може да&nbsp;се&nbsp;членови&nbsp;на&nbsp;една, повеќе&nbsp;или&nbsp;да&nbsp;не&nbsp;се&nbsp;членови&nbsp;на ниедна&nbsp;соба. За менаџмент на&nbsp;собите&nbsp;треба&nbsp;да&nbsp;ги&nbsp;понудите&nbsp;следните&nbsp;три&nbsp;методи:</p>

<ul>
<li><code>addRoom(String&nbsp;roomName)</code> - додава&nbsp;нова&nbsp;празна&nbsp;соба&nbsp;во&nbsp;листата&nbsp;на&nbsp;соби.&nbsp;</li>
<li><code>removeRoom(String&nbsp;roomName)</code>&nbsp;-&nbsp;ја&nbsp;отстранува&nbsp;собата&nbsp;од&nbsp;листата.&nbsp;</li>
<li><code>getRoom(String&nbsp;roomName):ChatRoom</code>&nbsp;- го&nbsp;враќа&nbsp;објектот&nbsp;кој&nbsp;ја&nbsp;претставува&nbsp;собата&nbsp;со&nbsp;име&nbsp;<code>roomName</code>.&nbsp;Фрлете&nbsp;<code>NoSuchRoomException(roomName)</code>&nbsp;доколку&nbsp;не&nbsp;постои&nbsp;соба&nbsp;со&nbsp;тоа&nbsp;име.</li>
</ul>

<p><strong>Забелешка</strong>:&nbsp;Собите&nbsp;чувајте&nbsp;ги&nbsp;во&nbsp;<strong>TreeMap</strong>&nbsp;за&nbsp;да&nbsp;бидат&nbsp;секогаш&nbsp;подредени&nbsp;по&nbsp;нивното&nbsp;име.</p>

<p>Дополнително&nbsp;во&nbsp;класата&nbsp;ChatSystem&nbsp;постојат&nbsp;следните&nbsp;методи&nbsp;за&nbsp;работа&nbsp;со&nbsp;корисниците:</p>

<ul>
<li><code>ChatSystem()</code>&nbsp;- default&nbsp;constructor&nbsp;</li>
<li><code>register(String&nbsp;userName)</code>&nbsp;-&nbsp;го&nbsp;регистрира&nbsp;корисникот&nbsp;во&nbsp;системот.&nbsp;Го&nbsp;додава&nbsp;во&nbsp;собата&nbsp;со најмалку&nbsp;корисници. Доколку&nbsp;има&nbsp;повеќе такви&nbsp;соби&nbsp;тогаш го&nbsp;додава во првата соба&nbsp;по лексикоргафско&nbsp;подредување.</li>
<li><code>registerAndJoin(String&nbsp;userName,&nbsp;String roomName)</code> - го&nbsp;регистрира&nbsp;корисникот&nbsp;во&nbsp;системот. Дополнително&nbsp;го&nbsp;додава&nbsp;во&nbsp;собата&nbsp;со&nbsp;име&nbsp;<code>roomName</code>.&nbsp;</li>
<li><code>joinRoom(String&nbsp;userName,&nbsp;String roomName)</code>&nbsp;- го&nbsp; додава&nbsp; корисникот&nbsp; во&nbsp; собата&nbsp; со&nbsp;соодветно&nbsp; име&nbsp; доколку&nbsp; таа&nbsp; постои,&nbsp; во&nbsp; спротивно&nbsp; фрла&nbsp; исклучок&nbsp; од&nbsp; типот <code>NoSuchRoomExcеption(roomName)</code>.&nbsp;Ако&nbsp;не&nbsp;постои&nbsp;регистриран&nbsp;корисник&nbsp;со&nbsp;тоа&nbsp;име&nbsp;се&nbsp;фрла&nbsp;исклучок&nbsp;<code>NoSuchUserException(userName)</code>.</li>
<li><code>leaveRoom(String&nbsp;username,&nbsp;String&nbsp;roomName)</code>&nbsp;-&nbsp;го&nbsp;отстранува&nbsp;корисникот&nbsp;од&nbsp;собата&nbsp;со&nbsp;соодветно&nbsp; име&nbsp; доколку&nbsp; таа&nbsp; постои.&nbsp; во&nbsp; спротивно&nbsp; фрла&nbsp; исклучок&nbsp; од&nbsp; типот <code>NoSuchRoomExcеption(roomName)</code>.&nbsp;Ако&nbsp;не&nbsp;постои&nbsp;регистриран&nbsp;корисник&nbsp;со&nbsp;тоа&nbsp;име&nbsp;се&nbsp;фрла&nbsp;исклучок&nbsp;<code>NoSuchUserException(userName)</code>.</li>
<li>followFriend(String&nbsp;username,&nbsp;String&nbsp;friend_username)&nbsp;–&nbsp;корисникот&nbsp;со&nbsp;име&nbsp;username&nbsp;го приклучува&nbsp;во&nbsp;сите&nbsp;соби&nbsp;во&nbsp;кој е&nbsp;член&nbsp;корисникот со&nbsp;име <code>friendUsername</code>. Ако&nbsp;не постои регистриран&nbsp;корисник&nbsp;со&nbsp;тоа&nbsp;име&nbsp;се&nbsp;фрла&nbsp;исклучок&nbsp;<code>NoSuchUserException(userName)</code>.</li>
</ul>