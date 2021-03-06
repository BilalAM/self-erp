package self.erp.commons.restful;

import javax.ws.rs.core.Response;
import java.io.File;

/**
 * @author BilalAM (github.com/BilalAM)
 */
public interface RestfulHelper {
    Response post(String url, Object o);

    Object get(String url, Class clazz);

    File getFile(String url, String pathToDownload);
}
