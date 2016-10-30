import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSA {


	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException
	{
		RSA rsaObject = new RSA();

		// Should be called when the user selects to generate new ones
		rsaObject.generateNewKeys();

		String text = "Hello World m8";

		byte[] dataToEnc = text.getBytes();

		byte[] encyptedData = rsaObject.encrypt(dataToEnc);

		rsaObject.decrypt(encyptedData);

	}

	public void saveToFile(String fileName, BigInteger m, BigInteger ex) throws IOException
	{
		ObjectOutputStream output = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
		try
		{
			output.writeObject(m);
			output.writeObject(ex);	
		}
		catch(Exception e)
		{
			throw new IOException("error", e);
		}
		finally
		{
			output.close();
		}

	}

	PublicKey pubfromFile(String fileName) throws IOException
	{
		File file = new File(fileName);
		InputStream in = new FileInputStream(file);
		ObjectInputStream oin = new ObjectInputStream(new BufferedInputStream(in));
		try 
		{
			BigInteger m = (BigInteger) oin.readObject();
			BigInteger e = (BigInteger) oin.readObject();
			RSAPublicKeySpec keySpec = new RSAPublicKeySpec(m, e);
			KeyFactory fact = KeyFactory.getInstance("RSA");
			PublicKey pubKey = fact.generatePublic(keySpec);
			return pubKey;
		}
		catch (Exception e)
		{
			throw new RuntimeException("error", e);
		}
		finally
		{
			oin.close();
		}

	}

	PrivateKey privFromFile(String fileName) throws IOException
	{
		File file = new File(fileName);
		InputStream in = new FileInputStream(file);
		ObjectInputStream oin = new ObjectInputStream(new BufferedInputStream(in));
		try 
		{
			BigInteger m = (BigInteger) oin.readObject();
			BigInteger e = (BigInteger) oin.readObject();
			RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(m, e);
			KeyFactory fact = KeyFactory.getInstance("RSA");
			PrivateKey privateKey = fact.generatePrivate(keySpec);
			return privateKey;
		}
		catch (Exception e)
		{
			throw new RuntimeException("error", e);
		}
		finally
		{
			oin.close();
		}
	}

	public byte[] encrypt(byte[] data) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
		PublicKey pubKey = pubfromFile("C:\\Users\\Alexander Savill\\workspace\\RSATest\\public.key");
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		byte[] cipherData = cipher.doFinal(data);
		String string = new String(cipherData, Charset.forName("UTF-8"));
		System.out.println(string);
		return cipherData;
	}

	public byte[] decrypt(byte[] data) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
		PrivateKey privKey = privFromFile("C:\\Users\\Alexander Savill\\workspace\\RSATest\\private.key");

		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privKey);
		byte[] cipherData = cipher.doFinal(data);
		String string = new String(cipherData, Charset.forName("UTF-8"));
		System.out.println(string);
		return cipherData;
	}


	public void generateNewKeys() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException
	{
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(512);
		KeyPair kp = kpg.genKeyPair();

		KeyFactory fact = KeyFactory.getInstance("RSA");

		RSAPublicKeySpec pub = fact.getKeySpec(kp.getPublic(),
				RSAPublicKeySpec.class);

		RSAPrivateKeySpec priv = fact.getKeySpec(kp.getPrivate(),
				RSAPrivateKeySpec.class);

		saveToFile("public.key", pub.getModulus(),
				pub.getPublicExponent());

		saveToFile("private.key", priv.getModulus(),
				priv.getPrivateExponent());
	}

}
