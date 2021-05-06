package se.kth.ii1302.healthwatcher.model;

import java.io.IOException;

/**
 * Provide the device id that can be either decided or random generated.
 */
public interface DeviceIDHandler {
    /**
     * Generate a device id with a certain pattern (HW-XXXXX) and save it to a file.
     * @throws IOException in case something went wrong with processing the file.
     */
    void generateDeviceId() throws IOException;

    /**
     * Set the device id to a particular device id.
     * @param deviceId the desired device id.
     * @throws IOException in case something went wrong with processing the file.
     */
    void setGeneratedDeviceId(String deviceId) throws IOException;

    /**
     * Get the device id current or generated last.
     * @return a string with the device id.
     */
    String getGeneratedDeviceId();
}
