package sample;

import com.sun.xml.internal.ws.api.FeatureConstructor;
import javafx.fxml.FXML;

import javafx.*;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    javafx.scene.control.Button Button;
    @FXML
    TextField n;
    @FXML
    TextField generatedPForN;

    @FXML
    TextField generatedQForN;

    @FXML
    private void s1_CalculatePandQ() {
        Integer number = Integer.parseInt(n.getText());
        generatedPForN.setText(number.toString());
        generatedQForN.setText(number.toString());

        // perform Euclids algorithm on "number" and that results in p and q.

    }
}
