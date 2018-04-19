package integration;

import fr.univ_smb.isc.m2.models.Region;
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
public class RegionIntegrationTest {

    private int port = 8080;
    private JSONArray sample = JsonReader.getArray("Regions");

    public RegionIntegrationTest() throws IOException, ParseException {
    }

    // Récupère les couleurs à l'URL /api/color
    List<Region> getRegions() throws IOException, URISyntaxException {
        HttpUriRequest request = new HttpGet(new URL("http://localhost:" + port + "/api/region/").toURI());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        return RetrieveUtil.retrieveResourceFromResponse(response, new ArrayList<Region>().getClass());
    }

    @Test
    public void T1_Should_200_On_All_Regions() throws IOException, URISyntaxException {
        HttpUriRequest request = new HttpGet(new URL("http://localhost:" + port + "/api/region/").toURI());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(SC_OK);
    }

    @Test
    public void T2_Should_Find_All_Regions() throws IOException, URISyntaxException {
        assertThat(getRegions().size()).isEqualTo(13);
    }

    @Test
    public void T3_Should_All_Regions_Equal_To() throws IOException, URISyntaxException, ParseException {
        JSONArray json = JsonReader.toArray(getRegions());

        assertThat(json).containsExactlyInAnyOrderElementsOf(sample);
    }
}
