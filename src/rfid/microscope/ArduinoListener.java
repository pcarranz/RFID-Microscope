/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rfid.microscope;

import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/*
 * Handle serial COM port reading and events for RFID readers.
 */
class ArduinoListener implements SerialPortEventListener
{

    @Override
    public void serialEvent(SerialPortEvent event)
    {
        if(event.isRXCHAR())
        {
            //If data is available
            if(event.getEventValue() == 10)
            {
                
            }
        }
    }
    
}
