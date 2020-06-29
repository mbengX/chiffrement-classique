/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptographie.chiffrement.fair;

/**
 *
 * @author fodil
 */

import cryptographie.chiffrement.Chiffrement;
   import java.awt.Point;
import java.util.Scanner;

 
public class Playfair extends Chiffrement{
    public char[][] charTable;
    public static Point[] positions;
     static String key;
     boolean choix;
      static int l=0;

    public Playfair(String cle) {
        choix = false;
        key = cle;
        createTable(key, choix);
    }
 
      
 
    public static String prompt(String promptText, Scanner sc, int minLen) {
        String s;
        key=sc.toString();
        l=key.length();
        do {
            System.out.print(promptText);
            s = sc.nextLine().trim();
        } while (s.length() < minLen);
        return s;
    }
 
    public static String prepareText(String s, boolean changeJtoI) {
        s = s.toUpperCase().replaceAll("[^A-Z]", "");
        return changeJtoI ? s.replace("J", "I") : s.replace("W", "");
    }
 
    public  void createTable(String key, boolean changeJtoI) {
        charTable = new char[5][5];
        positions = new Point[26];
 
        String s = prepareText(key + "ABCDEFGHIJKLMNOPQRSTUVWXYZ", changeJtoI);
 
        int len = s.length();
        for (int i = 0, k = 0; i < len; i++) {
            char c = s.charAt(i);
            if (positions[c - 'A'] == null) {
                charTable[k / 5][k % 5] = c;
                positions[c - 'A'] = new Point(k % 5, k / 5);
                k++;
            }
        }
    }
 
    public  String chiffre(String s) {
        s = prepareText(s, choix);
        StringBuilder sb = new StringBuilder(s);
 
        for (int i = 0; i < sb.length(); i += 2) {
 
            if (i == sb.length() - 1)
                sb.append(sb.length() % 2 == 1 ? 'X' : "");
 
            else if (sb.charAt(i) == sb.charAt(i + 1))
                sb.insert(i + 1, 'X');
        }
        return codec(sb, 1);
    }
 
    public String dechiffre(String s) {
        return codec(new StringBuilder(s), 4);
    }
 
    public  String codec(StringBuilder text, int direction) {
        int len = text.length();
       
        for (int i = 0; i < len; i += 2) {
            char a = text.charAt(i);
            char b = text.charAt(i + 1);
 
            int row1 = positions[a - 'A'].y;
            int row2 = positions[b - 'A'].y;
            int col1 = positions[a - 'A'].x;
            int col2 = positions[b - 'A'].x;
 
            if (row1 == row2) {
                col1 = (col1 + direction) % 5;
                col2 = (col2 + direction) % 5;
 
            } else if (col1 == col2) {
                row1 = (row1 + direction) % 5;
                row2 = (row2 + direction) % 5;
 
            } else {
                int tmp = col1;
                col1 = col2;
                col2 = tmp;
            }
 
            text.setCharAt(i, charTable[row1][col1]);
            text.setCharAt(i + 1, charTable[row2][col2]);
        }
        
        
        
        return text.toString();
    }
    
    public String getStringTable(){
        String table = "";
        System.out.println("x*y="+charTable.length+" "+charTable[0].length);
        for(int i=0; i<charTable.length; i++){
            for(int j=0; j<charTable[0].length; j++){
                table+=charTable[i][j]+" ";
            }
            table+="\n";
        }
        
        return table;
    }
}
