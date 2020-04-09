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

    @FXML
    javafx.scene.control.Button Button;
    @FXML
    TextField givenN;

    @FXML
    TextField givenE;

    @FXML
    TextField calculatedD;
    @FXML
    TextField messageTextbox;

    @FXML
    TextField cipherTextbox;

    @FXML
    Label encLabel;

    @FXML
    private void s1_calculateD() {

    }

    @FXML
    private void s2_decryptMessage() {

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
     *   - e is the secondPublicKey
     *   - phi is (p – 1)(q – 1), where p is firstPrimenumber and q is secondPrimeNumber
     * ----------------------------------------------------------------------
     *
     *   Calculates the decryption key d
     *
     *   @param      firstPrimeNumber: the p number
     *   @param      secondPrimeNumber: the q number
     *   @param      secondPublicKey: the public key e which was calculated before
     *   @return     the decryption key d
     */
    public static Double calculateDecryptionKey(Double firstPrimeNumber, Double secondPrimeNumber, Double secondPublicKey) {
        Double phiNumber = (firstPrimeNumber - 1) * (secondPrimeNumber - 1);
        Double d = -1.0;
        Integer counter = 1;

        while (true) {
            d = (1 + counter * phiNumber) / secondPublicKey;
            if (HelperMethods.checkIfInteger(d)) {
                return d;
            }
            counter++;
        }

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
