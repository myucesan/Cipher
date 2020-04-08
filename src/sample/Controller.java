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
        Integer e = calculatePublicKeyE(p, q);
        generatedE.setText(e.toString());

    }

                /*
    *   ----------------- THEORY & PRACTICAL EXPLANATION ---------------------
    *   An extended version of the Euclidean algorithm
    *   can be used to find a concrete expression for the greatest common
        divisor of integers a and b
    *
    *   To put Euclid's algorithm into practice, a recursive method is
    *   created and keeps calling itself till the second number is equal to 0.
    *
    *   Time-complexity (in Big O): TO CALCULATE
    *   ----------------------------------------------------------------------
    *
    *   Calculates the Greatest Common Divisor.
    *
    *   @param      firstNumber: the first number toe be inserted. The Phi number
    *   @param      secondNumber: the second number. This is supposed to be 'E' in the end
    *   @return     Greatest Common Divisor
    */

    public static int euclidAlgorithmGcd(int firstNumber, int secondNumber) {
        if (secondNumber == 0) {
            return firstNumber;
        } else {
            return euclidAlgorithmGcd(secondNumber, firstNumber % secondNumber);
        }
    }

    /*
     * ----------------- THEORY & PRACTICAL EXPLANATION ---------------------
     *   Two public keys are important for RSA: a key pq which is a
     *   multiplication of two prime numbers p and q, and a key
     *   e which is relatively prime to the Phi Number, which can
     *   be calculated as: (p-1) * (q-1). Here, we calculate e.
     *
     *   According to the theory a larger e number helps guarantee
     *   the secrecy of the RSA. This is why we loop from the
     *   (pq - 1) till value 1. Whenever the greatest common
     *   divisor is equal to 1, that value is taken and returned.
     * ----------------------------------------------------------------------
     *
     *   Calculates the public key e.
     *
     *   @param      firstPrimeNumber: the p number
     *   @param      secondPrimeNumber: the q number
     *   @return     the public key e
     */
    public static int calculatePublicKeyE(int firstPrimeNumber, int secondPrimeNumber) {
        int phiNumber = (firstPrimeNumber - 1) * (secondPrimeNumber - 1);
        int e = -1;

        for (e = (phiNumber - 1); e > 1; e--) {
            if (euclidAlgorithmGcd(phiNumber, e) == 1) break;
        }

        return e;
    }
}
