/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptographie.chiffrement.hill;

import cryptographie.chiffrement.Chiffrement;

/**
 *
 * @author Nicodeme Mbeng
 */
public class Hill extends Chiffrement{
    private int[][] cle;
    private int[][] invCle;

    public Hill(int[][] cle) throws Exception {
        this.cle = cle;
        int det = cle[0][0]*cle[1][1] - cle[1][0]*cle[0][1];
        
        if(pgcd(det, ALPHABET.length()) !=1)
            throw new Exception("La matrice est incorrecte ! Entrez une matrice dont le determinant est premier avec la taille de l'alphabet="+ALPHABET.length());
        
    }
    
    public Hill(int a, int b, int c, int d) throws Exception{
        int[][] tab = {
            {a, b},
            {c, d}};
        
        this.cle = tab;
        int det = cle[0][0]*cle[1][1] - cle[1][0]*cle[0][1];
        
        if(pgcd(det, ALPHABET.length()) !=1)
            throw new Exception("La matrice est incorrecte ! Entrez une matrice dont le determinant est premier avec la taille de l'alphabet="+ALPHABET.length());
        
        inverseCle();
    }

    @Override
    public String chiffre(String texteClair) {
        String chiffre = "";
        
        for(int i=0; i<texteClair.length(); i+=2){
            if(texteClair.charAt(i)!=' '){
                chiffre += value((cle[0][0]*index(texteClair.charAt(i))+cle[0][1]*index(texteClair.charAt(i+1)))%ALPHABET.length());
                chiffre += value((cle[1][0]*index(texteClair.charAt(i))+cle[1][1]*index(texteClair.charAt(i+1)))%ALPHABET.length()); 
            }
            else
                chiffre += " ";
        }
        
        return chiffre;
    }

    @Override
    public String dechiffre(String texteChiffre) {
        int det = cle[0][0]*cle[1][1] - cle[1][0]*cle[0][1];
        int invdet = inverse(Math.abs(det));
        invdet = det>0 ? invdet:-1*invdet;
        
        int[][] invCle = new int[2][2];
        invCle[0][0] = cle[1][1];
        invCle[1][1] = cle[0][0];
        invCle[0][1] = cle[0][1]*-1;
        invCle[1][0] = cle[1][0]*-1;
        
        String dechiffre = "";
        
        for(int i=0; i<texteChiffre.length(); i+=2){
            if(texteChiffre.charAt(i)!=' '){
                dechiffre += value(modulo((invdet*invCle[0][0]*index(texteChiffre.charAt(i))+invdet*invCle[0][1]*index(texteChiffre.charAt(i+1))), ALPHABET.length()));
                //System.out.println((invdet*invCle[1][0]*index(texteChiffre.charAt(i))+invdet*invCle[1][1]*index(texteChiffre.charAt(i+1)))%26);
                dechiffre += value(modulo((invdet*invCle[1][0]*index(texteChiffre.charAt(i))+invdet*invCle[1][1]*index(texteChiffre.charAt(i+1))), ALPHABET.length())); 
            }
            else
                dechiffre += " ";
        }
        
        return dechiffre;
    }
    
    private void inverseCle(){
        int det = cle[0][0]*cle[1][1] - cle[1][0]*cle[0][1];
        int invdet = inverse(Math.abs(det));
        invdet = det>0 ? invdet:-1*invdet;
        
        invCle = new int[2][2];
        invCle[0][0] = cle[1][1];
        invCle[1][1] = cle[0][0];
        invCle[0][1] = cle[0][1]*-1;
        invCle[1][0] = cle[1][0]*-1;
        
        invCle[0][0] = modulo(invCle[0][0], ALPHABET.length());
        invCle[1][1] = modulo(invCle[1][1], ALPHABET.length());
        invCle[0][1] = modulo(invCle[0][1], ALPHABET.length());
        invCle[1][0] = modulo(invCle[1][0], ALPHABET.length());
    }

    public int[][] getInvCle() {
        return invCle;
    }
}
