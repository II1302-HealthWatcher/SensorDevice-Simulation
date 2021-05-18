package se.kth.ii1302.healthwatcher.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.ii1302.healthwatcher.dto.MeasurementsDTO;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class MeasurementsProviderTest {
    MeasurementsProvider measurementsProvider;

    @BeforeEach
    void setUp() {
        measurementsProvider = new MeasurementsProvider("HW-12345");
    }

    @AfterEach
    void tearDown() {
        measurementsProvider = null;
    }

    @Test
    void generateMeasurement() {
        String expected = "Device ID: HW-12345" +
                "\r\nMeasurement Date: 2021-05-17 12:00" +
                "\r\nBody temperature: " +
                "\r\nBlood Oxygen Level: " +
                "\r\nHeart Pulse Rate: \r\n";
        measurementsProvider.generateMeasurement(new Date(121,4,17,12,0));
        String[] preparation = measurementsProvider.getMeasurements().get(0).toString().split("Body");
        String actual = preparation[0] + "Body" + preparation[1].replaceAll("[\\d.]", "");
        assertEquals(expected, actual, "The data generate does not follow the desired pattern!");
    }

    @Test
    void generateCriticalMeasurement() {
        String expected = "Device ID: HW-12345" +
                "\r\nMeasurement Date: 2021-05-17 12:00" +
                "\r\nBody temperature: " +
                "\r\nBlood Oxygen Level: " +
                "\r\nHeart Pulse Rate: \r\n";
        measurementsProvider.generateCriticalMeasurement(new Date(121,4,17,12,0));
        String[] preparation = measurementsProvider.getMeasurements().get(0).toString().split("Body");
        String actual = preparation[0] + "Body" + preparation[1].replaceAll("[\\d.]", "");
        assertEquals(expected, actual, "The data generate does not follow the desired pattern!");
    }

    @Test
    void generateSeveralMeasurements() {
        measurementsProvider.generateSeveralMeasurements(15, new Date(121,4,17,12,0));
        MeasurementsDTO[] measurements = measurementsProvider.getMeasurements().toArray(new MeasurementsDTO[0]);
        assertEquals(15, measurements.length, "The count of generated data is does not match!");
        for(int i = 0; i < measurements.length; i++) {
            assertNotNull(measurements[i], "One or more of the generated data is null!");
        }
    }
}