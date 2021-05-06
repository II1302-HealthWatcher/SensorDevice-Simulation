package se.kth.ii1302.healthwatcher.integration;

import java.io.IOException;

/**
 * Handle the communication to the server for sending measurements.
 */
public interface InformationTransferProvider {
    /**
     * Send the information of the measurement to the server.
     * @param measurement the desired measurement to send.
     * @return a string with the response.
     * @throws IOException is case something went wrong with the communication of the sending process.
     */
    String sendMeasurement(String measurement) throws IOException;

    /**
     * Send several measurements at a certain pace.
     * @param measurements the desired measurements that wanted to be send.
     * @param transferRate the rate of the pause gap between the sending process.
     * @return an array with all the response messages.
     * @throws InterruptedException in case something went wrong with the waiting process.
     * @throws IOException is case something went wrong with the communication of the sending process.
     */
    String[] sendSeveralMeasurements(String[] measurements, int transferRate) throws InterruptedException, IOException;
}
