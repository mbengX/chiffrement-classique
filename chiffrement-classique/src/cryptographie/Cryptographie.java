/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptographie;

import cryptographie.chiffrement.fair.Playfair;
import cryptographie.view.ChiffrementView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Nicodeme Mbeng
 */
public class Cryptographie extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        ChiffrementView chiffrementView = new ChiffrementView();
        
        Scene scene = new Scene(chiffrementView.getPane());
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("Chiffrement classique");
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("file:icon.png"));
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch();
    }
    
}
