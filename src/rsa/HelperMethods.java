package rsa;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;



public class HelperMethods {

    public static HashMap<Long, Long> listOfE;
    public static Long e;
    public static Long phi;
    public static Long theGivenN;

    public static ArrayList<Long> findPQ(Long n) {
        theGivenN = n;  // global voor is validate
        ArrayList<Long> primeNumbers = new ArrayList<>();

        for (Long i = 3L; i <= Math.sqrt(n); i+= 2)
        {
            while (n%i == 0)
            {
                primeNumbers.add(i);
                n /= i;
            }
        }


        if (n > 2) {
            primeNumbers.add(n);

        }

        if(primeNumbers.size() > 2) {
            // A given n might not have an unique p and q, primeNumbers will then contain the whole factorization
            return null;
        } else if (primeNumbers.size() == 1) {
            // A given n might be a prime number in itself, in that case it has no p and q either
            return null;
        }
//        System.out.println(primeNumbers.toString());
        return primeNumbers;
    }


    public static Long getRandomNumber() {
        Double number = (Double) (Math.floor(Math.random() * (listOfE.size())));
        Long randomNumber = number.longValue();
        return randomNumber;
    }

    /*
     * ----------------- THEORY & PRACTICAL EXPLANATION ---------------------
     *   This code reflects the theory: Solving the problem of big numbers
     * ----------------------------------------------------------------------
     *
     *   calculates modular arithmetic.
     *
     *   @param      base: base number
     *   @param      exp: the power
     *   @param      n: the modular number
     *   @return     result of modular arithmetic
     */


