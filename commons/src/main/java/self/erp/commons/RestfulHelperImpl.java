package self.erp.commons;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class RestfulHelperImpl implements RestfulHelper {

    private static final RestTemplate restTemplate = new RestTemplate();

    @Override
    public int post(String url, Object o) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Object> entity = new HttpEntity<Object>(o, headers);
        ResponseEntity<Object> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
        return responseEntity.getStatusCode().value();
    }
}
