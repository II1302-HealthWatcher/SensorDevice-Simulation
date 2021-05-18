package se.kth.ii1302.healthwatcher.dto;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class MeasurementsDTOTest {
    MeasurementsDTO measurement;

    @BeforeEach
    void setUp() {
        measurement = new MeasurementsDTO("HW-12345", new Date(121,4,17,12,0)
        ,37.31f,96,80);
    }

    @AfterEach
    void tearDown() {
        measurement = null;
    }

    @Test
    void testToString() {
        String expected = "Device ID: HW-12345" +
                "\r\nMeasurement Date: 2021-05-17 12:00" +
                "\r\nBody temperature: 37.31" +
                "\r\nBlood Oxygen Level: 96" +
                "\r\nHeart Pulse Rate: 80\r\n";
        String actual = measurement.toString();
        assertEquals(expected, actual,"The measurements is not well formed as the desired pattern!");
    }
}