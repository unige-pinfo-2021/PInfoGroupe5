package api.model;

import java.io.IOException;

import java.util.Base64;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class Encryption{

	private static final String ALGORITHM = "AES";
	private static final String key = "Bg4u5x!F%F*M-KpF" ; //128 bytes -"Bar12345Bar12345"

	/*** DATA for mySQL ***/
	private String hashu = "jgy1NSLxg9zMWmAeMgUPx228Da+fR2XNRAXLkjXMPNX2kP09CXcEPHu9MelR0yCM";
	private String hashg = "Idyy4tnYy0FqPdZ2hU65pw==";
	private String hashp = "Y45EPedZm9/0YFibtxANzA==";
	

	public String getp()  throws Exception{
		return decrypt(hashp);
	}

	public String getu()  throws Exception{
		return decrypt(hashu);
	}

	public String getg()  throws Exception{
		return decrypt(hashg);
	}

	public String encrypt(String secret) throws Exception {
	    Key key = newKey();
	    Cipher c = Cipher.getInstance(ALGORITHM);
	    c.init(Cipher.ENCRYPT_MODE, key);
	    byte[] encValue = c.doFinal(secret.getBytes());

	    return Base64.getEncoder().encodeToString(encValue);
	}

	
	public String decrypt(String str) throws Exception {
	    Key key = newKey();
	    Cipher c = Cipher.getInstance(ALGORITHM);
	    c.init(Cipher.DECRYPT_MODE, key);

	    byte[] secret = Base64.getDecoder().decode(str);

	    String decrypted = new String(c.doFinal(secret));
	    return decrypted;
	}

	private Key newKey(){
		Key key = new SecretKeySpec(this.key.getBytes(), ALGORITHM);
		return key;
	}


}//end class
