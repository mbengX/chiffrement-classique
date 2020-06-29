package cryptographie.chiffrement.des;

import cryptographie.chiffrement.Chiffrement;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;

public class DesChiffrement extends Chiffrement{
  Cipher ecipher;

  Cipher dcipher;

    public DesChiffrement(SecretKey key) throws Exception {
      ecipher = Cipher.getInstance("DES");
      dcipher = Cipher.getInstance("DES");
      ecipher.init(Cipher.ENCRYPT_MODE, key);
      dcipher.init(Cipher.DECRYPT_MODE, key);
    }

    @Override
    public String chiffre(String texteClair) {
      try {
          // Encode the string into bytes using utf-8
          byte[] utf8 = texteClair.getBytes("UTF8");
          
          // Encrypt
          byte[] enc = ecipher.doFinal(utf8);
          
          // Encode bytes to base64 to get a string
          return new sun.misc.BASE64Encoder().encode(enc);
      } catch (UnsupportedEncodingException ex) {
          Logger.getLogger(DesChiffrement.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IllegalBlockSizeException ex) {
          Logger.getLogger(DesChiffrement.class.getName()).log(Level.SEVERE, null, ex);
      } catch (BadPaddingException ex) {
          Logger.getLogger(DesChiffrement.class.getName()).log(Level.SEVERE, null, ex);
      }
      
      return null;
    }

    @Override
    public String dechiffre(String texteChiffre) {
      try {
          // Decode base64 to get bytes
          byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(texteChiffre);
          
          byte[] utf8 = dcipher.doFinal(dec);
          
          // Decode using utf-8
          return new String(utf8, "UTF8");
      } catch (IOException ex) {
          Logger.getLogger(DesChiffrement.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IllegalBlockSizeException ex) {
          Logger.getLogger(DesChiffrement.class.getName()).log(Level.SEVERE, null, ex);
      } catch (BadPaddingException ex) {
          Logger.getLogger(DesChiffrement.class.getName()).log(Level.SEVERE, null, ex);
      }
      return null;
    }
}

/*public class Main {
  public static void main(String[] argv) throws Exception {
    SecretKey key = KeyGenerator.getInstance("DES").generateKey();
    DesEncrypter encrypter = new DesEncrypter(key);
    String encrypted = encrypter.encrypt("Don't tell anybody!");
    String decrypted = encrypter.decrypt(encrypted);
  }
}*/