    public static Long extendedModularArithmetic(Long base, Long exp, Long n) {
        if (exp == 0) {
            return 1L;
        } else if (exp % 2 == 0) {
            Long subResult = extendedModularArithmetic(base, exp/2, n) % n;
            return (subResult*subResult);
        } else {
            return (base % n) * extendedModularArithmetic(base, exp-1, n) % n;
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
     *   @param      p the p number
     *   @param      q: the q number
     *   @return     the public key e
     */
    public static HashMap<Long, Long> calculatePublicKeyE(Long p, Long q) {
        Long phiNumber = (p - 1) * (q - 1);
        HelperMethods.phi = phiNumber;
        Long e = -1L;
        HashMap<Long, Long> listOfE = new HashMap<Long, Long>();
        Long index = 0L;
        Instant start = Instant.now();

        for (e = (phiNumber - 1); e > 1; e--) {
            if (euclidAlgorithmGcd(phiNumber, e) == 1) {
                listOfE.put(index, e);
                index++;

                if(listOfE.size() > 500) {
                    break;
                }
            }
        }

        Instant finish = Instant.now();
        System.out.println("E are generated in so many milliseconds:");
        Long timeElapsed = Duration.between(start, finish).toMillis();
        System.out.println(timeElapsed);
        return listOfE;
    }


        /*
    *   ----------------- THEORY & PRACTICAL EXPLANATION ---------------------
    *   An extended version of the Euclidean algorithm
    *   can be used to find a concrete expression for the greatest common
        divisor of Longs a and b
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

    public static Long euclidAlgorithmGcd(Long firstNumber, Long secondNumber) {
        if (secondNumber == 0) {
            return firstNumber;
        } else {
            return euclidAlgorithmGcd(secondNumber, firstNumber % secondNumber);
        }
    }

    /*
     *   @param      number: The number that has to be checked on
     *   @return     true if number is an Long, false if not
     */
    public static Boolean checkIfLong(Double number) {
        return number % 1 == 0;
    }

    /*
     * ----------------- THEORY & PRACTICAL EXPLANATION ---------------------
     *   To find the decryption key, a value d that is a
     *   positive inverse to e modulo (p – 1)(q – 1) ought to be found.
     *   p and q are the prime numbers in our parameters.
     *   There are many ways to make this possible.
     *
     *  The positive inverse is being found using the method findInverse.
     *  Then, we substract the positive inverse from the phi number, if 
     *  the result is negative.
     * ----------------------------------------------------------------------
     *
     *   Calculates the decryption key d
     *
     *   @param      p: the p number
     *   @param      q: the q number
     *   @param      e: the public key e which was calculated before
     *   @return     the decryption key d
     */
    public static Long calculateDecryptionKey(Long e, Long p, Long q) {
        Long phiNumber = (p - 1) * (q - 1);
        Long result = findInverse(e, phiNumber);
        if (result < 0) {
            result = result + phiNumber;
        } 
        return result;

    }

    /*
     * ----------------- THEORY & PRACTICAL EXPLANATION ---------------------
     *   This method calculates the inverse modular. It does so by using
     *   an extended version of the Euclidean Algorithm, in which we try
     *   to find the linear combination of e and phi in the method. However,
     *   the important part about this is that if gcd(e, phi) = 1, then there exists
     *   an integer s such that as = 1 mod ( n). So if we find the linear combination
     *   of e and phi, we will find the inverse, because we have already shown that 
     *   gcd(e, phi) equals 1.
     * ----------------------------------------------------------------------
     *
     *   Calculates the inverse
     *
     *   @param      e: the public key e which was calculated before
     *   @param      phi: (p-1)(q-1)
     *   @return     the inverse
     */

    public static Long findInverse(Long e, Long phi)
	{
		Long x = 0L, y = 1L, lastx = 1L, lasty = 0L;
		while(phi!=0)
		{
			Long quotient = e/phi;

			Long temp = e;
			e = phi;
			phi=temp%phi;

			temp = x;
			x=lastx-quotient*x;
			lastx=temp;

			temp = y;
			y=lasty-quotient*y;
			lasty=temp;
		}

		return lastx;
	}
    
    /*
     * ----------------- THEORY & PRACTICAL EXPLANATION ---------------------
     *  This methods decrypts the cipher using the decryption key d. It applies the method:
     *  message = c^d mod n. As d might be too big, we split it as explained in the
     *  theory chapter: solving the problem of big numbers, using the method
     *  extendedModularArithmetic.
     * ----------------------------------------------------------------------
     *
     *   Decrypts the message.
     *
     *   @param      cipher: the encrypted message
     *   @param      decryptionKey: the decryption key
     *   @param      n: p*q, a public key
     *   @return     the original message
     */

    public static ArrayList<Long> decrypt(ArrayList<Long> cipher, Long decryptionKey, Long n) {
        ArrayList<Long> decryptedMessage = new ArrayList<Long>();
        for (Long c : cipher) {
            decryptedMessage.add(extendedModularArithmetic(c, decryptionKey, n));
        }
        return decryptedMessage;
    }



    /////// --------------------- /////////////
    //          Validation Functions         //
    ////// ---------------------- ////////////

    public static ArrayList<Long> findPrimes(Long n) {
        ArrayList<Long> primeNumbers = new ArrayList<>();
        for(Long i = 2L ; i < n; i++) {
            while(n % i == 0.0) {
                primeNumbers.add(i);
                n = n / i;
            }
        }

        if(n > 2) {
            primeNumbers.add(n);
        }

        return primeNumbers;
    }

    public static Boolean isValidE(Long e) {
        ArrayList<Long> primeNumbersOfE = findPrimes(e);
        String s = "Prime factors of e: ";
        for(Long prime : primeNumbersOfE) {

            s += prime + " , ";
        }
        System.out.println(s);

        if(euclidAlgorithmGcd(phi,e) == 1 && e >1 && e< theGivenN) {
            return true;
        } else {
            return false;
        }
    }


    /////// --------------------- /////////////
    //          File Management             //
    ////// ---------------------- ////////////
    public static void writeToFile(String e) throws IOException {
        String filePath = "listOfE.txt";
        File checkFile = new File(filePath);
        Boolean fileExists;
        fileExists = checkFile.exists();

        BufferedWriter writer = new BufferedWriter(new FileWriter("listOfE.txt", fileExists));
        writer.write(e + "\n");
        writer.close();
    }



}
