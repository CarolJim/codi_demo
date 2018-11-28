package com.pagatodo.richardsproject.commons;

import android.content.Context;
import android.provider.Settings;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

public class Utils {

    public static String getTokenDevice(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String objectToString(Serializable obj) {
        if (obj == null)
            return "";
        try {
            ByteArrayOutputStream serialObj = new ByteArrayOutputStream();
            ObjectOutputStream objStream;
            objStream = new ObjectOutputStream(serialObj);
            objStream.writeObject(obj);
            objStream.close();
            return Codec.asHexStr(serialObj.toByteArray());
        } catch (IOException e) {
            return null;
        }
    }

    public static Serializable stringToObject(String str) {
        if (str == null || str.length() == 0)
            return null;
        try {
            ByteArrayInputStream serialObj = new ByteArrayInputStream(
                    Codec.asBytes(str));
            ObjectInputStream objStream;
            objStream = new ObjectInputStream(serialObj);
            return (Serializable) objStream.readObject();
        } catch (Exception e) {
            return null;
        }
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

    public static String getSHA256(String msg) {
        String ans = null;
        try {
            MessageDigest md;
            md = MessageDigest.getInstance("SHA256");
            md.update(msg.getBytes());
            ans = Utils.bytesToHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            ans = String.valueOf(msg.hashCode());
        }

        return ans;
    }

    public static String bytesToHex(byte[] b) {
        char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        StringBuffer buf = new StringBuffer();
        for (int j = 0; j < b.length; j++) {
            buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
            buf.append(hexDigit[b[j] & 0x0f]);
        }
        return buf.toString();
    }
}
