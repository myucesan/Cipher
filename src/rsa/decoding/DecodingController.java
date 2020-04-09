package rsa.decoding;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import rsa.HelperMethods;

import java.util.ArrayList;

public class DecodingController {


    @FXML TextField givenN;
    @FXML TextField givenE;
    @FXML TextField calculatedD;
    @FXML TextField messageTextbox;
    @FXML TextField cipherTextbox;
    @FXML Label encLabel;

    @FXML
    private void s1_calculateD() {
        ArrayList<Double> primeNumbers = HelperMethods.findPQ(Double.parseDouble(givenN.getText()));
        Double p = 0.0;
        Double q = 0.0;
        Double n = Double.parseDouble(givenN.getText());
        if(primeNumbers != null) {
            p = primeNumbers.get(0);
            q = primeNumbers.get(1);
        }
        calculatedD.setText(HelperMethods.calculateDecryptionKey(p, q, HelperMethods.e).toString());
    }

    @FXML
    private void s2_decryptMessage() {
        String[] ciphertext = cipherTextbox.getText().split(" ");
        ArrayList<Double> cipher = new ArrayList<Double>();
        Double d = Double.parseDouble(calculatedD.getText());
        Double n = Double.parseDouble(givenN.getText());

        for(String c : ciphertext) {
            cipher.add(Double.parseDouble(c));
        }
        ArrayList<Double> decrypted = HelperMethods.decrypt(cipher, d, n);
        String decryptedMessage = "";
        for(Double decrypt : decrypted) {
            decryptedMessage += (char)decrypt.intValue();
        }
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
