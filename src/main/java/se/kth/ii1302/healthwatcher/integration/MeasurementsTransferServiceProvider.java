package se.kth.ii1302.healthwatcher.integration;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Handle the communication to the server for sending measurements.
 */
public class MeasurementsTransferServiceProvider implements InformationTransferProvider{
    private String managementServerURL;

    /**
     * Constructor
     * @param url the server url.
     */
    public MeasurementsTransferServiceProvider(String url) {
        this.managementServerURL = url;
    }

    /**
     * Send the information of the measurement to the server.
     * @param measurement the desired measurement to send.
     * @return a string with the response.
     * @throws IOException is case something went wrong with the communication of the sending process.
     */
    public String sendMeasurement(String measurement) throws IOException {
        HttpURLConnection serverConnection = getConnection();
        serverConnection.setRequestMethod("POST");
        serverConnection.setDoOutput(true);
        serverConnection.setUseCaches(false);
        serverConnection.setRequestProperty("Content-Length", Integer.toString(measurement.length()));
        DataOutputStream sendRequestToServer = new DataOutputStream(serverConnection.getOutputStream());
        sendRequestToServer.writeBytes(measurement);
        BufferedReader getServerResponse = new BufferedReader(new InputStreamReader(serverConnection.getInputStream()));
        StringBuilder responseContent = new StringBuilder();
        String line = getServerResponse.readLine();
        while (line != null) {
            responseContent.append(line + "\n");
            line = getServerResponse.readLine();
        }
        closeConnection(serverConnection);
        return responseContent.toString();
    }

    /**
     * Send several measurements at a certain pace.
     * @param measurements the desired measurements that wanted to be send.
     * @param transferRate the rate of the pause gap between the sending process.
     * @return an array with all the response messages.
     * @throws InterruptedException in case something went wrong with the waiting process.
     * @throws IOException is case something went wrong with the communication of the sending process.
     */
    public String[] sendSeveralMeasurements(String[] measurements, int transferRate) throws InterruptedException, IOException {
        String[] results = new String[measurements.length];
        for (int i = 0; i < measurements.length; i++) {
            results[i] = sendMeasurement(measurements[i]);
            Thread.sleep(transferRate * 1000);
        }
        return results;
    }

    private HttpURLConnection getConnection() throws IOException {
        URL managementServer= new URL(this.managementServerURL);
        return (HttpURLConnection) managementServer.openConnection();
    }

    private void closeConnection(HttpURLConnection connection) {
        connection.disconnect();
    }
}
