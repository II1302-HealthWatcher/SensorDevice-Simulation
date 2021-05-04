package se.kth.ii1302.healthwatcher.model;

import java.io.IOException;

public interface DeviceIDHandler {
    void generateDeviceId() throws IOException;
    void setGeneratedDeviceId(String deviceId) throws IOException;
}
