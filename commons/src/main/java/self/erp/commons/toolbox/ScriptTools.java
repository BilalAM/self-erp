package self.erp.commons.toolbox;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class ScriptTools {

    private static final String BASH_PREFIX = "/bin/bash";
    private static final String C_FLAG = "-c";
    private static final Logger LOGGER = Logger.getLogger(ScriptTools.class);

    public static int execute(String command) {
        Process process;
        int statusCode;
        String outputOfCommand;
        try {
            String[] whichCommand = new String[] { BASH_PREFIX, C_FLAG, command };
            LOGGER.info("Starting to execute command [ " + Arrays.toString(whichCommand) + " ]");
            process = Runtime.getRuntime().exec(whichCommand);
            String line = null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while((line = reader.readLine()) != null){
                System.out.println(line);
            }

            String errorLine = null;
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while((errorLine = errorReader.readLine()) != null){
                System.out.println(errorLine);
            }
            statusCode = process.waitFor();
            if (statusCode == 0) {
                LOGGER.info("Executed succesfully , return code [ " + statusCode + " ]");
                outputOfCommand = IOUtils.toString(process.getInputStream(), "UTF-8");
                LOGGER.info("Output is  [ " + outputOfCommand + " ]");
            } else {
                LOGGER.error("Failed to execute , status code [ " + statusCode + " ]");
                outputOfCommand = IOUtils.toString(process.getErrorStream(), "UTF-8");
                LOGGER.error("Output is [ " + outputOfCommand + " ]");
            }
        } catch (Exception exception) {
            LOGGER.error("Something has gone wrong !", exception);
            throw new RuntimeException(exception);
        }
        return statusCode;
    }
}
