/**
 * 
 */


import lejos.pc.comm.*;

import java.io.*;
/**
 * @author admin
 *
 */
public class USBSenderTest {

    /**
     * @param args
     * @throws NXTCommException 
     * @throws NXTCommException 
     */
    public static void main(String[] args) {
        NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);

        
        NXTInfo[] nxtInfo = null;
        try {
            nxtInfo = nxtComm.search(null, NXTCommFactory.BLUETOOTH);
        } catch (NXTCommException e) {
            System.out.println("Exception with search");
        }
        try {
            nxtComm.open(nxtInfo[0]);
        } catch (NXTCommException e) {
            System.out.println("Exception with open");
        }
        
        OutputStream out = nxtComm.getOutputStream();
        DataOutputStream dOut = new DataOutputStream(out);
        
        InputStream in = nxtComm.getInputStream();
        DataInputStream dIn = new DataInputStream(in);
        
        for(int i=0;i<100;i++) {
            try {
                System.out.println(i*30000);
                dOut.writeInt(i*30000);
                dOut.flush();            
            } catch (IOException ioe) {
                System.out.println("Write Exception");
            }
            
            try {
                System.out.println(dIn.readInt());
            } catch (IOException ioe) {
                System.out.println("Read Exception ");
            }
        }
        
        try {
            System.out.println("Closing...    ");
            dIn.close();
            dOut.close();
            nxtComm.close();
        } catch (IOException ioe) {
            System.out.println("Close Exception");
        }
        
        
    }

}
