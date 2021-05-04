package se.kth.ii1302.healthwatcher.model;

import se.kth.ii1302.healthwatcher.dto.MeasurementsDTO;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

/**
 * Handle the encryption and encryption of measurements for security of sending information to the server.
 */
public class EncryptionServiceProvider implements EncryptionProvider{
    private final String encryptionKey = "HealthWatcherKey";
    private byte[] key;
    private SecretKeySpec secretKey;

    /**
     * Constructor, encryption using AES.
     * @throws UnsupportedEncodingException in case something went wrong with the encoding.
     * @throws NoSuchAlgorithmException in case something went wrong with the algorithm.
     */
    public EncryptionServiceProvider() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        this.key = this.encryptionKey.getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        this.key = sha.digest(this.key);
        this.key = Arrays.copyOf(this.key, 16);
        this.secretKey = new SecretKeySpec(this.key, "AES");
    }

    /**
     * Encrypt the measurement with AES algorithm.
     * @param measurement the desired measurement to be encrypted.
     * @return a string with the encrypted measurement.
     * @throws UnsupportedEncodingException in case something went wrong with the encoding.
     * @throws NoSuchAlgorithmException in case something went wrong with the algorithm.
     * @throws NoSuchPaddingException in case something went wrong with the padding.
     * @throws InvalidAlgorithmParameterException in case something went wrong with the parameters passed to the algorithm.
     * @throws InvalidKeyException in case something went wrong with the key.
     * @throws IllegalBlockSizeException in case something went wrong with the block size.
     * @throws BadPaddingException in case something went wrong with the padding.
     */
    public String encryptMeasurement(MeasurementsDTO measurement) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException,
            UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, this.secretKey, new IvParameterSpec(this.key));
        byte[] encryptedData = cipher.doFinal(measurement.toString().getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    /**
     * Decrypt the measurement that was encrypted with AES algorithm.
     * @param encryptedMeasurement the desired measurement to decrypt.
     * @return a string with the decrypted measurement.
     * @throws NoSuchPaddingException in case something went wrong with the padding.
     * @throws NoSuchAlgorithmException in case something went wrong with the algorithm.
     * @throws InvalidAlgorithmParameterException in case something went wrong with the parameters passed to the algorithm.
     * @throws InvalidKeyException  in case something went wrong with the key.
     * @throws IllegalBlockSizeException in case something went wrong with the block size.
     * @throws BadPaddingException in case something went wrong with the padding.
     */
    public String decryptMeasurement(String encryptedMeasurement) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, this.secretKey, new IvParameterSpec(this.key));
        byte[] encryptedData = Base64.getDecoder().decode(encryptedMeasurement);
        byte[] res = cipher.doFinal(encryptedData);
        return new String(res, StandardCharsets.UTF_8);
    }
}
