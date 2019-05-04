package self.erp.commons.restful;

public interface RestfulHelper {
    int post(String url, Object o);

    Object get(String url, Class clazz);
}
