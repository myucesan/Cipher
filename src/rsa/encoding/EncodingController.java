package rsa.encoding;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import rsa.HelperMethods;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class EncodingController {

    @FXML TextField givenN;
    @FXML TextField generatedPForN;
    @FXML TextField generatedQForN;
    @FXML TextField generatedE;
    @FXML TextField messageTextbox;
    @FXML TextField cipherTextbox;
    @FXML Label decLabel;
    @FXML TextArea infoArea;

    /**
     * Step 1: Take the given N and extract p and q by prime factorization
     *                 Note the time it takes to produce p and q.
     *
     *                 formulae: p * q = n
     *                 find p and q
     *
     */
    @FXML
    private void s1_CalculatePandQ() {
        infoArea.setText("p and q are being calculated ... please wait ...");
            Double n = Double.parseDouble(givenN.getText());
            Instant start = Instant.now();
            ArrayList<Double> primeNumbers = HelperMethods.findPQ(n);
            Instant finish = Instant.now();
            Long timeElapsed = Duration.between(start, finish).toMillis();
            infoArea.setText("Amount of time busy finding p and q:   " + timeElapsed.toString() + " milliseconds");

            // N = 3843 gives an error factorization is 3 x 3 x 7 x 61
            if(primeNumbers.equals(null)) {
                generatedPForN.setText("n does not have p");
                generatedQForN.setText("n does not have q");
                infoArea.setText("The given n is a prime factor on itself so it has no p and q or it is a number that has no p and q.");
            } else {
                generatedQForN.setText(primeNumbers.get(0).toString());
                generatedPForN.setText(primeNumbers.get(1).toString());
            }
    }

    @FXML
    private void s1_validateSelfEnteredPandQ() {
        try {
            Double p = Double.parseDouble(generatedPForN.getText());
            Double q = Double.parseDouble(generatedQForN.getText());
            Double pq = p * q;

            if (HelperMethods.findPQ(pq) == null) {
                infoArea.setText("The combination of p and q are invalid.");
                generatedE.setText("");
            } else {
                infoArea.setText("New n has been calculated using p and q.");
                givenN.setText(pq.toString());
            }
        } catch(NumberFormatException e) {
            infoArea.setText("p and/or q is empty.");
            givenN.setText("");
        }


    }

    @FXML
    private void s2_CalculateE() {

        // TODO:  check if p and q is inserted and validated
        // TODO: Validate self-entered E
        Double p = Double.parseDouble(generatedPForN.getText());
        Double q = Double.parseDouble(generatedQForN.getText());
        HelperMethods.listOfE = HelperMethods.calculatePublicKeyE(p, q);
        Integer numberOfE = HelperMethods.listOfE.size();
        Double e = HelperMethods.listOfE.get(HelperMethods.getRandomNumber());
        generatedE.setText(e.toString());
        HelperMethods.e = e;
    }

    @FXML
    private void s2_validateSelfEnteredE() {
        // TODO TODO TODO
        if(HelperMethods.listOfE == null) {
            System.out.println("Soorry");
        } else {
            String insertedE = generatedE.getText();
            Integer e = Integer.parseInt(insertedE);
            System.out.println(e);
        }
    }

    @FXML
    private void s3_CalculateCipher() {
        String message = messageTextbox.getText();
        char[] messageArray = message.toCharArray();
        ArrayList<Double> messageToNumericArray = new ArrayList<Double>();
        ArrayList<String> messageToCipher = new ArrayList<String>();
        Double e = HelperMethods.e;
        Double n = Double.parseDouble(givenN.getText());

        for (char character : messageArray) {
            Integer numeric = (int)character;
            messageToNumericArray.add(Double.parseDouble(numeric.toString()));
        } // Dit klopt

        System.out.println(messageToNumericArray.toString());
        for (Double m : messageToNumericArray) {
            Double cipher = HelperMethods.extendedModularArithmetic(m, e, n);
            messageToCipher.add(String.valueOf(cipher.intValue()));
        }

        String cipher = String.join(" ", messageToCipher);
        cipherTextbox.setText(cipher);
    }





    @FXML
    private void switchToDecoding() throws Exception {
        Stage stage = (Stage) decLabel.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../decoding/decoding.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Cipher v0.0.1 | Decoding");
        stage.setScene(scene);
        stage.show();
    }
}
