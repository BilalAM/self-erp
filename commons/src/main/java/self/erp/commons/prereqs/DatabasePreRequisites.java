package self.erp.commons.prereqs;

import org.apache.commons.io.IOUtils;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class that is responsible for pre-loading and configuration different services , drivers and softwares to be used
 * by our microservices and project throughout.
 *
 * @author : BilalAM
 */
public class DatabasePreRequisites {
    private static final Logger LOGGER = Logger.getLogger("PreReqs");
    private static final String SUDO_PASSWORD = "mib";
    private static final String START_MYSQL_SERVICE = "echo \"" + SUDO_PASSWORD + "\"| sudo -S systemctl start mysqld ";
    private static final String BASH_PREFIX = "/bin/bash";
    private static final String C_FLAG = "-c";

    /**
     * Starts the MySQL service AS ROOT USER .
     */
    public static void startMysqlService() {
        LOGGER.log(Level.INFO, "Starting up mysql service on host as sudo user");
        int returnCode = -100;
        try {
            LOGGER.log(Level.INFO, START_MYSQL_SERVICE);
            String[] cmd = { BASH_PREFIX, C_FLAG, START_MYSQL_SERVICE };
            Process p = Runtime.getRuntime().exec(cmd);
            returnCode = p.waitFor();
            if (returnCode == 0) {
                String message = "Successfully started mysql service as sudo user";
                LOGGER.log(Level.INFO, message);
                System.out.println(IOUtils.toString(p.getInputStream()));
            } else {
                String message = "Error ! , Unable to start mysql service as sudo user";
                LOGGER.log(Level.SEVERE, message);
                throw new RuntimeException(message);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Something went wrong", e);
            throw new RuntimeException("Halting execution of program , logs : ", e);
        }
    }
}
