package integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

public class RetrieveUtil {

    public static <T> T retrieveResourceFromResponse(HttpResponse response, Class<T> clazz) throws IOException {
        String jsonFromResponse = null;
        try {
            jsonFromResponse = EntityUtils.toString(response.getEntity());
            ObjectMapper mapper = new ObjectMapper().configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(jsonFromResponse, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

}
