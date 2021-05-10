package se.kth.ii1302.healthwatcher.view;

import se.kth.ii1302.healthwatcher.controller.Controller;
import se.kth.ii1302.healthwatcher.dto.MeasurementsDTO;

import java.util.Date;
import java.util.Scanner;

/**
 * The main interface of the application that will provide easy access to the application functionalities.
 */
public class MainView {
    private Controller controller;
    
    /**
     * Constructs a MainView and sets the controller field.
     * @param controller 
     */
    public MainView(Controller controller) {
        this.controller = controller;
    }
    
    /**
     * Runs a rudimentary interface in the console where the user can choose
     * different operations from a menu.
     */
    public void runInterface() {
        try {
            // Switch cases to make the app interface.
            // URL that helps with sending data tests "https://httpbin.org/anything"
            // URL for the server management server "http://5.150.209.79:1234/"
            Scanner scanner = new Scanner(System.in);
            MeasurementsDTO[] measurements = null;
            while(true) {
                interfaceMenu();
                System.out.println("Enter you choice: ");
                int choice = scanner.nextInt();
                System.out.println("");
                Date testDate = new Date(2021, 5, 1, 7, 30);
                switch (choice) {
                    case 1:
                        System.out.println("The present device id: " + this.controller.getDeviceID());
                        break;
                    case 2:
                        System.out.println("Write your desired device id (HW-XXXXX):");
                        String newDeviceId = scanner.next();
                        this.controller.setCertainDeviceId(newDeviceId);
                        break;
                    case 3:
                        this.controller.generateRandomDeviceId();
                        String randomDeviceId = this.controller.getDeviceID();
                        System.out.println("The generated device ID is: " + randomDeviceId);
                        break;
                    case 4:
                        this.controller.generateMeasurementForDevice(testDate);
                        measurements = this.controller.getGeneratedMeasurements();
                        System.out.println("Measurement has been generated.");
                        break;
                    case 5:
                        this.controller.generateMeasurementsForDevice(testDate, 10);
                        measurements = this.controller.getGeneratedMeasurements();
                        System.out.println("Multiple measurements has been generated.");
                        break;
                    case 6:
                         this.controller.generateCriticalMeasurementForDevice(testDate);
                         measurements = this.controller.getGeneratedMeasurements();
                         System.out.println("Critical measurement has been generated.");
                        break;
                    case 7:
                        System.out.println("These are your current measurements:");
                        showMeasurements(measurements);
                        break;
                    case 8:
                        this.controller.sendMeasurements(10);
                        System.out.println("Measurements uploaded to server.");
                        break;
                }
                System.out.println();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void interfaceMenu() {
       System.out.println("*********************************************************");
       System.out.println("**               Device Simulation App                 **");
       System.out.println("*********************************************************");
       System.out.println("1. Get device id.");
       System.out.println("2. Set certain device id.");
       System.out.println("3. Generate a random device id.");
       System.out.println("4. Generate measurement for the current device.");
       System.out.println("5. Generate multiple measurements for the current device.");
       System.out.println("6. Generate critical measurement for the current device.");      
       System.out.println("7. Get the current generated measurements.");
       System.out.println("8. Send the current measurements to the server.");
       System.out.println("*********************************************************");
       System.out.println("*********************************************************");

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
