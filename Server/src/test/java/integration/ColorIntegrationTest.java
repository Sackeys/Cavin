package integration;

import fr.univ_smb.isc.m2.models.Color;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

public class ColorIntegrationTest {

    private int port = 8080;
    private JSONArray sample = JsonReader.getArray("Colors", Arrays.asList("id"));

    public ColorIntegrationTest() throws IOException, ParseException {
    }

    // Récupère les couleurs à l'URL /api/color
    List<Color> getColors() throws IOException, URISyntaxException {
        HttpUriRequest request = new HttpGet(new URL("http://localhost:" + port + "/api/color/").toURI());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        return RetrieveUtil.retrieveResourceFromResponse(response, new ArrayList<Color>().getClass());
    }

    @Test
    public void should_200_On_All_Colors() throws IOException, URISyntaxException {
        HttpUriRequest request = new HttpGet(new URL("http://localhost:" + port + "/api/color/").toURI());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(SC_OK);
    }

    @Test
    public void should_404_On_Non_Existing_Color() throws IOException, URISyntaxException {
        HttpUriRequest request = new HttpGet(new URL("http://localhost:" + port + "/api/color/" + 25).toURI());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(SC_NOT_FOUND);
    }

    @Test
    public void should_Find_All_Colors() throws IOException, URISyntaxException {
        List<Color> getColors = getColors();
        assertThat(getColors.size()).isEqualTo(17);
    }

    @Test
    public void should_Equals_To() throws IOException, URISyntaxException {
        List<Color> getColors = getColors();
        JSONArray jsonColors = JsonReader.toArray(getColors);

        assertThat(jsonColors).containsExactlyElementsOf(sample);
    }
}
