package self.erp.commons.prereqs;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import self.erp.commons.restful.RestfulHelper;
import self.erp.commons.toolbox.ScriptTools;
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

    @Value("${mysql.password}")
    private String mysqlPassword;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private RestfulHelper restfulHelper;

    private static final Logger LOGGER = Logger.getLogger(DatabasePreRequisites.class.getClass());
    private static String START_MYSQL_SERVICE = "echo '" + "%s" + "' | sudo -S service mysql start ";
    private static final String WHICH_MYSQL = "which mysql";

    /**
     * Starts the MySQL service AS ROOT USER before the Hikari beans are instantiated .
     */
    @PostConstruct
    public void startMysqlService() {
        LOGGER.info("Starting up mysql service on host as sudo user [ " + sudoPassword + " ]");
        try {
            if (mySQLExists()) {
                ScriptTools.execute(String.format(START_MYSQL_SERVICE, sudoPassword));
            } else {
                installMYSQL();
            }
        } catch (Exception e) {

        }
    }

    /**
     * Check if mysql is installed or not . Executes which command to see .
     * 
     * @return : true if which mysql returns the path of the installed binary . false if it returns nothing , which
     *         means mysql is not installed .
     */
    private static boolean mySQLExists() {
        try {
            int returnCode = ScriptTools.execute(WHICH_MYSQL);
            if (returnCode == 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception exception) {
            LOGGER.error("Something has gone wrong !", exception);
            throw new RuntimeException(exception);
        }
    }

    private void installMYSQL() {
        try {
            InputStream installMysqlFileStream = resourceLoader
                    .getResource("classpath:mysql-install-script-template.txt").getInputStream();
            String installMysqlTemplate = IOUtils.toString(installMysqlFileStream, "UTF-8");
            String installMysqlScript = StringTools.replaceString(installMysqlTemplate,
                    Map.of("password", sudoPassword, "mysql-password", mysqlPassword));
            ScriptTools.execute(installMysqlScript);
        } catch (Exception e) {
            LOGGER.error("Something has gone wrong ! ", e);
            throw new RuntimeException(e);
        }
    }

}
