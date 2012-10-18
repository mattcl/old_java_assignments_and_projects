/**
 * DataManager.java
 */
package rampancy_old.data;

import rampancy_old.RampantRobot;
import robocode.*;
import robocode.util.*;

import java.io.*;
import java.util.*;
import java.util.zip.*;

/**
 * This class manages data loading and saving
 * @author Matthew Chun-Lum
 *
 */
public class DataManager {
    public RampantRobot robot;
    public ArrayList<EnemyDataModel> dataToExport;
    
    public DataManager(RampantRobot robot) {
        this.robot = robot;
        dataToExport = new ArrayList<EnemyDataModel>();
    }
    
    public void exportData() {
        for(EnemyDataModel data : dataToExport) {
            write(data);
        }
        System.out.println("Data saved");
        System.out.println("Storage space remaining: " + robot.getDataQuotaAvailable() + " bytes");
    }
    
    public EnemyDataModel getDataFor(String name) {
        try {
            ZipInputStream zipin = new ZipInputStream(new FileInputStream(robot.getDataFile(name + ".zip")));
            zipin.getNextEntry();
            ObjectInputStream in = new ObjectInputStream(zipin);
            Object obj = in.readObject();
            in.close();
            if(obj instanceof EnemyDataModel) {
                System.out.println("Loading saved gun data for " + name);
                return (EnemyDataModel) obj;
            }
        } catch (FileNotFoundException e) {
            System.out.println("First time fighting " + name);
        } catch (IOException e) {
            System.out.println("Error reading file for " + name + "!");
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found exception");
            e.printStackTrace();
        } 
        return null;
    }
    
    // ------------ Private ------------- //
    
    /**
     * Writes a file to the data directory
     */
    private void write(EnemyDataModel data) {
        
        String filename = data.enemyName;
        
        try {
            ZipOutputStream zip = new ZipOutputStream(new RobocodeFileOutputStream(robot.getDataFile(filename + ".zip")));
            zip.putNextEntry(new ZipEntry(filename));
            ObjectOutputStream out = new ObjectOutputStream(zip);
            out.writeObject(data);
            out.flush();
            zip.closeEntry();
            out.close();
        } catch (IOException e) {
            System.out.println("Error writing object: " + e.toString());
        }
    }
}
