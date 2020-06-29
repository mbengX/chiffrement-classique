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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author Nicodeme Mbeng
 */
public class RootViewController implements Initializable {
    
    @FXML
    private ComboBox<String> chiffrementBox;
    
    @FXML
    private Button parametreButton;
    @FXML
    private Button chiffreButton;
    @FXML
    private Button dechiffreButton;
    
    @FXML
    private TextArea chiffreArea;
    @FXML
    private TextArea clairArea;
    @FXML
    private TextArea consoleArea;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public ComboBox<String> getChiffrementBox() {
        return chiffrementBox;
    }

    public Button getParametreButton() {
        return parametreButton;
    }

    public Button getChiffreButton() {
        return chiffreButton;
    }

    public Button getDechiffreButton() {
        return dechiffreButton;
    }

    public TextArea getChiffreArea() {
        return chiffreArea;
    }

    public TextArea getClairArea() {
        return clairArea;
    }

    

    public TextArea getConsoleArea() {
        return consoleArea;
    }
    
    
}
