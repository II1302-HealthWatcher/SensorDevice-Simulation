package se.kth.ii1302.healthwatcher.integration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import se.kth.ii1302.healthwatcher.dto.MeasurementsDTO;

import se.kth.ii1302.healthwatcher.integration.MeasurementsTransferServiceProvider;

// URL that helps with sending data tests "https://httpbin.org/anything"
// URL for the server management server "http://5.150.209.79:1234/"

class MeasurementsTransferServiceProviderTest {
    MeasurementsTransferServiceProvider measurementsTransferServiceProvider;
    MeasurementsDTO measurement;

    @BeforeEach
    void setUp() {
        measurementsTransferServiceProvider = new MeasurementsTransferServiceProvider("https://httpbin.org/anything");
    }

    @AfterEach
    void tearDown() {
        measurementsTransferServiceProvider = null;
    }
    
    @Test
    void sendMeasurement() throws IOException {
        String measurement = "Test";
        String expected = "{  \"args\": {},   \"data\": \"\",   \"files\": {},   \"form\": {    \"Test\": \"\"  },   \"";
        String[] actual = measurementsTransferServiceProvider.sendMeasurement(measurement).split("headers");
        assertEquals(expected, actual[0].replaceAll("\n", ""), "The response data does not follow the desired pattern!");
    }

    @Test
    void sendSeveralMeasurements() throws InterruptedException, IOException{
        String[] measurements = {"Test", "Test", "Test"};
        String response = "{  \"args\": {},   \"data\": \"\",   \"files\": {},   \"form\": {    \"Test\": \"\"  },   \"";
        String[] expected = {response, response, response};
        String[] prepare = measurementsTransferServiceProvider.sendSeveralMeasurements(measurements, 1);
        
        List<String> arrlist = new ArrayList<String>();
        
        for(int i = 0; i < prepare.length; i++){
            String[] temp = prepare[i].split("headers");
            String result = temp[0].replaceAll("\n", "");
            arrlist.add(result);
        }
        String[] actual = arrlist.toArray(expected);
        assertEquals(expected, actual, "The response data does not follow the desired pattern!");
    }

}
