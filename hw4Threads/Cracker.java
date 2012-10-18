// Cracker.java
/*
 Generates SHA hashes of short strings in parallel.
*/

import java.security.*;
import java.util.Arrays;
import java.util.concurrent.Semaphore;

public class Cracker {
	// Array of chars used to produce strings
	public static final char[] CHARS = "abcdefghijklmnopqrstuvwxyz0123456789.,-!ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	public static final String ALGORITHM = "SHA";
	
	private static byte[] target;
	private static int maxLength;
	private static int minLength;
	private static boolean print;
	
	private static Semaphore workersAreDone;
	
	/*
	 Given a byte[] array, produces a hex String,
	 such as "234a6f". with 2 chars for each byte in the array.
	 (provided code)
	*/
	public static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
	
	/*
	 Given a string of hex byte values such as "24a26f", creates
	 a byte[] array of those values, one byte value -128..127
	 for each 2 chars.
	 (provided code)
	*/
	public static byte[] hexToArray(String hex) {
		byte[] result = new byte[hex.length()/2];
		for (int i=0; i<hex.length(); i+=2) {
			result[i/2] = (byte) Integer.parseInt(hex.substring(i, i+2), 16);
		}
		return result;
	}
	
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Args: target length [workers]");
			System.exit(1);
		}
		// args: targ len [num]
		String targ = args[0];
		int len = Integer.parseInt(args[1]);
		int num = 1;
		int minLen = 0;
		if (args.length>2) {
			num = Integer.parseInt(args[2]);
		}
		
		if(args.length > 3) {
		    minLen = Integer.parseInt(args[3]);
		}
		// a! 34800e15707fae815d7c90d49de44aca97e2d759
		// xyz 66b27417d37e024c46526c2f6d358a754fc552f3
		// 2k7! c5e478e7da53b70f0fabcdefa082e1d1c5a2bc6d
		
		maxLength = len;
		minLength = minLen;
		print = targ.equalsIgnoreCase("print");
		target = (print ? null : hexToArray(targ));
		if(print) System.out.println("target: " + targ);
		System.out.println("maximum length: "+len);
        System.out.println("minimum length: "+minLen);
		System.out.println();
		workersAreDone = new Semaphore(0);
		
		forkOffWorkers(num);
		waitForWorkers(num);
		System.out.println("all done");
	}
	
	// ----------------- Private ------------------- //
	
	// forks off the specified number of workers
	private static void forkOffWorkers(int num) {
        int segment = CHARS.length/num;
        for(int i = 0; i < num; i++) {
            int end = (i == num - 1 ? CHARS.length : (i+1) * segment);
            Thread worker = new Thread(new Worker(i*segment, end));
            worker.start();
        }
    }
	
	// waits until all of the workers finish
	private static void waitForWorkers(int num) {
	    try {
            workersAreDone.acquire(num);
        } catch (Exception ignored) {}
	}
	
	// inner Worker class
	private static class Worker implements Runnable {
	     
	    private int begin;
	    private int end;
	    
	    private MessageDigest digest;
	    
	    public Worker(int begin, int end) {
	        
	        this.begin = begin;
	        this.end = end;
	        
	        try {
	            digest = MessageDigest.getInstance(ALGORITHM);
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.exit(1);
	        }
	    }
	    
	    public void run() {
	        for(int i = begin; i < end; i++)
	            search(Character.toString(CHARS[i]));
	        workersAreDone.release();
	    }
	    
	    // recursive search function that generates all possible string
	    // combinations given a starter string
	    private void search(String str) {
	        if(str.length() >= minLength) {
	            digest.reset();
	            digest.update(str.getBytes());
	            byte[] bytes = digest.digest();
	            if(print) printStr(str, bytes);
	            else if(MessageDigest.isEqual(bytes, target)) printStr("match:"+str, bytes);
	        }
	        if(str.length() >= maxLength) return;
	        for(int i = 0; i < CHARS.length; i++)
	            search(str + CHARS[i]);
	    }
	    
	    // prints the given string and byte array
	    private void printStr(String str, byte[] bytes) {
	        System.out.println(str + " " + hexToString(bytes));
	    }
	}
}
