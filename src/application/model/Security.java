package application.model;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Security {
    public static String getMd5(String pInput) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(pInput.getBytes());
            BigInteger lHashInt = new BigInteger(1, digest.digest());
            return String.format("%1$032X", lHashInt);
        } catch (NoSuchAlgorithmException lException) {
            throw new RuntimeException(lException);
        }
    }

}
