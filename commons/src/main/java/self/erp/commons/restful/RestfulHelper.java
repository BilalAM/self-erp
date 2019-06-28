package self.erp.commons.restful;

import javax.ws.rs.core.Response;

public interface RestfulHelper {
        Response post(String url, Object o);

    Object get(String url, Class clazz);
}
