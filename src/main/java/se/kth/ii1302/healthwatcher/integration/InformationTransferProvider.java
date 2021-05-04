package se.kth.ii1302.healthwatcher.integration;

import java.io.IOException;

public interface InformationTransferProvider {
    String sendMeasurement(String measurement) throws IOException;
    String[] sendSeveralMeasurements(String[] measurements, int transferRate) throws InterruptedException, IOException;
}
