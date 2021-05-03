package se.kth.ii1302.healthwatcher.integration;

import se.kth.ii1302.healthwatcher.dto.MeasurementsDTO;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MeasurementsTransferServiceProvider {
    String managementServerURL = "https://httpbin.org/anything";

    public MeasurementsTransferServiceProvider() {}

    private HttpURLConnection getConnection() throws IOException {
        URL managementServer= new URL(this.managementServerURL);
        return (HttpURLConnection) managementServer.openConnection();
    }

    private void closeConnection(HttpURLConnection connection) {
        connection.disconnect();
    }

    public String sendMeasurement(MeasurementsDTO measurement) throws IOException {
        HttpURLConnection serverConnection = getConnection();
        serverConnection.setRequestMethod("POST");
        serverConnection.setDoOutput(true);
        serverConnection.setUseCaches(false);
        serverConnection.setRequestProperty("Content-Length", Integer.toString(measurement.toString().length()));
        DataOutputStream sendRequestToServer = new DataOutputStream(serverConnection.getOutputStream());
        sendRequestToServer.writeBytes(measurement.toString());
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

    public String sendSeveralMeasurements(MeasurementsDTO[] measurements, int transferRate) throws InterruptedException, IOException {
        StringBuilder results = new StringBuilder();
        for (int i = 0; i < measurements.length; i++) {
            results.append(sendMeasurement(measurements[i]));
            Thread.sleep(transferRate * 1000);
        }
        return results.toString();
    }
}
