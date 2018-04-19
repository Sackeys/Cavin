package integration;

import fr.univ_smb.isc.m2.models.Grape;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GrapeIntegrationTest {

    private int port = 8080;
    private JSONArray sample = JsonReader.getArray("Grapes");

    public GrapeIntegrationTest() throws IOException, ParseException {
    }

    // Récupère les sépages à l'URL /api/grape
    List<Grape> getGrapes() throws IOException, URISyntaxException {
        HttpUriRequest request = new HttpGet(new URL("http://localhost:" + port + "/api/grape/").toURI());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        return RetrieveUtil.retrieveResourceFromResponse(response, new ArrayList<Grape>().getClass());
    }

    @Test
    public void T1_Should_200_On_All_Grapes() throws IOException, URISyntaxException {
        HttpUriRequest request = new HttpGet(new URL("http://localhost:" + port + "/api/grape/").toURI());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(SC_OK);
    }

    @Test
    public void T2_Should_Find_All_Grapes() throws IOException, URISyntaxException {
        assertThat(getGrapes().size()).isEqualTo(12);
    }

    @Test
    public void T3_Should_All_Grapes_Equal_To() throws IOException, URISyntaxException, ParseException {
        JSONArray json = JsonReader.toArray(getGrapes());

        assertThat(json).containsExactlyInAnyOrderElementsOf(sample);
    }
}