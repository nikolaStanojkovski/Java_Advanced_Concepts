package Lab_01._Roman_Numbers;

import java.util.Scanner;
import java.util.stream.IntStream;

public class RomanConverterTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        IntStream.range(0, n)
                .forEach(x -> System.out.println(RomanConverter.toRoman(scanner.nextInt())));
        scanner.close();
    }
}


class RomanConverter {

    public static String toRoman(int n) {
        int temp=n;
        String finalString = "";
        int size=0;
        while(temp>0){
            int number = temp%10;
            temp/=10;
            ++size;
            finalString = getRomanLetter(number, size) + finalString;
        }
        return finalString;
    }

    public static String getRomanLetter(int number, int size)
    {
        if(number==0)
            return "";
        String theDigitFirst=""; String theDigitSecond=""; String theDigitThird="";
        if(size==1)
        {
            theDigitFirst="I"; theDigitSecond="V"; theDigitThird="X";
        }
        else if(size==2)
        {
            theDigitFirst="X"; theDigitSecond="L"; theDigitThird="C";
        }
        else if(size==3)
        {
            theDigitFirst="C";theDigitSecond="D"; theDigitThird="M";
        }
        else if(size==4)
        {
            StringBuilder sumLetters = new StringBuilder();
            for(int i=0;i<number;i++)
                sumLetters.append("M");
            return sumLetters.toString();
        }

        if(number<5 && number!=4)
        {
            StringBuilder sumLetters = new StringBuilder();
            for(int i=0;i<number;i++)
                sumLetters.append(theDigitFirst);
            return sumLetters.toString();
        }
        else if(number==5)
            return theDigitSecond;
        else if(number == 4)
            return theDigitFirst+theDigitSecond;
        else if(number != 9)
        {
            StringBuilder sumLetters = new StringBuilder(theDigitSecond);
            for(int i=0;i<number-5;i++)
                sumLetters.append(theDigitFirst);
            return sumLetters.toString();
        }
        else
            return theDigitFirst + theDigitThird;
    }

}

