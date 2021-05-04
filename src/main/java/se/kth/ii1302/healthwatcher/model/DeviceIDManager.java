package se.kth.ii1302.healthwatcher.model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

/**
 * Provide the device id that can be either decided or random generated.
 */
public class DeviceIDManager implements DeviceIDHandler{
    private String generatedDeviceId;

    /**
     * Constructor, get the last device id generated.
     * @throws IOException in case something went wrong with processing the file.
     */
    public DeviceIDManager() throws IOException {
        String[] history = Files.readString(Paths.get("DeviceIdHistory.txt")).split("\n");
        this.generatedDeviceId = history[history.length - 1];
    }

    /**
     * Generate a device id with a certain pattern (HW-XXXXX) and save it to a file.
     * @throws IOException in case something went wrong with processing the file.
     */
    public void generateDeviceId() throws IOException {
        Random rand = new Random();
        int randomDeviceId = rand.nextInt(100000);
        String deviceId = "" + randomDeviceId;
        int missingZeros = 5 - deviceId.length();
        for(int i = 0; i < missingZeros; i++) {
            deviceId = "0" + deviceId;
        }
        this.generatedDeviceId = "HW-" + deviceId;
        saveGeneratedDeviceId();
    }

    /**
     * Get the device id present or generated last.
     * @return a string with the device id.
     */
    public String getGeneratedDeviceId() {
        return this.generatedDeviceId;
    }

    /**
     * Set the device id to a particular device id.
     * @param deviceId the desired device id.
     * @throws IOException in case something went wrong with processing the file.
     */
    public void setGeneratedDeviceId(String deviceId) throws IOException {
        this.generatedDeviceId = deviceId;
        saveGeneratedDeviceId();
    }

    private void saveGeneratedDeviceId() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("DeviceIdHistory.txt", true));
        writer.append(this.generatedDeviceId + "\n");
        writer.close();
    }
}
