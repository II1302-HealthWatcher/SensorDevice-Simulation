package se.kth.ii1302.healthwatcher.model;

import se.kth.ii1302.healthwatcher.dto.MeasurementsDTO;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Provide the measurements for a device.
 */
public class MeasurementsProvider implements InformationProvider{
    private String deviceId;
    private ArrayList<MeasurementsDTO> measurements;

    /**
     * Constructor
     * @param deviceId the device id that will be provided with the measurements.
     */
    public MeasurementsProvider(String deviceId) {
        this.deviceId = deviceId;
        this.measurements = new ArrayList<>();
    }

    /**
     * Generate a random measurements for the device.
     * @param measurementDate the date decided for the measurement.
     */
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

    /**
     * Generate a random critical measurements for the device.
     * @param measurementDate the date decided for the measurement.
     */
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

    /**
     * Generate several measurements with 1 hour gap between them, a random critical measurements is decided
     * for certain count of normal generated measurements.
     * @param count how many measurements will be generated.
     * @param startDate
     */
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

    /**
     * Get the array holding the generated measurements.
     * @return an arraylist containing the measurements.
     */
    public ArrayList<MeasurementsDTO> getMeasurements() {
        return this.measurements;
    }

    /**
     * Get the device id that the measurements will provided for.
     * @return a string with the device id.
     */
    public String getDeviceId() {
        return this.deviceId;
    }

    /**
     * Empty out the generated measurements to start a new ones.
     */
    public void resetMeasurements() {
        this.measurements = new ArrayList<>();
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
}
