package self.erp.commons.prereqs;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

/**
 * A class that is responsible for pre-loading and configuration different services , drivers and softwares to be used
 * by our microservices and project throughout.
 *
 * @author : BilalAM
 */
public class DatabasePreRequisites {

    private static final Logger LOGGER = Logger.getLogger(DatabasePreRequisites.class.getClass());
    private static final String SUDO_PASSWORD = "mib";
    private static final String START_MYSQL_SERVICE = "echo \"" + SUDO_PASSWORD + "\"| sudo -S systemctl start mysqld ";
    private static final String BASH_PREFIX = "/bin/bash";
    private static final String C_FLAG = "-c";
    private static final String WHICH_MYSQL = "which mysql";

    /**
     * Starts the MySQL service AS ROOT USER .
     */
    public static void startMysqlService() {
        LOGGER.info("Starting up mysql service on host as sudo user");
        int returnCode = -100;
        try {
            LOGGER.info(START_MYSQL_SERVICE);
            String[] cmd = { BASH_PREFIX, C_FLAG, START_MYSQL_SERVICE };
            Process p = Runtime.getRuntime().exec(cmd);
            returnCode = p.waitFor();
            if (returnCode == 0) {
                String message = "Successfully started mysql service as sudo user";
                LOGGER.info(message);
                System.out.println(IOUtils.toString(p.getInputStream()));
            } else {
                String message = "Error ! , Unable to start mysql service as sudo user";
                LOGGER.error(message);
                throw new RuntimeException(message);
            }
        } catch (Exception e) {
            LOGGER.error("Something went wrong", e);
            throw new RuntimeException("Halting execution of program , logs : ", e);
        }
    }

    /**
     * Check if mysql is installed or not . Executes which command to see .
     * 
     * @return : true if which mysql returns the path of the installed binary . false if it returns nothing , which
     *         means mysql is not installed .
     */
    public static boolean doesMySQLExists() {
        Process whichCommandProcess;
        int statusCode = 100;
        try {
            String[] whichCommand = new String[] { BASH_PREFIX, C_FLAG, WHICH_MYSQL };
            whichCommandProcess = Runtime.getRuntime().exec(whichCommand);
            String outputOfCommand = IOUtils.toString(whichCommandProcess.getInputStream(), "UTF-8");
            if (outputOfCommand.contains("/mysql")) {
                LOGGER.info("mysql is installed [ " + outputOfCommand + "  ] ");
                return true;
            } else {
                LOGGER.error("mysql is not installed !");
                return false;
            }
        } catch (Exception exception) {
            LOGGER.error("Something has gone wrong !", exception);
            throw new RuntimeException(exception);
        }
    }
}
