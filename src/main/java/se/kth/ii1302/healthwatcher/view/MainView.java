package se.kth.ii1302.healthwatcher.view;

import se.kth.ii1302.healthwatcher.controller.Controller;
import se.kth.ii1302.healthwatcher.dto.MeasurementsDTO;

import java.util.Date;

/**
 * The main interface of the application that will provide easy access to the application functionalities.
 */
public class MainView {
    private Controller controller;

    public MainView(Controller controller) {
        this.controller = controller;
    }

    public void runInterface() {
        try {
            // Switch cases to make the app interface.
            // URL that helps with sending data tests "https://httpbin.org/anything"
            // URL for the server management server "http://5.150.209.79:1234/"
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void showMeasurements(MeasurementsDTO measurement) {
        System.out.println(measurement.toString());
        System.out.println("");
    }

    private void showMeasurements(MeasurementsDTO[] measurements) {
        for (int i = 0; i < measurements.length; i++) {
            System.out.println("Measurement nr: " + (i + 1));
            System.out.println(measurements[i].toString());
        }
    }

    private void showResponses(String[] responses) {
        for (int i = 0; i < responses.length; i++) {
            System.out.println("Response nr: " + (i +1));
            System.out.println(responses[i].replaceAll("\n", ""));
            System.out.println("");
        }
    }
}
