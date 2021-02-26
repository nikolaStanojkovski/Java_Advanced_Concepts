package Lab_08._Refactoring;

import java.util.ArrayList;
import java.util.Scanner;

abstract class TriviaQuestion {

    protected String question;		// Actual question
    protected String answer;		// Answer to question
    protected int value;			// Point value of question

    public TriviaQuestion() {
        question = "";
        answer = "";
        value = 0;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public int getValue() {
        return value;
    }

    public void printCorrectMessage() {
        System.out.println("That is correct!  You get " + getValue() + " points.");
    }

    public void printWrongMessage() {
        System.out.println("Wrong, the correct answer is " + getAnswer());
    }

    public abstract void showQuestion();

    public abstract int showAnswer(int score, String answer);
}

class TrueFalseQuestion extends TriviaQuestion {

    @Override
    public void showQuestion() {
        System.out.println(super.getQuestion());
        System.out.println("Enter 'T' for true or 'F' for false.");
    }

    @Override
    public int showAnswer(int score, String answer) {
        if (answer.charAt(0) == super.getAnswer().charAt(0)) {
            super.printCorrectMessage();
            score += super.getValue();
        } else {
            super.printWrongMessage();
        }
        return score;
    }

    public TrueFalseQuestion(String q, String a, int v) {
        this.question = q;
        this.answer = a;
        this.value = v;
    }
}

class FreeformQuestion extends TriviaQuestion {

    public FreeformQuestion(String q, String a, int v) {
        this.question = q;
        this.answer = a;
        this.value = v;
    }

    @Override
    public void showQuestion() {
        System.out.println(super.getQuestion());
    }

    @Override
    public int showAnswer(int score, String answer) {
        if (answer.charAt(0) == super.getAnswer().charAt(0)) {
            super.printCorrectMessage();
            score += super.getValue();
        } else {
            super.printWrongMessage();
        }
        return score;
    }
}

class TriviaData {

    private final ArrayList<TriviaQuestion> data;

    public TriviaData() {
        data = new ArrayList<>();
    }

    public void addFreeformQuestion(String q, String a, int v) {
        data.add(new FreeformQuestion(q, a, v));
    }

    public void addTrueFalseQuestion(String q, String a, int v) {
        data.add(new TrueFalseQuestion(q, a, v));
    }

    public void showQuestion(int index) {
        TriviaQuestion q = data.get(index);
        System.out.println("Question " + (index + 1) + ".  " + q.getValue() + " points.");
        q.showQuestion();
    }

    public int numQuestions() {
        return data.size();
    }

    public TriviaQuestion getQuestion(int index) {
        return data.get(index);
    }

}

public class TriviaGame {

    public TriviaData questions;	// Questions

    public TriviaGame() {
        // Load questions
        questions = new TriviaData();
        questions.addFreeformQuestion("The possession of more than two sets of chromosomes is termed?",
                "polyploidy", 3);
        questions.addTrueFalseQuestion("Erling Kagge skiied into the north pole alone on January 7, 1993.",
                "F", 1);
        questions.addFreeformQuestion("1997 British band that produced 'Tub Thumper'",
                "Chumbawumba", 2);
        questions.addFreeformQuestion("I am the geometric figure most like a lost parrot",
                "polygon", 2);
        questions.addTrueFalseQuestion("Generics were introducted to Java starting at version 5.0.",
                "T", 1);
    }
    // Main game loop

    public static void main(String[] args) {
        int score = 0;
        int questionNum = 0;
        TriviaGame game = new TriviaGame();
        Scanner keyboard = new Scanner(System.in);

        while (questionNum < game.questions.numQuestions()) {

            game.questions.showQuestion(questionNum);

            String answer = keyboard.nextLine();

            TriviaQuestion q = game.questions.getQuestion(questionNum);
            score = q.showAnswer(score, answer);
            System.out.println("Your score is " + score);
            questionNum++;
        }
        System.out.println("Game over!  Thanks for playing!");
    }
}