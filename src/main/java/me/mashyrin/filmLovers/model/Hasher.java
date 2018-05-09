package me.mashyrin.filmLovers.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {
    
    private static byte[] salt = "thats_salt".getBytes();
    
    public static String getSecurePassword( String password ) throws NoSuchAlgorithmException {
        String generatedPassword;
        MessageDigest messageDigest = MessageDigest.getInstance( "MD5" );
        messageDigest.update( salt );
        byte[] bytes = messageDigest.digest( password.getBytes() );
        StringBuilder stringBuilder = new StringBuilder();
        for( int i = 0; i < bytes.length; i++ ) {
            stringBuilder.append( Integer.toString( ( bytes[i] & 0xff ) + 0x100, 16 ).substring( 1 ) );
        }
        generatedPassword = stringBuilder.toString();
        return generatedPassword;
    }
}
