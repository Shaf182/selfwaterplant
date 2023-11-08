import org.firmata4j.I2CDevice;
import org.firmata4j.IODevice;
import org.firmata4j.Pin;
import org.firmata4j.firmata.FirmataDevice;
import org.firmata4j.ssd1306.SSD1306;
import java.io.IOException;
import java.util.Timer;
import org.firmata4j.Pin; // Firmata
import java.io.IOException;
import java.util.TimerTask;
public class MinorProject {
    static final int A2 = 16; // Moisture Sensor
    static final int D6 = 6; // Button
    static final int D4 = 4; // red LED
    static final int D2 = 2; // MOSFET/Pump
    static final byte I2C0 = 0x3C; // OLED Display

    public static void main(String[] args) throws IOException, InterruptedException {
        var myUSBPort = "COM4"; // TO-DO : modify based on your computer setup.
        var a = new FirmataDevice(myUSBPort);
        a.start();
        a.ensureInitializationIsDone();

            var pump = a.getPin(D2);
            pump.setMode(Pin.Mode.OUTPUT);
            var moistureSensor = a.getPin(A2);
            moistureSensor.setMode(Pin.Mode.ANALOG);
            var task = new pumpTask(pump, moistureSensor);
        Timer timer = new Timer();
        timer.schedule(task,0,10);
        }
    }



public class pumpTask extends TimerTask {
    private final Pin pump;
    private final Pin moisture;

    // The Constructor for ButtonTask
    pumpTask(Pin pump, Pin moisture) {
        // Assign the externally-set "pin" to internal variable myPin
        this.pump = pump;
        this.moisture = moisture;
    }

    @Override
    public void run() {
        var dryValue = 760;
        var wetValue = 550;
        var moistureValue = moisture.getValue();
        if (moistureValue > dryValue) {
            try {
                pump.setValue(1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("The soil is dry: " +moistureValue);
        } else if (moistureValue > dryValue) {
            try {
                pump.setValue(1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("The soil is wet, but not wet enough: " +moistureValue);
        } else if (moistureValue <= dryValue) {
                try {
                    pump.setValue(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("The soil is saturated: " +moistureValue);
        }
    }
}