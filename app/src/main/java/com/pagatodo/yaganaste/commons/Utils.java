package com.pagatodo.yaganaste.commons;

import android.content.Context;
import android.provider.Settings;
import android.util.Base64;

import org.apache.commons.codec.binary.Hex;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Utils {

    public static String getTokenDevice(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String cipherRSA(String text, String rsaKey) {
        String result;
        try {
            byte[] expBytes = Base64.decode("AQAB".getBytes("UTF-8"), Base64.DEFAULT);
            byte[] modBytes = Base64.decode(rsaKey.getBytes("UTF-8"), Base64.DEFAULT);

            BigInteger modules = new BigInteger(1, modBytes);
            BigInteger exponent = new BigInteger(1, expBytes);

            KeyFactory factory = KeyFactory.getInstance("RSA");
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");

            RSAPublicKeySpec pubSpec = new RSAPublicKeySpec(modules, exponent);

            PublicKey pubKey = factory.generatePublic(pubSpec);
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            byte[] encrypted = cipher.doFinal(text.getBytes());
            result = Base64.encodeToString(encrypted, Base64.DEFAULT);
        } catch (Exception e) {
            result = null;
        }
        return result;
    }

    public static String Aes128CbcPkcs(String key, String initVector, String value, int mode) {
        String result;
        try {
            IvParameterSpec iv = new IvParameterSpec(Hex.decodeHex(initVector.toCharArray()));
            SecretKeySpec skeySpec = new SecretKeySpec(Hex.decodeHex(key.toCharArray()), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(mode, skeySpec, iv);
            if (mode == Cipher.ENCRYPT_MODE) {
                byte[] encrypted = cipher.doFinal(value.getBytes());
                result = Base64.encodeToString(encrypted, Base64.DEFAULT);
            } else {
                byte[] original = cipher.doFinal(Base64.decode(value, Base64.DEFAULT));
                result = new String(original);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            result = null;
        }
        return result;
    }


    public static String Sha512Hex(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String HmacSha256(String key, String data) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(Hex.decodeHex(key.toCharArray()), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] encrypted = sha256_HMAC.doFinal(data.getBytes());
        return Base64.encodeToString(encrypted, Base64.DEFAULT);
    }
}
