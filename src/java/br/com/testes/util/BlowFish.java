/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.testes.util;

/**
 *
 * @author carlos.macedo
 */
import javax.crypto.*;
import javax.crypto.spec.*;

public class BlowFish {

    private static String passfrase = "achavedeveter16";

    public static final String cript(String str) {
        String strCript = str;

        try {
            Cipher ch = Cipher.getInstance("Blowfish");
            SecretKey k1 = new SecretKeySpec(passfrase.getBytes(), "Blowfish");

            //criptografando  
            ch.init(Cipher.ENCRYPT_MODE, k1);
            byte b[] = ch.doFinal(strCript.getBytes());
            //System.out.println("B: " + b);  
            String s1 = Conversion.byteArrayToBase64String(b);
            strCript = s1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return strCript;
    }

    public static final String decript(String str) {
        String strDecript = str;

        try {
            Cipher ch = Cipher.getInstance("Blowfish");
            SecretKey k1 = new SecretKeySpec(passfrase.getBytes(), "Blowfish");

            //decriptografando  
            ch.init(Cipher.DECRYPT_MODE, k1);
            byte b[] = Conversion.base64StringToByteArray(strDecript);
            byte b1[] = ch.doFinal(b);
            String s1 = new String(b1);
            strDecript = s1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return strDecript;
    }
}
