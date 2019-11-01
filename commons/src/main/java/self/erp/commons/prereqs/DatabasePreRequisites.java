package self.erp.commons.prereqs;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import self.erp.commons.restful.RestfulHelper;
import self.erp.commons.toolbox.StringTools;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContextListener;
import java.io.InputStream;
import java.util.Map;

/**
 * A class that is responsible for pre-loading and configuration MYSQL server on startup of the ErpApplication. This
 * class implements the ServletContextListener interface so that the database startup process takes before the Hikari
 * Pool beans are instantiated .
 *
 * @author : BilalAM
 */
@Component
@PropertySource(value = "classpath:common.application.properties")
public class DatabasePreRequisites implements ServletContextListener {

    @Value("${bash.root.password}")
    private String sudoPassword;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private RestfulHelper restfulHelper;

    private static final Logger LOGGER = Logger.getLogger(DatabasePreRequisites.class.getClass());
    private static String START_MYSQL_SERVICE = "echo \"" + "%s" + "\"| sudo service mysql start ";
    private static final String BASH_PREFIX = "/bin/bash";
    private static final String C_FLAG = "-c";
    private static final String WHICH_MYSQL = "which mysql";
    private static final String MYSQL_DOWNLOAD_LINK = "https://dev.mysql.com/get/Downloads/MySQL-5.7/mysql-5.7.27-linux-glibc2.12-i686.tar.gz";
    private static final String MYSQL_BINARY_PATH = "/tmp/mysql.tar.gz";

    /**
     * Starts the MySQL service AS ROOT USER before the Hikari beans are instantiated .
     */
    @PostConstruct
    public void startMysqlService() {
        installMYSQL();
        LOGGER.info("Starting up mysql service on host as sudo user [ " + sudoPassword + " ]");
        int returnCode = -100;
        try {
            LOGGER.info(START_MYSQL_SERVICE);
            String[] cmd = { BASH_PREFIX, C_FLAG, String.format(START_MYSQL_SERVICE, sudoPassword) };
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

    public void installMYSQL() {
        try {
            InputStream installMysqlFileStream = resourceLoader
                    .getResource("classpath:mysql-install-script-template.txt").getInputStream();
            String installMysqlString = IOUtils.toString(installMysqlFileStream, "UTF-8");
            String a = StringTools.replaceString(installMysqlString,
                    Map.of("password", "mib", "mysql-password", "p@ssw0rd"));
            System.out.println(a);
        } catch (Exception e) {

        }
    }

}
