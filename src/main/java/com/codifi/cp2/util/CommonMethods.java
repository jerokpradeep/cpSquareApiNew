package com.codifi.cp2.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.json.JSONObject;

public class CommonMethods {
    /**
     * Method to encrypt password using MD5
     * 
     * @param passkey
     * @return
     */
    public static String PasswordEncryption(String passkey) {
        String MD5pass = "";
        final byte[] defaultBytes = passkey.getBytes();
        try {
            final MessageDigest md5MsgDigest = MessageDigest.getInstance("MD5");
            md5MsgDigest.reset();
            md5MsgDigest.update(defaultBytes);
            final byte messageDigest[] = md5MsgDigest.digest();

            final StringBuffer hexString = new StringBuffer();
            for (final byte element : messageDigest) {
                final String hex = Integer.toHexString(0xFF & element);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            passkey = hexString + "";
        } catch (final NoSuchAlgorithmException nsae) {
            nsae.printStackTrace();
        }
        MD5pass = passkey;
        return MD5pass;
    }

    /**
     * Method to populate failed responses Message
     */
    public static JSONObject failedResponseMessage(String resultString, String status) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("error", UUID.randomUUID().toString());
        jsonObject.put("resString", resultString);
        jsonObject.put("data", "[]");
        jsonObject.put("message", MessageConstants.FAILED);
        jsonObject.put("status", status);
        return jsonObject;
    }

    /**
     * Method to populate Error Response Message
     * 
     * @param errorMsg
     * @param methodName
     */
    public static JSONObject ErrorResponseMessage(String errorMsg, String methodName) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("error", UUID.randomUUID().toString());
        jsonObject.put("resString", "Error in " + methodName + " : " + errorMsg);
        jsonObject.put("data", "[]");
        jsonObject.put("message", MessageConstants.FAILED);
        jsonObject.put("status", MessageConstants.ERROR_CODE);
        System.out.println("Error in " + methodName + " : " + errorMsg);
        return jsonObject;
    }

}