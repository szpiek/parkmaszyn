package Utilities;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class PasswordGenerator {
	public static String generatePassword(String pass) throws NoSuchAlgorithmException
	{
        MessageDigest m = null;
		m = MessageDigest.getInstance("MD5");
        m.update(pass.getBytes(),0,pass.length());
        return new BigInteger(1,m.digest()).toString(16);
	}
	
	public static String genPass()
	{
		Random r = new Random();
		Integer len = 0;
		do
		{
			len = r.nextInt(14);
		}
		while(len<6);
		char[] pass = new char[len];
		for(int i=0;i<len;i++)
		{
			pass[i] = (char)(r.nextInt(58)+64);
		}
		return new String(pass);
	}
}
