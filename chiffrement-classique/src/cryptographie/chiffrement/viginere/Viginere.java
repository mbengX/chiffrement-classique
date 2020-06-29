/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptographie.chiffrement.viginere;

import cryptographie.chiffrement.Chiffrement;
import cryptographie.chiffrement.cesar.Cesar;

/**
 *
 * @author Nicodeme Mbeng
 */
public class Viginere extends Chiffrement{
    private String cle;

    public Viginere(String cle) {
        this.cle = cle;
    }
    
    
    
    @Override
    public String chiffre(String texteClair) {
        String chiffre = "";
        int[] freq = new int[26];
        
        int j=0;
        for(int i=0; i<texteClair.length(); i++){
            if(texteClair.charAt(i)!=' '){
                freq[index(texteClair.charAt(i))]++;
                Chiffrement cesar = new Cesar(index(cle.charAt(j%cle.length())));
                chiffre += cesar.chiffre(texteClair.charAt(i)+"");
            }
            else
                chiffre += " ";
            j++;
        }
        
        for(int i=0; i<texteClair.length(); i++)
            System.out.println(((double)freq[i])/texteClair.length());
        
        return chiffre;
    }

    @Override
    public String dechiffre(String texteChiffre) {
        String dechiffre = "";
        
        int j=0;
        for(int i=0; i<texteChiffre.length(); i++){
            if(texteChiffre.charAt(i)!=' '){
                Chiffrement cesar = new Cesar(index(cle.charAt(j%cle.length())));
                dechiffre += cesar.dechiffre(texteChiffre.charAt(i)+"");
            }
            else
                dechiffre += " ";
            j++;
        }
        
        return dechiffre;
    }
    
}
