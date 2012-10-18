import java.io.IOException;

import lejos.nxt.*;
import lejos.nxt.comm.*;
import java.io.*;
import javax.bluetooth.*;

/**
 * 
 * Test of NXT to NXT Bluetooth comms.
 * 
 * Connects to another NXT, sends 100 ints, and receives the 
 * replies. Then closes the connection and shuts down.
 * 
 * Works with the BTReceive sample running on the slave NXT.
 * 
 * Change the name string to the name of your slave NXT, and make sure
 * it is in the known devices list of the master NXT. To do this, turn
 * on the slave NXT and make sure Bluetooth is on and the device
 * is visible. Use the Bluetooth menu on the slave for this. Then,
 * on the master, select the Bluetooth menu and then select Search.
 * The name of the slave NXT should appear. Select Add to add
 * it to the known devices of the master. You can check this has
 * been done by selecting Devices from the Bluetooth menu on the
 * master.
 * 
 * @author Lawrie Griffiths
 *
 */
public class BluetoothSenderTest {
    public static void main(String[] args) throws Exception {
        String name = "STARBot";
        
        System.out.println("connecting...");
        
        RemoteDevice btrd = Bluetooth.getKnownDevice(name);

        if (btrd == null) {
            System.out.println("No such device");
            Thread.sleep(2000);
            System.exit(1);
        }
        
        USBConnection btc = USB.connect(btrd);
        
        if (btc == null) {
            System.out.println("Connect fail");
            Thread.sleep(2000);
            System.exit(1);
        }
        
        System.out.println("Connected");
        
        DataInputStream dis = btc.openDataInputStream();
        DataOutputStream dos = btc.openDataOutputStream();
                
        for(int i=0;i<100;i++) {
            try {
                System.out.println(i*30000);
                dos.writeInt(i*30000);
                dos.flush();            
            } catch (IOException ioe) {
                System.out.println("Write Exception");
            }
            
            try {
                System.out.println(dis.readInt());
            } catch (IOException ioe) {
                System.out.println("Read Exception ");
            }
        }
        
        try {
            System.out.println("Closing...    ");
            dis.close();
            dos.close();
            btc.close();
        } catch (IOException ioe) {
            System.out.println("Close Exception");
        }
        
        System.out.println("Finished");
        Thread.sleep(2000);
    }
}
