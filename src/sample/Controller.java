package sample;

import com.sun.xml.internal.ws.api.FeatureConstructor;
import javafx.fxml.FXML;

import javafx.*;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class Controller {

    @FXML
    javafx.scene.control.Button Button;
    @FXML
    TextField givenN;
    @FXML
    TextField generatedPForN;

    @FXML
    TextField generatedQForN;

    @FXML
    TextField generatedE;

    @FXML
    private void s1_CalculatePandQ() {
        /* perform Euclids (prime factorization) algorithm on "number" and that results in p and q.
        Step 1: Take the given N and extract p and q using Euclid's algorithm
                Note the time it takes to produce p and q.
                TODO: Check if we used Euclid or if Euclid is actually relevant for this case.
                TODO: The time it takes has to be measured and displayed in the application aswell.

                formulae: p * q = n
         */

        Integer n = Integer.parseInt(givenN.getText());
        ArrayList<Integer> primeNumbers = new ArrayList<>();

        for(int i = 2; i < n; i++) {
            while(n % i == 0) {
                primeNumbers.add(i);
                n = n / i;
            }
        }

        if(n > 2) {
            primeNumbers.add(n);
        }

        generatedQForN.setText(primeNumbers.get(0).toString());
        generatedPForN.setText(primeNumbers.get(1).toString());
    }

    @FXML
    private void s2_CalculateE() {
        /*
        Step 2: Generate e using Euler totient functions
            Sub-step 1:
                formulae: φ(n) = (p - 1)(q-1)
                Say p = 5, q = 11
                φ(n) == (5 - 1)(11-1)
                φ(n) = 4 * 10
                φ(n) = 40
             Sub-step 2:
                Now we have the euler totient, say 40 in this case.
                There are two conditions for 'e'
                1. 1 < e < 40
                2. gcd(e, 40) = 1
         */

        Integer p = Integer.parseInt(generatedPForN.getText());
        Integer q = Integer.parseInt(generatedQForN.getText());
        p = p - 1;
        q = q - 1;
        Integer euler = p * q;
//        System.out.println(euler);

        int num1 = 338, num2 = euler, gcd = 1;


        /* loop is running from 1 to the smallest of both numbers
         * In this example the loop will run from 1 to 55 because 55
         * is the smaller number. All the numbers from 1 to 55 will be
         * checked. A number that perfectly divides both numbers would
         * be stored in variable "gcd". By doing this, at the end, the
         * variable gcd will have the largest number that divides both
         * numbers without remainder.
         */

            // For 3233 euler totient = 3120 so e is a value between 2 and 3119 that has a greatest common divisor of 1
            // So we have to go from 2 to 3119 up until we find a gcd of 1 (there are multiple e's possible.
        for(int i = 1; i <= num1 && i <= num2; i++)
            {
                if(num1%i==0 && num2%i==0) {
                    gcd = i;
                }

            }


        System.out.printf("GCD of %d and %d is: %d \n", num1, num2, gcd);
    }
}
