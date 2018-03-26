package integration;

import fr.univ_smb.isc.m2.models.Region;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

public class RegionIntegrationTest {

    private int port = 8080;

    @Test
    public void should_200_On_All_Regions() throws IOException, URISyntaxException {
        HttpUriRequest request = new HttpGet(new URL("http://localhost:" + port + "/api/region/").toURI());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        List<Region> getRegions = RetrieveUtil.retrieveResourceFromResponse(response, new ArrayList<Region>().getClass());

        // Succès
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(SC_OK);

        // 13 régions retournées
        assertThat(getRegions.size()).isEqualTo(13);

        // Régions dans l'ordre alphabétique
        //assertThat(getRegions).isSortedAccordingTo(Comparator.comparing(region -> region.label));
    }

    @Test
    public void should_404_On_Non_Existing_Region() throws IOException, URISyntaxException {
        HttpUriRequest request = new HttpGet(new URL("http://localhost:" + port + "/api/region/" + 25).toURI());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(SC_NOT_FOUND);
    }
}
