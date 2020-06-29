/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptographie.chiffrement;

/**
 *
 * @author Nicodeme Mbeng
 */
public abstract class Chiffrement {
    public static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
    protected Object cle;
    
    public abstract String chiffre(String texteClair);
    public abstract String dechiffre(String texteChiffre);

    public Object getCle() {
        return cle;
    }

    public void setCle(Object cle) {
        this.cle = cle;
    }
    
    protected int pgcd(int a, int b){
        if(b == 0)
            return a;
        else{
            int r = a%b;
            return pgcd(b, r);
        }
    }
    
    protected int inverse(int n){
        int inv = 0;
        int i=1;
        
        while(i<ALPHABET.length() && inv == 0){
            if(((i*n)%ALPHABET.length()) == 1)
                inv = i;            
            i+=2;
        }
        return inv;
    }
    
    public static int index(char caractere){
        return ALPHABET.indexOf(caractere);
    }
    
    public static int index(String caractere){
        return index(caractere.charAt(0));
    }
    
    protected String value(int index){
        return ALPHABET.charAt(index)+"";
    }
    
    public int modulo(int a, int b){
        if(a>=0)
            return a%b;
        else{
            int reste = a%b;
            int quotient = Math.abs(reste)/b;
            return (quotient+1)*b + reste;
        }
    }
}
