package se.kth.ii1302.healthwatcher.model;

import se.kth.ii1302.healthwatcher.dto.MeasurementsDTO;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Handle the encryption and encryption of measurements for security of sending information to the server.
 */
public interface EncryptionProvider {
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
    String encryptMeasurement(MeasurementsDTO measurement) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException,
            UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException;

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
    String decryptMeasurement(String encryptedMeasurement) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException;
}
