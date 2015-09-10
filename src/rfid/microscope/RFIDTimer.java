package rfid.microscope;

import java.util.Timer;
import java.util.TimerTask;
import jssc.SerialPortException;
import static rfid.microscope.RFIDMicroscope.arduinoPort;
import static rfid.microscope.RFIDMicroscope.serialPortB;
import static rfid.microscope.RFIDMicroscope.serialPortC;
import static rfid.microscope.RFIDMicroscope.isFactsOn;
import static rfid.microscope.RFIDMicroscope.isMicroscopeOn;

/**
 *
 * @author Patricia Carranza
 */
public class RFIDTimer implements Constants {

   public static Timer timer;

   // Runs after 5 seconds
   public RFIDTimer() {
      timer = new Timer(true);
      timer.schedule(new RFIDTimerReminder(), 5 * 1000);
      System.out.println("Timer scheduled");
   }

   class RFIDTimerReminder extends TimerTask {

      public void run() {
         if (isFactsOn) {
            isFactsOn = false;

            // Open Microscope port and turn on Arrow 1
            try {
               serialPortB.openPort();
               serialPortB.addEventListener(new COM_B_listener());
               System.out.println("Arrow 1 On: "
                    + arduinoPort.writeInt(ARROW_1_LED_ON));
            }
            catch (SerialPortException ex) {
               System.out.println(ex);
            } 
         }
         else if (isMicroscopeOn) {
            isMicroscopeOn = false;

            // Turn on Video port and turn on Arrow 2
            try {
               serialPortC.openPort();
               serialPortC.addEventListener(new COM_C_listener());
               System.out.println("Arrow 2 On: "
                    + arduinoPort.writeInt(ARROW_2_LED_ON));
            }
            catch (SerialPortException ex) {
               System.out.println(ex);
            } 
         }

         // Stop timer
         timer.cancel();
      }
   }
}
