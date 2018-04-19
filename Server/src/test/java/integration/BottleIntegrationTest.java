package integration;

import fr.univ_smb.isc.m2.models.Bottle;
import fr.univ_smb.isc.m2.models.Color;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BottleIntegrationTest {

    private int port = 8080;
    private JSONArray sample = JsonReader.getArray("Bottles");
    private JSONObject sampleUnit1 = JsonReader.getObject("Bottle_1");
    private JSONObject sampleUnit2 = JsonReader.getObject("Bottle_2");

    public BottleIntegrationTest() throws IOException, ParseException {
    }

    // Récupère les bouteilles à l'URL /api/bottle
    List<Color> getBottles() throws IOException, URISyntaxException {
        HttpUriRequest request = new HttpGet(new URL("http://localhost:" + port + "/api/bottle/").toURI());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        return RetrieveUtil.retrieveResourceFromResponse(response, new ArrayList<Bottle>().getClass());
    }

    @Test
    public void T1_Should_200_On_All_Bottles() throws IOException, URISyntaxException {
        HttpUriRequest request = new HttpGet(new URL("http://localhost:" + port + "/api/bottle/").toURI());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(SC_OK);
    }

    @Test
    public void T2_Should_Find_All_Bottles() throws IOException, URISyntaxException {
        assertThat(getBottles().size()).isEqualTo(3);
    }

    @Test
    public void T3_Should_All_Bottles_Equal_To() throws IOException, URISyntaxException, ParseException {
        JSONArray json = JsonReader.toArray(getBottles());

        assertThat(json).containsExactlyInAnyOrderElementsOf(sample);
    }

    @Test
    public void T4_Should_200_On_One_Bottle() throws IOException, URISyntaxException {
        HttpUriRequest request = new HttpGet(new URL("http://localhost:" + port + "/api/bottle/1").toURI());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(SC_OK);
    }

    @Test
    public void T5_Should_404_On_Fake_Bottle() throws IOException, URISyntaxException {
        HttpUriRequest request = new HttpGet(new URL("http://localhost:" + port + "/api/bottle/" + 25).toURI());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(SC_NOT_FOUND);
    }

    @Test
    public void T6_Should_One_Bottle_Equals_To() throws IOException, URISyntaxException, ParseException {
        HttpUriRequest request = new HttpGet(new URL("http://localhost:" + port + "/api/bottle/1").toURI());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        Bottle bottle = RetrieveUtil.retrieveResourceFromResponse(response, Bottle.class);
        JSONObject json = JsonReader.toObject(bottle);

        assertThat(json).isEqualTo(sampleUnit1);
    }

    @Test
    public void T7_Success_On_Add_One_Bottle() throws IOException, URISyntaxException, ParseException {
        HttpPost request = new HttpPost(new URL("http://localhost:" + port + "/api/bottle").toURI());
        String data = "{" +
                "\"label\": \"Nouveau vin\", " +
                "\"region\": 1, " +
                "\"color\": 1, " +
                "\"year\": 2015, " +
                "\"grape\": 2" +
                "}";

        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        request.setEntity(new StringEntity(data, ContentType.APPLICATION_FORM_URLENCODED));

        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        Bottle bottle = RetrieveUtil.retrieveResourceFromResponse(response, Bottle.class);
        JSONObject json = JsonReader.toObject(bottle);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(SC_OK);
        assertThat(json).isEqualTo(sampleUnit2);
        assertThat(getBottles().size()).isEqualTo(4);
    }

    @Test
    public void T8_Should_404_On_Add_Fake_Bottle() throws IOException, URISyntaxException {
        HttpPost request = new HttpPost(new URL("http://localhost:" + port + "/api/bottle").toURI());
        String data = "{" +
                "\"label\": \"Mauvais vin\", " +
                "\"region\": 69, " +
                "\"color\": 69, " +
                "\"year\": -69, " +
                "\"grape\": 69" +
                "}";

        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        request.setEntity(new StringEntity(data, ContentType.APPLICATION_FORM_URLENCODED));
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(SC_NOT_FOUND);
    }

    @Test
    public void T9_Should_Success_On_Remove_One_Bottle() throws IOException, URISyntaxException, ParseException {
        HttpDelete request = new HttpDelete(new URL("http://localhost:" + port + "/api/bottle/1").toURI());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        Bottle bottle = RetrieveUtil.retrieveResourceFromResponse(response, Bottle.class);
        JSONObject json = JsonReader.toObject(bottle);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(SC_OK);
        assertThat(json).isEqualTo(sampleUnit1);
        assertThat(getBottles().size()).isEqualTo(3);
    }

    @Test
    public void TA_Should_404_On_Remove_Fake_Bottle() throws IOException, URISyntaxException {
        HttpDelete request = new HttpDelete(new URL("http://localhost:" + port + "/api/bottle/52").toURI());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(SC_NOT_FOUND);
    }
}
