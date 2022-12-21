package org.team5507.lib.util;

import edu.wpi.first.wpilibj.DriverStation;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * @author Finn Frankis
 * @version Nov 18, 2018
 */
public class Logger {
    private static String defaultLoc = "/home/lvuser/logs/";
    private String fileLoc;
    private PrintWriter logger;

    public Logger() {
        this.fileLoc = defaultLoc;
    }

    public Logger(String fileLoc) {
        this.fileLoc = fileLoc;
    }

    public void start() {
        String fileName =
                DriverStation.getEventName()
                        + DriverStation.getMatchType()
                        + "-"
                        + DriverStation.getAlliance()
                        + DriverStation.getLocation()
                        + "-"
                        + DriverStation.getMatchNumber()
                        + ".txt";
        try {
            logger = new PrintWriter(fileLoc + fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void log(String val) {
        String message =
                (DriverStation.isAutonomous() ? "A" : "T")
                        + DriverStation.getMatchTime()
                        + " "
                        + val;
        if (logger != null) {
            logger.println(message);
        } else {
            System.out.println(message);
        }
    }
}
