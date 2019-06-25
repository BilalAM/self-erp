package self.erp.commons.prereqs;

import org.apache.commons.io.IOUtils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PreReqs {
        private static final Logger LOGGER = Logger.getLogger("PreReqs");
        private static final String SUDO_PASSWORD = "mib";
        private static final String START_MYSQL_SERVICE =
                "echo \"" + SUDO_PASSWORD + "\"| sudo -S systemctl start mysqld ";
        private static final String BASH_PREFIX = "/bin/bash";
        private static final String C_FLAG = "-c";
        private static final String START_MYSQL_SERVER = "mysql u root -p\"" + SUDO_PASSWORD + "\"";

        public static void startMysqlService() {
                LOGGER.log(Level.INFO, "Starting up mysql service on host as sudo user");
                int returnCode = -100;
                try {
                        LOGGER.log(Level.INFO, START_MYSQL_SERVICE);
                        String[] cmd = { "/bin/bash", "-c", START_MYSQL_SERVICE };
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
                }
        }
}
