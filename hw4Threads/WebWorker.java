import java.io.*;
import java.net.*;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

public class WebWorker extends Thread {
    private WebFrame frame;
    private String urlString;
    private int row;
    
    public WebWorker(String urlString, int row, WebFrame frame) {
        this.frame = frame;
        this.urlString = urlString;
        this.row = row;
    }
    
    public void run() {
        frame.changeWorkerCount(1);
        frame.updateRow(row, "fetching...");
        
        download();
        
        // signal that we're done
        frame.sendCompletionNotice();
    }

  //This is the core web/download i/o code...
 	public void download() {
		InputStream input = null;
		StringBuilder contents = null;
		try {
		    long startTime = System.currentTimeMillis();
			URL url = new URL(urlString);
			URLConnection connection = url.openConnection();
		
			// Set connect() to throw an IOException
			// if connection does not succeed in this many msecs.
			connection.setConnectTimeout(5000);
			
			connection.connect();
			input = connection.getInputStream();

			BufferedReader reader  = new BufferedReader(new InputStreamReader(input));
		
			char[] array = new char[1000];
			int len;
			contents = new StringBuilder(1000);
			while ((len = reader.read(array, 0, array.length)) > 0) {
				contents.append(array, 0, len);
				Thread.sleep(100);
			}
			
			// Successful download if we get here
			
			prettyStringOutput(contents.length(), System.currentTimeMillis() - startTime);
			
		}
		// Otherwise control jumps to a catch...
		catch(MalformedURLException exception) {
		    frame.updateRow(row, "err");
		}
		catch(InterruptedException exception) {
			frame.updateRow(row, "Interrupted");
		}
		catch(IOException exception) {
		    frame.updateRow(row, "err");
		}
		// "finally" clause, to close the input stream
		// in any case
		finally {
			try{
				if (input != null) input.close();
			}
			catch(IOException ignored) {}
		}
 	}
 	
 	// --------------- Private ---------------- //
 	
 	// update the row in the frame's table with a formatted string
 	private void prettyStringOutput(int size, long elapsedTime) {
 	    StringBuffer buff = new StringBuffer();
 	    SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
 	    format.format(new Date(System.currentTimeMillis()), buff, new FieldPosition(0));
 	    buff.append(" " + elapsedTime + "ms " + size + " bytes");
 	    frame.updateRow(row, new String(buff));
 	    
 	}
	
}
