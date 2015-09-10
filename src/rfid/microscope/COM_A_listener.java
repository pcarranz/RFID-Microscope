package rfid.microscope;

import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/*
 * Handle serial COM port reading and events for Facts reader. 
 * @author Patricia
 * Carranza
 */
class COM_A_listener implements SerialPortEventListener {
   // Read 12 bytes from serial port
   private final int NUM_BYTES = 12;

   @Override
   public void serialEvent(SerialPortEvent event) {
      if (event.isRXCHAR()) {
         //If data is available
         if (event.getEventValue() == NUM_BYTES) {
            //Check bytes count in the input buffer
            try {
               // Save current tag Id
               RFIDMicroscope.tagId = RFIDMicroscope.serialPortA.readString();
               System.out.println("Tag ID: " + RFIDMicroscope.tagId);

               RFIDMicroscope.factsHandler();
            }
            catch (SerialPortException ex) {
               System.out.println(ex);
            }
         }
      }
   }
}
