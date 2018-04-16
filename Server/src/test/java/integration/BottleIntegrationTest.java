package integration;

import fr.univ_smb.isc.m2.models.Bottle;
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

import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

public class BottleIntegrationTest {

    private int port = 8080;
    private JSONArray sample = JsonReader.getArray("Bottles");

    public BottleIntegrationTest() throws IOException, ParseException {
    }

    // Récupère les bouteilles à l'URL /api/bottle
    List<Color> getBottles() throws IOException, URISyntaxException {
        HttpUriRequest request = new HttpGet(new URL("http://localhost:" + port + "/api/bottle/").toURI());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        return RetrieveUtil.retrieveResourceFromResponse(response, new ArrayList<Bottle>().getClass());
    }

    @Test
    public void should_200_On_All_Bottles() throws IOException, URISyntaxException {
        HttpUriRequest request = new HttpGet(new URL("http://localhost:" + port + "/api/bottle/").toURI());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(SC_OK);
    }

    @Test
    public void should_404_On_Non_Existing_Bottle() throws IOException, URISyntaxException {
        HttpUriRequest request = new HttpGet(new URL("http://localhost:" + port + "/api/bottle/" + 25).toURI());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(SC_NOT_FOUND);
    }

    @Test
    public void should_Find_All_Bottles() throws IOException, URISyntaxException {
        assertThat(getBottles().size()).isEqualTo(3);
    }

    @Test
    public void should_Equals_To() throws IOException, URISyntaxException, ParseException {
        JSONArray jsonBottles = JsonReader.toArray(getBottles());

        assertThat(jsonBottles).containsExactlyInAnyOrderElementsOf(sample);
    }
}
