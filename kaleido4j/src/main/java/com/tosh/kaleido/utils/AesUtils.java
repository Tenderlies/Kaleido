package com.tosh.kaleido.utils;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.HexUtil;

public class AesUtils {
	private Key key;
	private Cipher cipher;

	byte[] iv = "0103021405060878".getBytes();

	private void init(byte[] keyBytes)
			throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException {
		// keyBytes length must be n*16
		int base = 16;
		if (keyBytes.length % base != 0) {
			int groups = keyBytes.length / base + (keyBytes.length % base != 0 ? 1 : 0);
			byte[] temp = new byte[groups * base];
			Arrays.fill(temp, (byte) 0);
			System.arraycopy(keyBytes, 0, temp, 0, keyBytes.length);
			keyBytes = temp;
		}
		Security.addProvider(new BouncyCastleProvider());
		key = new SecretKeySpec(keyBytes, "AES");
		cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
	}

	private byte[] encrypt(byte[] content, byte[] keyBytes)
			throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException {
		init(keyBytes);
		cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
		return cipher.doFinal(content);
	}

	public String encrypt(String content, String keyBytes) {
		try {
			return Base64.encode(encrypt(content.getBytes(), keyBytes.getBytes()));
		} catch (Exception e) {
			return null;
		}
	}

	public String encryptByHex(String content, String keyBytes) {
		try {
			return HexUtil.encodeHexStr(encrypt(content.getBytes(), keyBytes.getBytes()));
		} catch (Exception e) {
			return null;
		}
	}

	private byte[] decrypt(byte[] encryptedData, byte[] keyBytes)
			throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException {
		init(keyBytes);
		cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
		return cipher.doFinal(encryptedData);
	}

	public String decrypt(String encryptedData, String keyData) {
		try {
			return new String(decrypt(Base64.decode(encryptedData), keyData.getBytes()));
		} catch (Exception e) {
			return null;
		}
	}

	public String decryptByHex(String encryptedData, String keyData) {
		try {
			return new String(decrypt(HexUtil.decodeHex(encryptedData), keyData.getBytes()));
		} catch (Exception e) {
			return null;
		}
	}
}
