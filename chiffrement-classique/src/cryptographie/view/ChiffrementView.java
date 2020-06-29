/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptographie.view;

import cryptographie.chiffrement.Chiffrement;
import cryptographie.chiffrement.des.*;
import cryptographie.chiffrement.affine.Affine;
import cryptographie.chiffrement.cesar.Cesar;
import cryptographie.chiffrement.fair.Playfair;
import cryptographie.chiffrement.hill.Hill;
import cryptographie.chiffrement.viginere.Viginere;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.AnchorPane;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 *
 * @author Nicodeme Mbeng
 */
public class ChiffrementView {
    private AnchorPane pane;
    private RootViewController controller;
    private Chiffrement chiffrement = null;
    
    private static String[] listeDeChiffrement = {"Chiffrement de César", "Chiffrement de Viginère",
                    "Chiffrement Affine", "Chiffrement de Hill", "Chiffre de Playfair", "Chiffrement DES", "Chiffrement AES"};

    public ChiffrementView() {
        initPane();
        fillPane();
    }
    
    private void initPane(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("RootView.fxml"));
            pane = (AnchorPane) loader.load();
            
            controller = loader.getController();
        } catch (IOException e) {
            System.out.println("cryptographie.view.ChiffrementView.initPane()");
            e.printStackTrace();
        }
    }
    
    private void fillPane(){
        for(int i=0; i<listeDeChiffrement.length; i++){
            controller.getChiffrementBox().getItems().add(listeDeChiffrement[i]);
        }
        
        controller.getChiffrementBox().setValue(listeDeChiffrement[0]);
        
        controller.getParametreButton().setOnAction((event) -> {
            controlleParametre();
        });
        
        controller.getChiffreButton().setOnAction((event) -> {
            controlleChiffre();
        });
        
        controller.getDechiffreButton().setOnAction((event) -> {
            controlleDechiffre();
        });
    }
    
    private void controlleParametre(){
        try {
            if(controller.getChiffrementBox().getValue().equals(listeDeChiffrement[0])){//César
                String cle = cesaireDialogParametre();

                if(cle!=null){
                    cle = cle.toUpperCase();
                    String info = "Chiffrement de César\n"+
                                  "----------------------\n"+
                        "clef = "+cle.charAt(0);
                    controller.getConsoleArea().setText(info);
                    chiffrement = new Cesar(Chiffrement.index(cle));
                }
            }
            else if(controller.getChiffrementBox().getValue().equals(listeDeChiffrement[1])){//Viginère
                String cle = viginereDialogParametre();
                
                if(cle != null){
                    cle = cle.toUpperCase();
                    String info = "Chiffrement de Viginère\n"+
                                  "-------------------------\n"+
                        "clef = "+cle;
                    controller.getConsoleArea().setText(info);
                    chiffrement = new Viginere(cle);
                }
            }
            else if(controller.getChiffrementBox().getValue().equals(listeDeChiffrement[2])){//Affine
                String[] cle = affineDialogParametre();
                
                if(cle != null){
                    String info = "Chiffrement Affine\n"+
                                  "-------------------\n";
                    
                    int a = 0, b = 0;
                    
                    try {
                        a = Integer.parseInt(cle[0]);
                        b = Integer.parseInt(cle[1]);
                        
                        info += "a = " + a +
                            "\nb = " + b + "\n";
                        
                        try {
                            chiffrement = new Affine(a, b);
                        } catch (Exception ex) {
                            info += "Le paramètre a = "+a+" n'est pas premier avec la taille de l'alphabet ("+Chiffrement.ALPHABET.length()+").\n";
                        }
                    } catch (NumberFormatException e) {
                        info += "a = " + cle[0] +
                            "\nb = " + cle[1] + "\n";
                        info += "Les paramètres entrés sont invalides ! Entrez des données valides.\n";
                    }
                    
                    controller.getConsoleArea().setText(info);
                }
            }
            else if(controller.getChiffrementBox().getValue().equals(listeDeChiffrement[3])){//Hill
                String[] cle = hillDialogParametre();
                
                if(cle != null){
                    String info = "Chiffrement de Hill\n"+
                                  "--------------------\n";
                    
                    int a = 0, b = 0, c = 0, d = 0;
                    
                    try {
                        a = Integer.parseInt(cle[0]);
                        b = Integer.parseInt(cle[1]);
                        c = Integer.parseInt(cle[2]);
                        d = Integer.parseInt(cle[3]);
                        
                        info += 
                                "    |"+a+" "+b+"\n"+
                                "M = |\n"+
                                "    |"+c+" "+d+"\n\n";
                        
                        try {
                            chiffrement = new Hill(a, b, c, d);
                            
                            int[][] invcle = ((Hill)chiffrement).getInvCle();
                            
                            info += "det_M = "+(a*d-b*c)+".\n"+
                                    "        |"+invcle[0][0]+" "+invcle[0][1]+"\n"+
                                    "inv_M = |\n"+
                                    "        |"+invcle[1][0]+" "+invcle[1][1]+"\n";;
                            
                        } catch (Exception ex) {
                            info += "Le determinant de la matrice det = "+(a*d-b*c)+" n'est pas premier avec la taille de l'alphabet ("+Chiffrement.ALPHABET.length()+").\n";
                        }
                    } catch (NumberFormatException e) {
                        info += 
                                "    |"+cle[0]+" "+cle[1]+"\n"+
                                "M = |\n"+
                                "    |"+cle[2]+" "+cle[3]+"\n";
                        info += "Les paramètres entrés sont invalides ! Entrez des données valides.\n";
                    }
                    
                    controller.getConsoleArea().setText(info);
                }
            }
            else if(controller.getChiffrementBox().getValue().equals(listeDeChiffrement[4])){//Playfair
                String cle = playfairDialogParametre("Paramètre chiffrement Playfair");
                
                if(cle != null){
                    cle = cle.toUpperCase();
                    String info = "Chiffrement de Playfair\n"+
                                  "------------------------\n"+
                        "clef = "+cle+"\n";
                    
                    chiffrement = new Playfair(cle);
                    info+="Matrice de chiffrement :\n"+((Playfair)chiffrement).getStringTable();
                    controller.getConsoleArea().setText(info);
                }
            }
            else if(controller.getChiffrementBox().getValue().equals(listeDeChiffrement[5])){//DES
                String cle = playfairDialogParametre("Paramètre chiffrement DES");
                
                if(cle != null){
                    cle = cle.toUpperCase();
                    String info = "Chiffrement DES\n"+
                                  "------------------------\n"+
                        "clef = "+cle+"\n";
                    
                    SecretKey key = KeyGenerator.getInstance("DES").generateKey();
                    
                    chiffrement = new DesChiffrement(key);
                    controller.getConsoleArea().setText(info);
                }
            }
            else{
                controller.getConsoleArea().setText("Ce chiffrement n'est pas encore disponible.");
            }
        } catch (IOException ex) {
            Logger.getLogger(ChiffrementView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ChiffrementView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ChiffrementView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void controlleChiffre(){
        if(chiffrement != null){
            String textClair = controller.getClairArea().getText().toUpperCase();
            controller.getChiffreArea().setText(chiffrement.chiffre(textClair));
            System.out.println(chiffrement.chiffre(textClair));
        }
        else{
            controller.getConsoleArea().setText("Entrez des paramètres correctes pour le chiffrement selectionné !");
            System.out.println("Entrez des paramètres correctes pour le chiffrement selectionné !");
        }
    }
    
    private void controlleDechiffre(){
        if(chiffrement != null){
            String textChiffre = controller.getChiffreArea().getText().toUpperCase();
            controller.getClairArea().setText(chiffrement.dechiffre(textChiffre));
            System.out.println(chiffrement.dechiffre(textChiffre));
        }
        else{
            controller.getConsoleArea().setText("Entrez des paramètres correctes pour le chiffrement selectionné !");
            System.out.println("Entrez des paramètres correctes pour le chiffrement selectionné !");
        }
    }
    
    private String cesaireDialogParametre() throws IOException{
        String cle = "";
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Paramètre");
        
        ButtonType okButtonType = new ButtonType("OK", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
        
        //set pane
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("ParamCesaire.fxml"));
        AnchorPane cesairePane = (AnchorPane) loader.load();
        ParamCesaireController cesaireController = loader.getController();
        
        dialog.getDialogPane().setContent(cesairePane);
        dialog.setResultConverter(dialogButton->{
            if(okButtonType == dialogButton){
                try{
                    return cle.replaceFirst("", cesaireController.getCleField().getText());
                }
                catch(Exception e){
                    return null;
                }
            }
            return null;
        });
        
        Optional<String> selected = dialog.showAndWait();
        if(selected.isPresent()){
            return selected.get();
        }
        
        return null;
    }
    
    private String viginereDialogParametre() throws IOException{
        String cle = "";
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Paramètre");
        
        ButtonType okButtonType = new ButtonType("OK", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
        
        //set pane
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("ParamViginere.fxml"));
        AnchorPane cesairePane = (AnchorPane) loader.load();
        ParamViginereController cesaireController = loader.getController();
        
        dialog.getDialogPane().setContent(cesairePane);
        dialog.setResultConverter(dialogButton->{
            if(okButtonType == dialogButton){
                try{
                    return cle.replaceFirst("", cesaireController.getCleField().getText());
                }
                catch(Exception e){
                    return null;
                }
            }
            return null;
        });
        
        Optional<String> selected = dialog.showAndWait();
        if(selected.isPresent()){
            return selected.get();
        }
        
        return null;
    }
    
    private String playfairDialogParametre(String titre) throws IOException{
        String cle = "";
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Paramètre");
        
        ButtonType okButtonType = new ButtonType("OK", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
        
        //set pane
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("ParamViginere.fxml"));
        AnchorPane cesairePane = (AnchorPane) loader.load();
        ParamViginereController playfairController = loader.getController();
        
        playfairController.getTitreLabel().setText(titre);
        
        dialog.getDialogPane().setContent(cesairePane);
        dialog.setResultConverter(dialogButton->{
            if(okButtonType == dialogButton){
                try{
                    return cle.replaceFirst("", playfairController.getCleField().getText());
                }
                catch(Exception e){
                    return null;
                }
            }
            return null;
        });
        
        Optional<String> selected = dialog.showAndWait();
        if(selected.isPresent()){
            return selected.get();
        }
        
        return null;
    }
    
    private String[] affineDialogParametre() throws IOException{
        Dialog<String[]> dialog = new Dialog<>();
        dialog.setTitle("Paramètre");
        
        ButtonType okButtonType = new ButtonType("OK", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
        
        //set pane
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("ParamAffine.fxml"));
        AnchorPane cesairePane = (AnchorPane) loader.load();
        ParamAffineController affineController = loader.getController();
        
        dialog.getDialogPane().setContent(cesairePane);
        dialog.setResultConverter(dialogButton->{
            if(okButtonType == dialogButton){
                try{
                    return affineController.getParam();
                }
                catch(Exception e){
                    return null;
                }
            }
            return null;
        });
        
        Optional<String[]> selected = dialog.showAndWait();
        if(selected.isPresent()){
            return selected.get();
        }
        
        return null;
    }
    
    private String[] hillDialogParametre() throws IOException{
        Dialog<String[]> dialog = new Dialog<>();
        dialog.setTitle("Paramètre");
        
        ButtonType okButtonType = new ButtonType("OK", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
        
        //set pane
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("ParamHill.fxml"));
        AnchorPane cesairePane = (AnchorPane) loader.load();
        ParamHillController hillController = loader.getController();
        
        dialog.getDialogPane().setContent(cesairePane);
        dialog.setResultConverter(dialogButton->{
            if(okButtonType == dialogButton){
                try{
                    return hillController.getParam();
                }
                catch(Exception e){
                    return null;
                }
            }
            return null;
        });
        
        Optional<String[]> selected = dialog.showAndWait();
        if(selected.isPresent()){
            return selected.get();
        }
        
        return null;
    }

    public AnchorPane getPane() {
        return pane;
    }
    
    
}
