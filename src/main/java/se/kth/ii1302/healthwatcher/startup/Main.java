package se.kth.ii1302.healthwatcher.startup;

import se.kth.ii1302.healthwatcher.controller.Controller;
import se.kth.ii1302.healthwatcher.integration.InformationTransferProvider;
import se.kth.ii1302.healthwatcher.integration.MeasurementsTransferServiceProvider;
import se.kth.ii1302.healthwatcher.model.DeviceIDHandler;
import se.kth.ii1302.healthwatcher.model.DeviceIDManager;
import se.kth.ii1302.healthwatcher.model.EncryptionProvider;
import se.kth.ii1302.healthwatcher.model.EncryptionServiceProvider;
import se.kth.ii1302.healthwatcher.view.MainView;

/**
 * Initialize the needed components to run the application.
 */
public class Main {
    public static void main(String[] args) {
        try {
            DeviceIDHandler deviceIDManager = new DeviceIDManager();
            InformationTransferProvider measurementsTransferServiceProvider =
                    new MeasurementsTransferServiceProvider("https://httpbin.org/anything");
            EncryptionProvider encryptionServiceProvider = new EncryptionServiceProvider();
            Controller controller = new Controller((DeviceIDManager) deviceIDManager,
                    (MeasurementsTransferServiceProvider) measurementsTransferServiceProvider,
                    (EncryptionServiceProvider) encryptionServiceProvider);
            MainView appInterface = new MainView(controller);
            appInterface.runInterface();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
