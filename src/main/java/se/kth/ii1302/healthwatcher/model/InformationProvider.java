package se.kth.ii1302.healthwatcher.model;

import se.kth.ii1302.healthwatcher.dto.MeasurementsDTO;

import java.util.ArrayList;
import java.util.Date;

/**
 * Provide the measurements for a device.
 */
public interface InformationProvider {
    /**
     * Generate a random measurements for the device.
     * @param measurementDate the date decided for the measurement.
     */
    void generateMeasurement(Date measurementDate);

    /**
     * Generate a random critical measurements for the device.
     * @param measurementDate the date decided for the measurement.
     */
    void generateCriticalMeasurement(Date measurementDate);

    /**
     * Generate several measurements with 1 hour gap between them, a random critical measurements is decided
     * for certain count of normal generated measurements.
     * @param count how many measurements will be generated.
     * @param startDate
     */
    void generateSeveralMeasurements(int count, Date startDate);

    /**
     * Get the array holding the generated measurements.
     * @return an arraylist containing the measurements.
     */
    ArrayList<MeasurementsDTO> getMeasurements();

    /**
     * Empty out the generated measurements to start a new ones.
     */
    void resetMeasurements();
}
