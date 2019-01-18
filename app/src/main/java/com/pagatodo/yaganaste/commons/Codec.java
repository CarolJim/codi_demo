package com.pagatodo.yaganaste.commons;

import android.util.Log;

import com.pagatodo.yaganaste.BuildConfig;

import java.util.zip.CRC32;

/**
 * @author carlos.
 * Created 28/08/2012.
 */
public class Codec {

    public static String asHexStr(byte buf[]) {
        StringBuffer strbuf = new StringBuffer(buf.length * 2);
        int i;
        for (i = 0; i < buf.length; i++) {
            if ((buf[i] & 0xff) < 0x10)
                strbuf.append("0");
            strbuf.append(Long.toString(buf[i] & 0xff, 16));
        }
        return strbuf.toString();
    }


    public static byte[] asBytes(String s) {
        String s2;
        byte[] b = new byte[s.length() / 2];
        int i;
        for (i = 0; i < s.length() / 2; i++) {
            s2 = s.substring(i * 2, i * 2 + 2);
            b[i] = (byte) (Integer.parseInt(s2, 16) & 0xff);
        }
        return b;
    }

    public static String applyCRC32(String s) {
        CRC32 crc = new CRC32();
        crc.update(s.getBytes());
        String encode = String.format("%08X", crc.getValue());
        if (BuildConfig.DEBUG)
            Log.i("CRC32", String.format("Source: %s ----- Result: %s", s, encode));
        return encode;

    }

}
