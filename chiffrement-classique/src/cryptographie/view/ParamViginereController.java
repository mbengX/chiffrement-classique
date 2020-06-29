/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptographie.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Nicodeme Mbeng
 */
public class ParamViginereController implements Initializable {
    @FXML
    private TextField cleField;
    
    @FXML
    private Label titreLabel;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public TextField getCleField() {
        return cleField;
    }

    public Label getTitreLabel() {
        return titreLabel;
    }
    
    
}
