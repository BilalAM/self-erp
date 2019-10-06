package self.erp.commons.prereqs;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import self.erp.commons.restful.RestfulHelper;

import java.io.File;

/**
 * A class that is responsible for pre-loading and configuration different services , drivers and softwares to be used
 * by our microservices and project throughout.
 *
 * @author : BilalAM
 */
public class DatabasePreRequisites {

    @Autowired
    private  RestfulHelper restfulHelper;
    private static final Logger LOGGER = Logger.getLogger(DatabasePreRequisites.class.getClass());
    private static final String SUDO_PASSWORD = "mib";
    private static final String START_MYSQL_SERVICE = "echo \"" + SUDO_PASSWORD + "\"| sudo -S systemctl start mysqld ";
    private static final String BASH_PREFIX = "/bin/bash";
    private static final String C_FLAG = "-c";
    private static final String WHICH_MYSQL = "which mysql";
    private static final String MYSQL_DOWNLOAD_LINK = "https://cdn.mysql.com//Downloads/MySQL-8.0/mysql-8.0.17-linux-glibc2.12-i686.tar.xz";
    private static final String MYSQL_BINARY_PATH = "/tmp/mysql.tar.gz";
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
    private static boolean doesMySQLExists() {
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



    private File downloadAndInstallMySQL(){
        File mysqlBinary = new File(MYSQL_BINARY_PATH);
        try {
            if(mysqlBinary.isFile()){
                // file already exists , start extracting it
            }else {
                mysqlBinary = restfulHelper.getFile(MYSQL_DOWNLOAD_LINK, MYSQL_BINARY_PATH);
                // file doesnt exist , download it on the provided path , extract it and run the install commands.
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return mysqlBinary;
    }


}
