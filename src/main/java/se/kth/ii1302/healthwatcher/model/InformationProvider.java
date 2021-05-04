package se.kth.ii1302.healthwatcher.model;

import java.util.Date;

public interface InformationProvider {
    void generateMeasurement(Date measurementDate);
    void generateCriticalMeasurement(Date measurementDate);
    void generateSeveralMeasurements(int count, Date startDate);
}
