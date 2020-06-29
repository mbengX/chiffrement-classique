/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptographie.chiffrement.affine;

import cryptographie.chiffrement.Chiffrement;

/**
 *
 * @author Nicodeme Mbeng
 */
public class Affine extends Chiffrement{
    private int b;
    private String alphabet;
    
    public Affine(int a, int b) throws Exception {
        alphabet = Chiffrement.ALPHABET;
        if(pgcd(a, alphabet.length()) !=1)
            throw new Exception("Premier argument incorrect! entrez un nombre premier avec la taille de l'alphabet="+alphabet.length());
        
        cle = a;
        this.b = b;
    }
    
    @Override
    public String chiffre(String texteClair) {
        String chiffre = "";
        
        for(int i=0; i<texteClair.length(); i++){
            if(texteClair.charAt(i)!=' ')
                chiffre += alphabet.charAt((alphabet.indexOf(texteClair.charAt(i))*(Integer)cle+b)%alphabet.length())+"";
            else
                chiffre += " ";
        }
        
        return chiffre;
    }

    @Override
    public String dechiffre(String texteChiffre) {
        int a_1 = inverse((int) cle);
        String dechiffre = "";
        
        for(int i=0; i<texteChiffre.length(); i++){
            if(texteChiffre.charAt(i) != ' ')
                dechiffre += alphabet.charAt(modulo((a_1*(alphabet.indexOf(texteChiffre.charAt(i))-b)), alphabet.length()))+"";
            else
                dechiffre += " ";
        }
        
        return dechiffre;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }
    
    
}
