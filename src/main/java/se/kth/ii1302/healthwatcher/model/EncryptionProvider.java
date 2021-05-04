package se.kth.ii1302.healthwatcher.model;

import se.kth.ii1302.healthwatcher.dto.MeasurementsDTO;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface EncryptionProvider {
    String encryptMeasurement(MeasurementsDTO measurement) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException,
            UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException;

    String decryptMeasurement(String encryptedMeasurement) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException;
}
