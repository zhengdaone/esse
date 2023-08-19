package com.fl.esse.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordGenerator {

    public static String getPassword(String password){
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        messageDigest.update((password).getBytes());
        byte[] byteBuffer = messageDigest.digest();
        return new String(Base64.getEncoder().encode(byteBuffer));
    }
}
