package se.kth.ii1302.healthwatcher.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Handle the storage of measurements of the devices inside the application.
 */
public final class MeasurementsDTO {
    private String deviceId;
    private Date measurementDate;
    private float bodyTemperature;
    private int bloodOxygenLevel;
    private int heartPulseRate;

    /**
     * Constructor
     * @param deviceId the device id with specific pattern (HW-XXXXX)
     * @param measurementDate the date of the taken measurement.
     * @param bodyTemperature the temperature value of the body at the measurement time.
     * @param bloodOxygenLevel the oxygen level in the blood at the measurement time.
     * @param heartPulseRate the pulse rate of the heart at the measurement time.
     */
    public MeasurementsDTO(String deviceId, Date measurementDate, float bodyTemperature, int bloodOxygenLevel, int heartPulseRate) {
        this.deviceId = deviceId;
        this.bodyTemperature = bodyTemperature;
        this.bloodOxygenLevel = bloodOxygenLevel;
        this.heartPulseRate = heartPulseRate;
        this.measurementDate = measurementDate;
    }

    /**
     * Represent the measurement as string.
     * @return the string representation of the measurement.
     */
    public String toString() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        return  "Device ID: " + this.deviceId +
                "\r\nMeasurement Date: " + dateFormatter.format(this.measurementDate).toString() +
                "\r\nBody temperature: " + this.bodyTemperature +
                "\r\nBlood Oxygen Level: " + this.bloodOxygenLevel +
                "\r\nHeart Pulse Rate: " + this.heartPulseRate + "\r\n";
    }
}
