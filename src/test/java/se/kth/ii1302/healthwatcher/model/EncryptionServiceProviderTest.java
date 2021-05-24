package se.kth.ii1302.healthwatcher.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.ii1302.healthwatcher.dto.MeasurementsDTO;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class EncryptionServiceProviderTest {
    private EncryptionServiceProvider encryptionServiceProvider;

    @BeforeEach
    void setUp() {
        try {
            encryptionServiceProvider = new EncryptionServiceProvider();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
        encryptionServiceProvider = null;
    }

    @Test
    void encryptMeasurement() {
        MeasurementsDTO measurement = new MeasurementsDTO(
                "HW-12345", new Date(121,05,24,12,0),
                37.37f,97, 75
        );
        try {
            String encryptedMeasurement = encryptionServiceProvider.encryptMeasurement(measurement);
            String decryptedMeasurement = encryptionServiceProvider.decryptMeasurement(encryptedMeasurement);
            assertEquals(measurement.toString(), decryptedMeasurement, "The encryption did not work as intended");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}