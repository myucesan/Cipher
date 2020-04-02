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
    private void s1_CalculatePandQ() {
        /* perform Euclids (prime factorization) algorithm on "number" and that results in p and q.
        Step 1: Take the given N and extract p and q using Euclid's algorithm
                Note the time it takes to produce p and q.
                TODO: Check if we used Euclid or if Euclid is actually relevant for this case.
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
}
