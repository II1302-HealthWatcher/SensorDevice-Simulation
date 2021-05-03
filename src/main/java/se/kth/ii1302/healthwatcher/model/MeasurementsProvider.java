package se.kth.ii1302.healthwatcher.model;

import se.kth.ii1302.healthwatcher.dto.MeasurementsDTO;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class MeasurementsProvider {
    private String deviceId; // Device ID template is HW-XXXXX
    private ArrayList<MeasurementsDTO> measurements;

    public MeasurementsProvider(String deviceId) {
        this.deviceId = deviceId;
        this.measurements = new ArrayList<>();
    }

    public void generateMeasurement(Date measurementDate) {
        Random rand = new Random();
        float bodyTemperature = rand.nextFloat() + rand.nextInt(2) + 36;
        int bloodOxygenLevel = rand.nextInt(5) + 95;
        int heartPulseRate = rand.nextInt(11) + 70;
        MeasurementsDTO newMeasurement = new MeasurementsDTO(
                this.deviceId, measurementDate, roundToNDecimalPlaces(bodyTemperature, 2), bloodOxygenLevel, heartPulseRate
        );
        measurements.add(newMeasurement);
    }

    public void generateCriticalMeasurement(Date measurementDate) {
        Random rand = new Random();
        float bodyTemperature = rand.nextFloat() + rand.nextInt(1) + 38;
        int bloodOxygenLevel = rand.nextInt(5) + 90;
        int heartPulseRate = rand.nextInt(15) + 85;
        MeasurementsDTO newMeasurement = new MeasurementsDTO(
                this.deviceId, measurementDate, roundToNDecimalPlaces(bodyTemperature, 2), bloodOxygenLevel, heartPulseRate
        );
        measurements.add(newMeasurement);
    }

    public void generateSeveralMeasurements(int count, Date startDate) {
        Date measurementDate = startDate;

        for(int i = 0; i < count; i++) {
            if(i % 9 == 0 && i != 0) {
                generateCriticalMeasurement(measurementDate);
            } else {
                generateMeasurement(measurementDate);
            }

            measurementDate = Date.from(measurementDate.toInstant().plusSeconds(3600));
        }
    }

    private float roundToNDecimalPlaces(float number, int decimalPlaces) {
        StringBuilder pattern = new StringBuilder();
        pattern.append("#.");
        for(int i = 0; i < decimalPlaces; i++) {
            pattern.append("#");
        }
        DecimalFormat decimalFormatter = new DecimalFormat(pattern.toString());
        float roundedNumber = Float.parseFloat(decimalFormatter.format(number).replace("," , "."));
        return roundedNumber;
    }

    public ArrayList<MeasurementsDTO> getMeasurements() {
        return this.measurements;
    }
}
