import lejos.nxt.*;
import lejos.nxt.comm.*;
import java.io.*;

public class USBRecTest {

    public static void main(String [] args)  throws Exception 
    {
        String connected = "Connected";
        String waiting = "Waiting...";
        String closing = "Closing...";
        
        while (true)
        {
            LCD.drawString(waiting,0,0);
            LCD.refresh();

            USBConnection btc = USB.waitForConnection();
            
            LCD.clear();
            LCD.drawString(connected,0,0);
            LCD.refresh();  

            DataInputStream dis = btc.openDataInputStream();
            DataOutputStream dos = btc.openDataOutputStream();
            
            for(int i=0;i<100;i++) {
                int n = dis.readInt();
                LCD.drawInt(n,7,0,1);
                LCD.refresh();
                dos.writeInt(-n);
                dos.flush();
            }
            
            dis.close();
            dos.close();
            Thread.sleep(100); // wait for data to drain
            LCD.clear();
            LCD.drawString(closing,0,0);
            LCD.refresh();
            btc.close();
            LCD.clear();
        }
    }
}

