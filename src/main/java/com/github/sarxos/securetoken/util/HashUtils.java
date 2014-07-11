package com.github.sarxos.securetoken.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by benjamin on 11/07/14.
 */
public class HashUtils {
    private HashUtils() {
    }

    public static final String hashInMD5(String str) {
        try  {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte byteData[] = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch(NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }
}
