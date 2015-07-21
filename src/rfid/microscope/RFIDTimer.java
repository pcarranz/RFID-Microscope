package rfid.microscope;

import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;
import jssc.SerialPortException;

/**
 *
 * @author Patricia Carranza
 */
public class RFIDTimer {
   Toolkit toolkit;
   public static Timer timer;

   public RFIDTimer() {
      toolkit = Toolkit.getDefaultToolkit();
      timer = new Timer(true);  // make timer thread a daemon
      timer.schedule(new RFIDTimerReminder(), 0, 500);
   }

   class RFIDTimerReminder extends TimerTask {

      @Override
      public void run() {
         if (RFIDMicroscope.serialPortA.isOpened()) {
            try {
               RFIDMicroscope.serialPortA.closePort();

               if (!RFIDMicroscope.serialPortB.isOpened()) {
                  RFIDMicroscope.serialPortB.openPort();
                  RFIDMicroscope.serialPortB.addEventListener(new COM_B_listener());
               }

               if (RFIDMicroscope.serialPortC.isOpened()) {
                  RFIDMicroscope.serialPortC.closePort();
               }
            }
            catch (SerialPortException e) {
               System.out.println(e);
            }
         }
         else if (RFIDMicroscope.serialPortB.isOpened()) {
            try {
               RFIDMicroscope.serialPortB.closePort();

               if (RFIDMicroscope.serialPortA.isOpened()) {
                  RFIDMicroscope.serialPortA.closePort();
               }

               if (!RFIDMicroscope.serialPortC.isOpened()) {
                  RFIDMicroscope.serialPortC.openPort();
                  RFIDMicroscope.serialPortC.addEventListener(new COM_C_listener());
               }
            }
            catch (SerialPortException e) {
               System.out.println(e);
            }
         }
         else if (RFIDMicroscope.serialPortC.isOpened()) {
            try {
               RFIDMicroscope.serialPortC.closePort();

               if (RFIDMicroscope.serialPortB.isOpened()) {
                  RFIDMicroscope.serialPortB.closePort();
               }

               if (!RFIDMicroscope.serialPortA.isOpened()) {
                  RFIDMicroscope.serialPortA.openPort();
                  RFIDMicroscope.serialPortA.addEventListener(new COM_A_listener());
               }
            }
            catch (SerialPortException e) {
               System.out.println(e);
            }
         }
         //System.out.println("Task handled.");
      }
   }
}
