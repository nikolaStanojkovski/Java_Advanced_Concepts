# Candidate Evaluator

<p>Дадена е класата <code>Applicant</code> (кандидат за евалуација) со име, кредитен резултат (credit score), работен стаж (experience) и дали има криминално досие (criminal record). Исто така, даден е интерфејс за евалуација на кандидати <code>Evaluator</code> со еден метод <code>boolean evaluate(Applicant applicant)</code> и енумерација со следните видови на евалуатори:</p>

<p>Кандидатот ја поминува евалуацијата (методот враќа <code>true</code>) ако:</p>

<ul>
<li>NO_CRIMINAL_RECORD - нема криминално досие</li>
<li>MORE_EXPERIENCE - има барем 10 години работен стаж</li>
<li>MORE_CREDIT_SCORE - има кредитен резултат од минимум 500</li>
<li>NO_CRIMINAL_RECORD_AND_MORE_EXPERIENCE - нема криминално досие и има барем 10 години работен стаж</li>
<li>MORE_EXPERIENCE_AND_MORE_CREDIT_SCORE - има барем 10 години работен стаж и има кредитен резултат од минимум 500</li>
<li>NO_CRIMINAL_RECORD_AND_MORE_CREDIT_SCORE - нема криминално досие и има кредитен резултат од минимум 500.</li>
</ul>

<p>Ваша задача е да го имплементирате методот <code>build(Evaluator.TYPE type)</code> во класата <code>EvaluatorBuilder</code>, кој ќе враќа објект од соодветна имплементација на интерфејсот <code>Evaluator</code> за соодветниот тип на евалуација. Ако типот на евалуација не е некој од наведените, методот треба да фрли исклучок од тип <code>InvalidEvaluation</code>.</p>

<p>За добро дизајнирано решение (ќе биде објавено по завршувањето на испитот) ќе се добиваат бонус 10 поени.</p>