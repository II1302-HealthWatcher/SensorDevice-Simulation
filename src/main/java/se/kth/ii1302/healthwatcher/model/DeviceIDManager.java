package se.kth.ii1302.healthwatcher.model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class DeviceIDManager {
    private String generatedDeviceId;

    public DeviceIDManager() throws IOException {
        String[] history = Files.readString(Paths.get("DeviceIdHistory.txt")).split("\n");
        this.generatedDeviceId = history[history.length - 1];
    }

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

    private void saveGeneratedDeviceId() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("DeviceIdHistory.txt", true));
        writer.append(this.generatedDeviceId + "\n");
        writer.close();
    }

    public String getGeneratedDeviceId() {
        return this.generatedDeviceId;
    }
}
