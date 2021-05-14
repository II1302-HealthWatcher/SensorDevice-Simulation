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
    private int delay;
    
    /**
     * Constructs a MainView and sets the controller field.
     * @param controller 
     */
    public MainView(Controller controller) {
        this.controller = controller;
        this.output = new JTextArea("");
        this.output.setEditable(false);
        this.measurements = null;
        this.testDate = null;
        this.amount = 0;
        this.responses = null;
        this.delay = 0;
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
            frame.setSize(900, 500);
            frame.setLocationRelativeTo(null);

            // Adding filler at the top
            JPanel filler = new JPanel();
            frame.getContentPane().add(BorderLayout.NORTH, filler);

            // Static text area with option menu
            JTextArea textArea = new JTextArea(
                 "  Available actions \n\n"+
                 "  1. Get device id. \n"+
                 "  2. Set certain device id. \n"+
                 "  3. Generate a random device id. \n"+
                 "  4. Generate measurement for the current device. \n"+
                 "  5. Generate multiple measurements for the current device. \n"+
                 "  6. Generate critical measurement for the current device. \n"+     
                 "  7. Get the current generated measurements. \n"+
                 "  8. Send the current measurements to the server. \n"+
                 "  9. Print server responses. \n"
             );
            // Setting the properties of the textarea
            textArea.setEditable(false);
            textArea.setPreferredSize(new Dimension(400, 500));
            Color defaultColor = UIManager.getColor("Panel.background");
            textArea.setBackground(defaultColor);
            Font font = textArea.getFont();  
            textArea.setFont(font.deriveFont(Font.BOLD));
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
                    if (newDeviceId != null){
                        this.controller.setCertainDeviceId(newDeviceId);
                        output.append("- New device ID has been set.\n");
                    }
                    else
                        output.append("- No device ID was chosen.\n");
                } catch (IOException ex) {
                    Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
                }
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
                Date newDate = convertToDate();
                this.controller.generateMeasurementForDevice(newDate);
                measurements = this.controller.getGeneratedMeasurements();
                output.append("- A measurement has been generated." + "\n");
            });
            
            // Generate multiple measurements
            JButton button5 = new JButton("5");
            button5.addActionListener((ActionEvent e) -> {
                Date newDate = convertToDate();
                String inputAmount = JOptionPane.showInputDialog(frame,"Please write how many measurements you want.");
                this.controller.generateMeasurementsForDevice(newDate, Integer.parseInt(inputAmount));
                measurements = this.controller.getGeneratedMeasurements();
                output.append("- Measurements has been generated." + "\n");
            });
            
            // Generate critical measurement
            JButton button6 = new JButton("6");
            button6.addActionListener((ActionEvent e) -> {
                Date newDate = convertToDate();
                this.controller.generateCriticalMeasurementForDevice(newDate);
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
                String inputDelay = JOptionPane.showInputDialog(frame,"Please write the desired delay in seconds.");
                try {
                    responses = this.controller.sendMeasurements(Integer.parseInt(inputDelay));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                output.append("- Measurements sent to server." + "\n");
            });
            
            // Show the server responses
            JButton button9 = new JButton("9");
            button9.addActionListener((ActionEvent e) -> {
                resetOutput();
                showResponses(responses);
            });
            
            // Reset the output console
            JButton button10 = new JButton("Clear");
            button10.addActionListener((ActionEvent e) -> {
                resetOutput();
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
            controlField.add(button10);

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
    
    // Helper function to assist in parsing strings to date
    private Date convertToDate(){
        Date parsedDate = null;
        JTextField yearField = new JTextField();
        JTextField monthField = new JTextField();
        JTextField dayField = new JTextField();
        JTextField hourField = new JTextField();
        JTextField minuteField = new JTextField();
        Object[] message = {
            "Year", yearField,
            "Month", monthField,
            "Day", dayField,
            "Hour", hourField,
            "Minute", minuteField,
        };
        int option = JOptionPane.showConfirmDialog(null, message, "Enter all your values", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION)
        {
            String year = yearField.getText();
            String month = monthField.getText();
            String day = dayField.getText();
            String hour = hourField.getText();
            String minute = minuteField.getText();
            parsedDate = new Date(Integer.parseInt(year)-1900,Integer.parseInt(month)-1,Integer.parseInt(day),Integer.parseInt(hour),Integer.parseInt(minute));
        }
        
        return parsedDate;
    }
    
    private void resetOutput(){
        this.output.setText(null);
    }
}
