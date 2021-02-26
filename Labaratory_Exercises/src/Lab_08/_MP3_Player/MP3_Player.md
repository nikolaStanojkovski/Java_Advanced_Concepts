# MP3 Player

<p>Да се напише класа за <strong>MP3Player</strong> во која се чуваат листа со песни (<code>List&lt;Song&gt;</code>) и песната која моментално се слуша (е на ред да се пушти). MP3Player-от има четири копчиња <strong>Play</strong>, <strong>Stop</strong>, <strong>FWD</strong> и <strong>REW</strong>.</p>

<ul>
<li>Ако се притисне копчето <strong>Play</strong> се пушта моменталната песна (на екранот се испишува <code>"Song i is playing"</code>, каде <code>i</code> е редниот број на моменталната песна, почнувајќи од 0).</li>
<li>Ако се притисне копчето <strong>Stop</strong>:<ul>
<li>моменталната песна кој е пуштена се паузира (на екран се испишува <code>"Song i is paused"</code> каде <code>i</code> е моменталната песна која била пуштена).</li>
<li>листата целосно се ресетира од почеток, ако моменталната песна веќе била паузирана (на екран се испишува <code>"Songs are stopped"</code>).</li>
</ul>
</li>
<li>Ако се притисне копчето <strong>FWD</strong> песната се паузира и следната песна од листата станува моментална (да се земе во предвид кружното повторување на песните).</li>
<li>Ако се притисне копчето <strong>REW</strong> песната се паузира и претходната песна од листата станува моментална (да се земе во предвид кружното повторување на песните).</li>
</ul>

<p>За секоја песна (Song) се чуваат насловот на песната (String) и изведувачот на песната (String).</p>