// Cracker.java
/*
 Generates SHA hashes of short strings in parallel.
*/

import java.security.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

import javax.swing.SwingUtilities;

public class Cracker extends Thread {
    public static void main(String[] args) {
        
        String targets[] = {"c5e478e7da53b70f0fabcdefa082e1d1c5a2bc6d", "66b27417d37e024c46526c2f6d358a754fc552f3", "34800e15707fae815d7c90d49de44aca97e2d759"};
        
        Cracker cracker = new Cracker(targets, 4, 0, 5, null);
        
        cracker.start();
    }
    
    // Array of chars used to produce strings
    public static final char[] CHARS = "abcdefghijklmnopqrstuvwxyz0123456789.,-!ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    public static final String ALGORITHM = "SHA";
    
    private ConcurrentHashMap<HashContainer, String> targets;
    private int maxLength;
    private int minLength;
    private int numWorkers;
    
    private Worker[] workers;
    
    private Semaphore workersAreDone;
    
    private HashCracker crackerGUI;
    
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
    
    public Cracker(String[] targs, int maxLength, int minLength, int numWorkers, HashCracker crackerGUI) {
        this.maxLength = maxLength;
        this.minLength = minLength;
        this.numWorkers = numWorkers;
        this.crackerGUI = crackerGUI;
        this.targets = new ConcurrentHashMap<HashContainer, String>();
        for(int i = 0; i < targs.length; i++) {
            this.targets.put(new HashContainer(hexToArray(targs[i])), targs[i]);
        }
        workersAreDone = new Semaphore(0);
       
    }
    
    public void run() {
        forkOffWorkers();
        waitForWorkers();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                crackerGUI.signalDone();
            }
        });
    }
    
    // ----------------- Private ------------------- //
    
    // forks off the specified number of workers
    private void forkOffWorkers() {
        workers = new Worker[numWorkers];
        int segment = CHARS.length/numWorkers;
        for(int i = 0; i < numWorkers; i++) {
            int end = (i == numWorkers - 1 ? CHARS.length : (i+1) * segment);
            Worker worker = new Worker(i*segment, end);
            workers[i] = worker;
            worker.start();
        }
    }
    
    // waits until all of the workers finish
    private void waitForWorkers() {
        try {
            workersAreDone.acquire(numWorkers);
        } catch (InterruptedException ignored) {
            for(Worker worker : workers) {
                worker.interrupt();
            }
        }
    }
    
    // inner Worker class
    private class Worker extends Thread {
         
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
            if(this.isInterrupted()) return;
            if(str.length() >= minLength) {
                digest.reset();
                digest.update(str.getBytes());
                byte[] bytes = digest.digest();
                if(targets.get(new HashContainer(bytes)) != null) {
                    targets.remove(new HashContainer(bytes));
                    printStr(str, bytes);
                }
            }
            if(str.length() >= maxLength) return;
            for(int i = 0; i < CHARS.length; i++)
                search(str + CHARS[i]);
        }
        
        // prints the given string and byte array
        private void printStr(final String str, byte[] bytes) {
            //System.out.println(str + " " + hexToString(bytes));
            final String hash = hexToString(bytes);
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    crackerGUI.addSolution(str, hash);
                }
            });
        }
    }
}
