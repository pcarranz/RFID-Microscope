package rfid.microscope;

import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/**
 * Handle serial COM port reading and events for Video reader.
 *
 * @author Patricia Carranza
 */
class COM_C_listener implements SerialPortEventListener {

   @Override
   public void serialEvent(SerialPortEvent event) {
      if (event.isRXCHAR()) {
         //If data is available
         if (event.getEventValue() == 12) {
            //Check bytes count in the input buffer
            try {
               // Save current tag Id
               RFIDMicroscope.tagId = RFIDMicroscope.serialPortC.readString(); //Read 12 bytes from serial port
               System.out.println("Tag ID: " + RFIDMicroscope.tagId);

               RFIDMicroscope.videoHandler();
            }
            catch (SerialPortException ex) {
               System.out.println(ex);
            }
         }
      }
   }
}
