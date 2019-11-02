package self.erp.commons.restful;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @author BilalAM (github.com/BilalAM)
 */
@Service
public class RestfulHelperImpl implements RestfulHelper {

    private Client client = ClientBuilder.newClient();

    @Override
    public Response post(String url, Object o) {
        return client.target(url).request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(o, MediaType.APPLICATION_JSON));
    }

    @Override
    public Object get(String url, Class clazz) {
        return client.target(url).request(MediaType.APPLICATION_JSON).get(clazz);
    }

    @Override
    public File getFile(String url, String pathToDownloadWithName) {
        File downloadedFile = null;
        Response downloadedFileResponse = null;
        try {
            downloadedFile = new File(pathToDownloadWithName);
            if (!downloadedFile.exists()) {
                downloadedFile.createNewFile();
                downloadedFileResponse = client.target(url).request(MediaType.APPLICATION_OCTET_STREAM_TYPE).get();
                IOUtils.copy(downloadedFileResponse.readEntity(InputStream.class),
                        new FileOutputStream(downloadedFile));
            } else {
                return downloadedFile;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return downloadedFile;
    }

}
