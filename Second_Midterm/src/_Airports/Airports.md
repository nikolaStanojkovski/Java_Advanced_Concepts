# Airports

<p>Да се имплементира класа <code>Airports</code> со следните методи:</p>

<ul>
<li><code>public void addAirport(String name, String country, String code, int passengers)</code> - метод за додавање нов аеродром (име, држава, код и број на патници кои ги превезува годишно)</li>
<li><code>public void addFlights(String from, String to, int time, int duration)</code> - метод за додавање летови (код на аеродром за полетување, код на аеродром за слетување, време на тргнување во минути поминати од 0:00 часот, времетраење на летот во минути). Од аеродром А до аеродром Б може да има повеќе летови.</li>
<li><code>public void showFlightsFromAirport(String code)</code> - метод кои ги прикажува сите летови од аеродромот со код <code>code</code>. Прво се печати името на аеродромот (формат во пример излезот), потоа се печатат сите летови (формат во пример излезот) подредени најпрво лексикографски според кодот на аеродромот дестинација, а потоа летовите кон тој аеродром според времето на полетување (целосно точна имплементација се смета без повикување на sort методи).</li>
<li><code>public void showDirectFlightsFromTo(String from, String to)</code> - метод кој ги прикажува сите директни летови од аеродромот со код <code>from</code> до аеродромот со код <code>to</code>.</li>
<li><code>public void showDirectFlightsTo(String to)</code> - метод кои ги прикажува сите директни летови до аеродромот со код <code>to</code>.</li>
</ul>

