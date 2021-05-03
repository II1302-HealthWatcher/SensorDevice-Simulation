package se.kth.ii1302.healthwatcher.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class MeasurementsDTO {
    private String deviceId;
    private Date measurementDate;
    private float bodyTemperature;
    private int bloodOxygenLevel;
    private int heartPulseRate;

    public MeasurementsDTO(String deviceId, Date measurementDate, float bodyTemperature, int bloodOxygenLevel, int heartPulseRate) {
        this.deviceId = deviceId;
        this.bodyTemperature = bodyTemperature;
        this.bloodOxygenLevel = bloodOxygenLevel;
        this.heartPulseRate = heartPulseRate;
        this.measurementDate = measurementDate;
    }

    public String toString() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        return  "Device ID: " + this.deviceId +
                "\nMeasurement Date: " + dateFormatter.format(this.measurementDate).toString() +
                "\nBody temperature: " + this.bodyTemperature +
                "\nBlood Oxygen Level: " + this.bloodOxygenLevel +
                "\nHeart Pulse Rate: " + this.heartPulseRate;
    }
}
