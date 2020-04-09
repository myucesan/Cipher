package rsa;

import javafx.fxml.FXML;

import java.lang.reflect.Array;
import java.util.ArrayList;



public class HelperMethods {

    public static ArrayList<Double> listOfE;
    public static ArrayList<Double> findPQ(Double n) {
        ArrayList<Double> primeNumbers = new ArrayList<>();
        for(Double i = 2.0 ; i < n; i++) {
            while(n % i == 0.0) {
                primeNumbers.add(i);
                n = n / i;
            }
        }

        if(n > 2) {
            primeNumbers.add(n);
        }

        if(primeNumbers.size() > 2) {
            // A given n might not have an unique p and q, primeNumbers will then contain the whole factorization
            return null;
        } else if (primeNumbers.size() == 1) {
            // A given n might be a prime number in itself, in that case it has no p and q either
            return null;
        }

        return primeNumbers;
    }
    public static Double e;

    public static Integer getRandomNumber() {
        Double number = (Double) (Math.floor(Math.random() * (listOfE.size())));
        Integer randomNumber = number.intValue();
        return randomNumber;
    }



    public static Double calculateByCollary(Double m, Double e, Double n) {
        Double result = null;
        Integer power = 1; // Initial power we look at first


        while (power <= e) {
            Integer subResult = power & e.intValue();
            if (subResult > 0) {
                Double calculation;
                if (subResult > 2) {
                    Integer count = 0;
                    do {
                        subResult = subResult /2;
                        count++;
                    } while (subResult > 128);
                    calculation = Math.pow(m, subResult) % n;
                    for (int i = 0; i < count; i++) {
                        calculation = Math.pow(calculation, 2) % n;
                    }
                } else {
                    calculation = Math.pow(m, subResult) % n;
                }

                if (result == null) {
                    result = calculation;
                } else {
                    result *= calculation;
                }

            }
            power = power << 1;
        }

        return (result%n);
    }

    public static Double extendedModularArithmetic(Double msg, Double exp, Double n) {
        if (exp == 0) {
            return 1.0;
        } else if (exp % 2 == 0) {
            Double subResult = extendedModularArithmetic(msg, exp/2, n) % n;
            return (subResult*subResult);
        } else {
            return (msg % n) * extendedModularArithmetic(msg, exp-1, n) % n;
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
    public static ArrayList<Double> calculatePublicKeyE(Double firstPrimeNumber, Double secondPrimeNumber) {
        Double phiNumber = (firstPrimeNumber - 1) * (secondPrimeNumber - 1);
        Double e = -1.0;
        ArrayList<Double> listOfE = new ArrayList<Double>();

        for (e = (phiNumber - 1); e > 1; e--) {
            if (euclidAlgorithmGcd(phiNumber, e) == 1) {
                listOfE.add(e);
            }
        }

        return listOfE;
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

    public static Double euclidAlgorithmGcd(Double firstNumber, Double secondNumber) {
        if (secondNumber == 0) {
            return firstNumber;
        } else {
            return euclidAlgorithmGcd(secondNumber, firstNumber % secondNumber);
        }
    }

    /*
     *   @param      number: The number that has to be checked on
     *   @return     true if number is an integer, false if not
     */
    public static Boolean checkIfInteger(Double number) {
        return number % 1 == 0;
    }

    /*
     * ----------------- THEORY & PRACTICAL EXPLANATION ---------------------
     *   To find the decryption key, a value d that is a
     *   positive inverse to e modulo (p – 1)(q – 1) ought to be found.
     *   p and q are the prime numbers in our parameters.
     *   There are many ways to make this possible.
     *
     *   Our way is: d = 1 + k(phi) / e, where:
     *   - d is an integer
     *   - k is an integer
     *   - e is the e
     *   - phi is (p – 1)(q – 1), where p is firstPrimenumber and q is q
     * ----------------------------------------------------------------------
     *
     *   Calculates the decryption key d
     *
     *   @param      p: the p number
     *   @param      q: the q number
     *   @param      e: the public key e which was calculated before
     *   @return     the decryption key d
     */
    public static Double calculateDecryptionKey(Double p, Double q, Double e) {
        Double phiNumber = (p - 1) * (q - 1);
        Double d = -1.0;
        Integer counter = 1;

        while (true) {
            d = (1 + counter * phiNumber) / e;
            if (HelperMethods.checkIfInteger(d)) {
                return d;
            }
            counter++;
        }

    }

    public static ArrayList<Double> decrypt(ArrayList<Double> cipher, Double decryptionKey, Double n) {
        ArrayList<Double> decryptedMessage = new ArrayList<Double>();
        for (Double c : cipher) {
            decryptedMessage.add(extendedModularArithmetic(c, decryptionKey, n));
        }
        return decryptedMessage;
    }


    /////// --------------------- /////////////
    //          Backup Methods              //
    ////// ---------------------- ////////////
    public static String encryptMess(Double m) // m is an array with ascii values
    {
        String s = "";
        Double y = mpmod(m,e,3233.0);
        s += y;

        return s;

    }

    public static Double mpmod(Double base, Double exponent, Double modulus)
    {
        if ((base < 1) || (exponent < 0) || (modulus < 1))
        {
            return(0.0);
        }
        Double result = 1.0;
        while (exponent > 0)
        {
            if ((exponent % 2) == 1)
            {
                result = (result * base) % modulus;
            }
            base = (base * base) % modulus;
            exponent = Math.floor(exponent / 2);
        }
        return (result);
    }

}
