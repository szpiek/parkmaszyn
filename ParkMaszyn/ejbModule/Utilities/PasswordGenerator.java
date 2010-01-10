package Utilities;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordGenerator {
	public static String generatePassword(String pass) throws NoSuchAlgorithmException
	{
        MessageDigest m = null;
		m = MessageDigest.getInstance("MD5");
        m.update(pass.getBytes(),0,pass.length());
        return new BigInteger(1,m.digest()).toString(16);
	}
}
