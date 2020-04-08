package rsa.decoding;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
