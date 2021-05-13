package se.kth.ii1302.healthwatcher.view;

import se.kth.ii1302.healthwatcher.controller.Controller;
import se.kth.ii1302.healthwatcher.dto.MeasurementsDTO;

import java.util.Date;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.*; 
import java.awt.event.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The main interface of the application that will provide easy access to the application functionalities.
 */
public class MainView {
    private Controller controller;
    private JTextArea output;
    private MeasurementsDTO[] measurements;
    private Date testDate;
    private int amount;
    private String[] responses;
    
    /**
     * Constructs a MainView and sets the controller field.
     * @param controller 
     */
    public MainView(Controller controller) {
        this.controller = controller;
        this.output = new JTextArea("");
        this.output.setEnabled(false);
        this.measurements = null;
        this.testDate = new Date(121, 4, 1, 7, 30);
        this.amount = 10;
        this.responses = null;
    }
    
    /**
     * Runs a GUI interface in the console where the user can choose
     * different operations from a menu and results will be printed in the GUI.
     */
    public void runInterface() {

        try {
            // URL that helps with sending data tests "https://httpbin.org/anything"
            // URL for the server management server "http://5.150.209.79:1234/"
            
            // Creating the frame
            JFrame frame = new JFrame("HealthWatcher Sensor Simulation");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 500);

            // Adding filler at the top
            JPanel filler = new JPanel();
            frame.getContentPane().add(BorderLayout.NORTH, filler);

            // Static text area with option menu
            JTextArea textArea = new JTextArea(
                 "Available actions \n\n"+
                 "1. Get device id. \n"+
                 "2. Set certain device id. \n"+
                 "3. Generate a random device id. \n"+
                 "4. Generate measurement for the current device. \n"+
                 "5. Generate multiple measurements for the current device. \n"+
                 "6. Generate critical measurement for the current device. \n"+     
                 "7. Get the current generated measurements. \n"+
                 "8. Send the current measurements to the server. \n"+
                 "9. Print server responses. \n"
             );
            textArea.setEditable(false);
            frame.getContentPane().add(BorderLayout.LINE_START, textArea);

            // Scrollable area for function output  
            JScrollPane outputWindow = new JScrollPane(output);
            outputWindow.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);  
            outputWindow.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
            frame.add(outputWindow, BorderLayout.CENTER);  

            // Button menu at the bottom
            JPanel controlField = new JPanel();
            
            // Get device id
            JButton button1 = new JButton("1");
            button1.addActionListener((ActionEvent e) -> {
                output.append("- The current device ID is: " + this.controller.getDeviceID() + "\n");
            });
            
            // Set device id
            JButton button2 = new JButton("2");
            button2.addActionListener((ActionEvent e) -> {
                String newDeviceId = JOptionPane.showInputDialog(frame,"Please write your desired device ID (HW-XXXXX)");
                try {
                    this.controller.setCertainDeviceId(newDeviceId);
                } catch (IOException ex) {
                    Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
                }
                output.append("- New device ID has been set.\n");
            });

            // Generate random id
            JButton button3 = new JButton("3");
            button3.addActionListener((ActionEvent e) -> {
                try {
                    this.controller.generateRandomDeviceId();
                } catch (IOException ex) {
                    Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
                }
                String randomDeviceId = this.controller.getDeviceID();
                output.append("- The generated device ID is: " + randomDeviceId + "\n");
            });
            
            // Generate measurement
            JButton button4 = new JButton("4");
            button4.addActionListener((ActionEvent e) -> {
                this.controller.generateMeasurementForDevice(testDate);
                measurements = this.controller.getGeneratedMeasurements();
                output.append("- A measurement has been generated." + "\n");
            });
            
            // Generate multiple measurements
            JButton button5 = new JButton("5");
            button5.addActionListener((ActionEvent e) -> {
                this.controller.generateMeasurementsForDevice(testDate, amount);
                measurements = this.controller.getGeneratedMeasurements();
                output.append("- Measurements has been generated." + "\n");
            });
            
            // Generate critical measurement
            JButton button6 = new JButton("6");
            button6.addActionListener((ActionEvent e) -> {
                this.controller.generateCriticalMeasurementForDevice(testDate);
                measurements = this.controller.getGeneratedMeasurements();
                output.append("- Critical measurement has been generated." + "\n");
            });
            
            // Display measurements
            JButton button7 = new JButton("7");
            button7.addActionListener((ActionEvent e) -> {
                resetOutput();
                output.append("- These are your current measurements: \n");
                showMeasurements(measurements);
                
            });
            
            // Send measurements to server
            JButton button8 = new JButton("8");
            button8.addActionListener((ActionEvent e) -> {
                try {
                    responses = this.controller.sendMeasurements(2);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                output.append("- Measurements sent to server." + "\n");
            });
            
            JButton button9 = new JButton("9");
            button9.addActionListener((ActionEvent e) -> {
                resetOutput();
                showResponses(responses);
            });
            
            controlField.add(button1);
            controlField.add(button2);
            controlField.add(button3);
            controlField.add(button4);
            controlField.add(button5);
            controlField.add(button6);
            controlField.add(button7);
            controlField.add(button8);
            controlField.add(button9);

            frame.getContentPane().add(BorderLayout.SOUTH, controlField);

            frame.setVisible(true);
             
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
            output.append("Measurement nr: " + (i + 1) + "\n");
            output.append(measurements[i].toString() + "\n");
        }
    }

    private void showResponses(String[] responses) {
        for (int i = 0; i < responses.length; i++) {
            output.append("Response nr: " + (i +1) + "\n");
            output.append(responses[i].replaceAll("\n", "") + "\n\n");
        }
    }
    
    private void resetOutput(){
        this.output.setText(null);
    }
}
