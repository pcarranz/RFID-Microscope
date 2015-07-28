package rfid.microscope;

import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/*
 * Handle serial COM port reading and events for Facts reader.
 * @author Patricia Carranza
 */
class COM_A_listener implements SerialPortEventListener
{
    @Override
    public void serialEvent(SerialPortEvent event)
    {
        if(event.isRXCHAR())
        {
            //If data is available
            if(event.getEventValue() == 12)
            { 
                //Check bytes count in the input buffer
                try
                {
                    // Save current tag Id
                    RFIDMicroscope.tagId = RFIDMicroscope.serialPortA.readString(); //Read 12 bytes from serial port
                    System.out.println("Tag ID: " + RFIDMicroscope.tagId);
                                        
//                    // Handle tag based on COM port
//                    if(event.getPortName().equals(Constants.COM_A))
//                    {
                        RFIDMicroscope.factsHandler();
//                    }
                }
                catch(SerialPortException ex)
                {
                    System.out.println(ex);
                }
            }
        }
    }
}