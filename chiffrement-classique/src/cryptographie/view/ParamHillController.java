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
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Nicodeme Mbeng
 */
public class ParamHillController implements Initializable {
    @FXML
    private TextField aField;
    @FXML
    private TextField bField;
    @FXML
    private TextField cField;
    @FXML
    private TextField dField;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public String[] getParam(){
        String[] cle = new String[4];
        
        cle[0] = aField.getText();
        cle[1] = bField.getText();
        cle[2] = cField.getText();
        cle[3] = dField.getText();
        
        return cle;
    }
}
