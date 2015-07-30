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
public class RFIDTimer {

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

            try {
               serialPortB.openPort();
               serialPortB.addEventListener(new COM_B_listener());
               System.out.println("Arrow 1 On: "
                    + arduinoPort.writeInt(4));
            }
            catch (SerialPortException ex) {
               System.out.println(ex);
            } 
         }
         else if (isMicroscopeOn) {
            isMicroscopeOn = false;

            try {
               serialPortC.openPort();
               serialPortC.addEventListener(new COM_C_listener());
               System.out.println("Arrow 2 On: "
                    + arduinoPort.writeInt(5));
            }
            catch (SerialPortException ex) {
               System.out.println(ex);
            } 
         }

         timer.cancel();
      }
   }
}
