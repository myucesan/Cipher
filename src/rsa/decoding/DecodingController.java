package rsa.decoding;

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

import java.util.ArrayList;

public class DecodingController {


    @FXML TextField givenN;
    @FXML TextField calculatedD;
    @FXML TextField messageTextbox;
    @FXML TextField cipherTextbox;
    @FXML Label encLabel;
    @FXML TextField calculatedE;
    @FXML Button step1;
    @FXML TextArea infoArea;

    @FXML
    private void s1_calculateD() {
        // TODO: check if e is actually generated
//        if(HelperMethods.e != null) {
//            calculatedE.setText(HelperMethods.e.toString());
//        } else {
//            calculatedE.setText("9011");
//            infoArea.setText("No e has been calculated while encoding, please enter own e.");
//
//        }
        Long n = Long.parseLong(givenN.getText());
        Long e = Long.parseLong(calculatedE.getText());

        ArrayList<Long> primeNumbers = HelperMethods.findPQ(n);
        calculatedD.setText(primeNumbers.toString());


        Long p = 0L;
        Long q = 0L;

        System.out.println("e is: " + e);
        if(primeNumbers != null) {
            p = primeNumbers.get(0);
            q = primeNumbers.get(1);
        }



        calculatedD.setText(HelperMethods.calculateDecryptionKey(e, n).toString());
    }

    @FXML
    private void s1_validateSelfEnteredE() {
//        String inputGeneratedE = calculatedE.getText();
//        if (inputGeneratedE.isEmpty()) {
//            step1.setDisable(true);
//        } else {
//            Double e = Double.parseDouble(calculatedE.getText());
//            if(HelperMethods.isValidE(e) == false) {
//                infoArea.setText("E is not valid");
////                HelperMethods.e = 0.0;
//            } else {
//                infoArea.setText("E has been approved.");
//                HelperMethods.e = e;
//                step1.setDisable(false);
//            }
//        }

    }

    @FXML
    private void s2_decryptMessage() {
        String[] ciphertext = cipherTextbox.getText().split(",");
        ArrayList<Long> cipher = new ArrayList<Long>();
        Long d = Long.parseLong(calculatedD.getText());
        Long n = Long.parseLong(givenN.getText());

        for(String c : ciphertext) {
            cipher.add(Long.parseLong(c));
        }
        ArrayList<Long> decrypted = HelperMethods.decrypt(cipher, d, n);
        String decryptedMessage = "";
        for(Long decrypt : decrypted) {
            decryptedMessage += (char)decrypt.intValue();
        }
        System.out.println(decryptedMessage);
        messageTextbox.setText(decryptedMessage);
    }





    @FXML
    private void switchToEncoding() throws Exception {
        Stage stage = (Stage) encLabel.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../encoding/encoding.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Cipher v0.0.1 | Encoding");
        stage.setScene(scene);
        stage.show();
    }
}
