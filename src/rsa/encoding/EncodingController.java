package rsa.encoding;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import rsa.HelperMethods;

import java.io.IOException;
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
    @FXML Button step1;
    @FXML Button step2;
    @FXML Button step3;

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
        String inputGivenN = givenN.getText();

        if(inputGivenN.isEmpty()) {
            infoArea.setText("N has not been entered.");
        } else {
            infoArea.setText("p and q are being calculated ... please wait ...");
            Long n =  Long.parseLong(inputGivenN);
            Instant start = Instant.now();
            ArrayList<Long> primeNumbers = HelperMethods.findPQ(n);
            Instant finish = Instant.now();
            Long timeElapsed = Duration.between(start, finish).toMillis();
            infoArea.setText("Amount of time busy finding p and q:   " + timeElapsed.toString() + " milliseconds");
            step2.setDisable(false);

            // N = 3843 gives an error factorization is 3 x 3 x 7 x 61 FIXME
            if(primeNumbers.equals(null)) {
                generatedPForN.setText("n does not have p");
                generatedQForN.setText("n does not have q");
                infoArea.setText("The given n is a prime factor on itself so it has no p and q or it is a number that has no p and q.");
            } else {
                generatedQForN.setText(primeNumbers.get(0).toString());
                generatedPForN.setText(primeNumbers.get(1).toString());
            }
        }

    }

    @FXML
    private void s1_validateN() {
        String inputGivenN = givenN.getText();
        if (inputGivenN.isEmpty()) {
            step1.setDisable(true);
            step2.setDisable(true);
            step3.setDisable(true);
        } else {
            step1.setDisable(false);
        }

        // FIXME get NullPointer exception when inserting non primable number like 3231

    }
    @FXML
    private void s1_validateSelfEnteredPandQ() {
        String inputP = generatedPForN.getText();
        String inputQ = generatedQForN.getText();


        if (inputP.isEmpty() || inputQ.isEmpty()) {
            infoArea.setText("p and q is not entered.");
            step2.setDisable(true);
            step3.setDisable(true);
        } else {
            step2.setDisable(false);
                try {
                    Long p = Long.parseLong(inputP);
                    Long q = Long.parseLong(inputQ);
                    Long pq = p * q;

                    if (HelperMethods.findPQ(pq) == null) {
                        generatedE.setText("");
                        infoArea.setText("The combination of p and q are invalid.");

                    } else if(p == 0.0 || q == 0.0) {
                        generatedE.setText("");
                        infoArea.setText("The combination of p and q are invalid.");
                    } else {
                        infoArea.setText("New n has been calculated using p and q.");
                        givenN.setText(pq.toString());
                    }
                } catch(NumberFormatException e) {
                    givenN.setText("");
                    infoArea.setText("p and/or q is empty.");
                }
            }



    }

    @FXML
    private void s2_CalculateE() throws IOException {

        // FIXME: p and q is validated but if numbers are too big program will get stuck
        if (generatedPForN.getText().isEmpty() || generatedQForN.getText().isEmpty()) {
            infoArea.setText("p and q is not entered.");
        } else {
            Long p = Long.parseLong(generatedPForN.getText());
            Long q = Long.parseLong(generatedQForN.getText());

            HelperMethods.listOfE = HelperMethods.calculatePublicKeyE(p, q);
            Integer numberOfE = HelperMethods.listOfE.size();
            Long e = HelperMethods.listOfE.get(HelperMethods.getRandomNumber());
            generatedE.setText(e.toString());
            HelperMethods.e = e;
            step3.setDisable(false);
        }
    }

    @FXML
    private void s2_validateSelfEnteredE() {
        String inputGeneratedE = generatedE.getText();
        if (inputGeneratedE.isEmpty()) {
            step3.setDisable(true);
        } else {
            Long e = Long.parseLong(generatedE.getText());
            if(HelperMethods.isValidE(e) == false) {
                infoArea.setText("E is not valid");
            } else {
                infoArea.setText("E has been approved.");
                step2.setDisable(false);
            }

        }

    }

    @FXML
    private void s3_CalculateCipher() {
        String message = messageTextbox.getText();
        char[] messageArray = message.toCharArray();
        ArrayList<Long> messageToNumericArray = new ArrayList<Long>();
        ArrayList<String> messageToCipher = new ArrayList<String>();
        Long e = HelperMethods.e;
        Long n = Long.parseLong(givenN.getText());

        for (char character : messageArray) {
            Integer numeric = (int)character;
            messageToNumericArray.add(Long.parseLong(numeric.toString()));
        } // Dit klopt

        System.out.println(messageToNumericArray.toString());
        for (Long m : messageToNumericArray) {
            Long cipher = HelperMethods.extendedModularArithmetic(m, e, n);
            messageToCipher.add(String.valueOf(cipher.intValue()));
        }

        String cipher = String.join(",", messageToCipher);
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
