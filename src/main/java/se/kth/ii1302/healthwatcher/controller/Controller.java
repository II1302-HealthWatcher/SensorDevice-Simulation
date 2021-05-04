package se.kth.ii1302.healthwatcher.controller;

import se.kth.ii1302.healthwatcher.dto.MeasurementsDTO;
import se.kth.ii1302.healthwatcher.integration.MeasurementsTransferServiceProvider;
import se.kth.ii1302.healthwatcher.model.DeviceIDManager;
import se.kth.ii1302.healthwatcher.model.EncryptionServiceProvider;
import se.kth.ii1302.healthwatcher.model.MeasurementsProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * Control over the application.
 */
public class Controller {
    private DeviceIDManager deviceIDManager;
    private MeasurementsProvider measurementsProvider;
    private MeasurementsTransferServiceProvider measurementsTransferServiceProvider;
    private EncryptionServiceProvider encryptionServiceProvider;

    /**
     * Constructor
     * @param deviceIDManager The instance to manage the devices ids.
     * @param measurementsTransferServiceProvider the instance to send the generated measurements.
     * @param encryptionServiceProvider the instance to encrypt the measurement before sending them.
     */
    public Controller(DeviceIDManager deviceIDManager,
                      MeasurementsTransferServiceProvider measurementsTransferServiceProvider,
                      EncryptionServiceProvider encryptionServiceProvider) {
        this.deviceIDManager = deviceIDManager;
        this.measurementsProvider = new MeasurementsProvider(this.deviceIDManager.getGeneratedDeviceId());
        this.measurementsTransferServiceProvider = measurementsTransferServiceProvider;
        this.encryptionServiceProvider = encryptionServiceProvider;
    }

    /**
     * Generate a random device id.
     * @return a string with the generated device id.
     * @throws IOException in case something went wrong with the process.
     */
    public String generateRandomDeviceId() throws IOException {
        try {
            this.deviceIDManager.generateDeviceId();
            this.measurementsProvider = new MeasurementsProvider(this.deviceIDManager.getGeneratedDeviceId());
            return this.deviceIDManager.getGeneratedDeviceId();
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * Set a certain device id.
     * @param deviceId the desired device id.
     * @throws IOException in case something went wrong with the process.
     */
    public void setCertainDeviceId(String deviceId) throws IOException {
        try {
            this.deviceIDManager.setGeneratedDeviceId(deviceId);
            this.measurementsProvider = new MeasurementsProvider(deviceId);
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * Get the preset device id.
     * @return a string with the device id.
     */
    public String getDeviceID() {
        return this.deviceIDManager.getGeneratedDeviceId();
    }

    /**
     * Generate a random measurement for the present device.
     * @param date the desired date for the generated measurement.
     * @return a dto with the measurement information.
     */
    public MeasurementsDTO generateMeasurementForDevice(Date date) {
        this.measurementsProvider.resetMeasurements();
        this.measurementsProvider.generateMeasurement(date);
        return getGeneratedMeasurements()[0];
    }

    /**
     * Generate a random critical measurement for the present device.
     * @param date the desired date for the generated measurement.
     * @return a dto with the measurement information.
     */
    public MeasurementsDTO generateCriticalMeasurementForDevice(Date date) {
        this.measurementsProvider.resetMeasurements();
        this.measurementsProvider.generateCriticalMeasurement(date);
        return getGeneratedMeasurements()[0];
    }

    /**
     * Generate several measurements for the present device.
     * @param date the desired start date of the measurements.
     * @param count the number of the desired measurements count.
     * @return an array containing the measurements for the present device.
     */
    public MeasurementsDTO[] generateMeasurementsForDevice(Date date,int count) {
        this.measurementsProvider.resetMeasurements();
        this.measurementsProvider.generateSeveralMeasurements(count, date);
        return getGeneratedMeasurements();
    }

    /**
     * Get the generated measurement(s) for the present device.
     * @return an array containing the measurements for the present device.
     */
    public MeasurementsDTO[] getGeneratedMeasurements() {
        return this.measurementsProvider.getMeasurements().toArray(new MeasurementsDTO[0]);
    }

    /**
     * Encrypt and send the measurements to the server.
     * @param transferRate
     * @return an array with
     * @throws IOException in case something went wrong with the process.
     * @throws InterruptedException in case something went wrong with the waiting process.
     * @throws InvalidAlgorithmParameterException in case something went wrong with the parameters passed to the algorithm.
     * @throws NoSuchPaddingException in case something went wrong with the padding.
     * @throws IllegalBlockSizeException in case something went wrong with the block size.
     * @throws NoSuchAlgorithmException in case something went wrong with the algorithm.
     * @throws BadPaddingException in case something went wrong with the padding.
     * @throws InvalidKeyException in case something went wrong with the key.
     */
    public String[] sendMeasurements(int transferRate) throws IOException, InterruptedException,
            InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        try {
            String[] encryptedMeasurements = encryptMeasurements(getGeneratedMeasurements());
            return this.measurementsTransferServiceProvider.sendSeveralMeasurements(encryptedMeasurements, transferRate);
        } catch (Exception ex) {
            throw ex;
        }
    }


    private String[] encryptMeasurements(MeasurementsDTO[] measurements) throws InvalidAlgorithmParameterException,
            UnsupportedEncodingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException,
            BadPaddingException, InvalidKeyException {
        String[] encryptMeasurements = new String[measurements.length];
        for(int i = 0; i < encryptMeasurements.length; i++) {
            encryptMeasurements[i] = this.encryptionServiceProvider.encryptMeasurement(measurements[i]);
        }
        return encryptMeasurements;
    }
}
