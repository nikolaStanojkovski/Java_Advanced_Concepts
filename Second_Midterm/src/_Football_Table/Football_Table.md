# Football Table

<p>Да се имплементира класа <code>FootballTable</code> за обработка од податоците за повеќе фудбласки натпревари од една лига и прикажување на табелата на освоени поени според резултатите од натпреварите. Во класата да се имплементираат:</p>

<ul>
<li><code>public void addGame(String homeTeam, String awayTeam, int homeGoals, int awayGoals)</code> - метод за додавање податоци за одигран натпревар помеѓу тимот со име <code>homeTeam</code> (домашен тим) и тимот со име <code>awayTeam</code> (гостински тим), при што <code>homeGoals</code> претставува бројот на постигнати голови од домашниот тим, а <code>awayGoals</code> бројот на постигнати голови од гостинскиот тим.</li>
<li><code>public void printTable()</code> - метод за печатење на табелата според одиграните (внесените) натпревари. Во табелата се прикажуваат редниот број на тимот во табелата, името (со 15 места порамнето во лево), бројот на одиграни натпревари, бројот на победи, бројот на нерешени натпревари, бројот на освоени поени (сите броеви се печатат со 5 места порамнети во десно). Бројот на освоени поени се пресметува како <code>број_на_победи x 3 + број_на_нерешени x 1</code>. Тимовите се подредени според бројот на освоени поени во опаѓачки редослед, ако имаат ист број на освоени поени според гол разликата (разлика од постигнатите голови и примените голови) во опаѓачки редослед, а ако имаат иста гол разлика, според името.</li>
</ul>