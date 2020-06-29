/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptographie.chiffrement.cesar;

import cryptographie.chiffrement.Chiffrement;

/**
 *
 * @author Nicodeme Mbeng
 */
public class Cesar extends Chiffrement{

    public Cesar(int cle) {
        this.cle = new Integer(cle);
    }
    
    @Override
    public String chiffre(String texteClair) {
        String chiffre = "";
        
        for(int i=0; i<texteClair.length(); i++){
            if(texteClair.charAt(i)!=' ')
                chiffre += ALPHABET.charAt(modulo((ALPHABET.indexOf(texteClair.charAt(i))+(Integer)cle), ALPHABET.length()));
            else
                chiffre += " ";
        }
        
        return chiffre;
    }

    @Override
    public String dechiffre(String texteChiffre) {
        String dechiffre = "";
        
        for(int i=0; i<texteChiffre.length(); i++){
            if(texteChiffre.charAt(i) != ' ')
                dechiffre += ALPHABET.charAt(modulo((ALPHABET.indexOf(texteChiffre.charAt(i))-(Integer)cle), ALPHABET.length()));
            else
                dechiffre += " ";
        }
        
        return dechiffre;
    }
    
    
}
