package self.erp.commons.restful;

import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class RestfulHelperImpl implements RestfulHelper {

    private Client client = ClientBuilder.newClient();

    @Override public Response post(String url, Object o) {
        return client.target(url).request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(o, MediaType.APPLICATION_JSON));
    }

    @Override
    public Object get(String url, Class clazz) {
        return client.target(url).request(MediaType.APPLICATION_JSON).get(clazz);
    }
}
