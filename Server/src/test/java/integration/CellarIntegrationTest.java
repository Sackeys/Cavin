package integration;

import fr.univ_smb.isc.m2.models.Cellar;
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
public class CellarIntegrationTest {

    private int port = 8080;
    private JSONArray sample = JsonReader.getArray("Cellars");
    private JSONObject sampleUnit1 = JsonReader.getObject("Cellar_1");
    private JSONObject sampleUnit2 = JsonReader.getObject("Cellar_2");
    private JSONObject sampleUnit3 = JsonReader.getObject("Cellar_3");


    public CellarIntegrationTest() throws IOException, ParseException {
    }

    // Récupère les caves d'un utilisateur à l'URL /api/cellar?user=1
    List<Cellar> getCellars() throws IOException, URISyntaxException {
        HttpUriRequest request = new HttpGet(new URL("http://localhost:" + port + "/api/cellar?user=1").toURI());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        return RetrieveUtil.retrieveResourceFromResponse(response, new ArrayList<Cellar>().getClass());
    }

    @Test
    public void T1_Should_200_On_All_Cellars() throws IOException, URISyntaxException {
        HttpUriRequest request = new HttpGet(new URL("http://localhost:" + port + "/api/cellar?user=1").toURI());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(SC_OK);
    }

    @Test
    public void T2_Should_Find_All_Cellars() throws IOException, URISyntaxException {
        assertThat(getCellars().size()).isEqualTo(2);
    }

    @Test
    public void T3_Should_All_Cellars_Equal_To() throws IOException, URISyntaxException, ParseException {
        JSONArray json = JsonReader.toArray(getCellars());

        assertThat(json).containsExactlyInAnyOrderElementsOf(sample);
    }

    @Test
    public void T4_Success_On_Add_One_Cellar() throws IOException, URISyntaxException, ParseException {
        HttpPost request = new HttpPost(new URL("http://localhost:" + port + "/api/cellar?user=1").toURI());
        String data = "{" +
                "\"label\": \"Nouvelle cave\", " +
                "\"description\": \"Pour enfermer des gens...\"" +
                "}";

        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        request.setEntity(new StringEntity(data, ContentType.APPLICATION_FORM_URLENCODED));

        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        Cellar cellar = RetrieveUtil.retrieveResourceFromResponse(response, Cellar.class);
        JSONObject json = JsonReader.toObject(cellar);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(SC_OK);
        assertThat(json).isEqualTo(sampleUnit1);
        assertThat(getCellars().size()).isEqualTo(3);
    }

    @Test
    public void T5_Success_On_Add_One_Partial_Cellar() throws IOException, URISyntaxException, ParseException {
        HttpPost request = new HttpPost(new URL("http://localhost:" + port + "/api/cellar?user=1").toURI());
        String data = "{" +
                "\"label\": \"Une autre cave\"" +
                "}";

        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        request.setEntity(new StringEntity(data, ContentType.APPLICATION_FORM_URLENCODED));

        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        Cellar cellar = RetrieveUtil.retrieveResourceFromResponse(response, Cellar.class);
        JSONObject json = JsonReader.toObject(cellar);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(SC_OK);
        assertThat(json).isEqualTo(sampleUnit2);
        assertThat(getCellars().size()).isEqualTo(4);
    }

    @Test
    public void T6_404_On_Add_Fake_User() throws IOException, URISyntaxException {
        HttpPost request = new HttpPost(new URL("http://localhost:" + port + "/api/cellar?user=22").toURI());
        String data = "{" +
                "\"label\": \"Une autre cave\"" +
                "}";

        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        request.setEntity(new StringEntity(data, ContentType.APPLICATION_FORM_URLENCODED));

        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(SC_NOT_FOUND);
    }

    @Test
    public void T7_404_On_Add_Fake_Cellar() throws IOException, URISyntaxException {
        HttpPost request = new HttpPost(new URL("http://localhost:" + port + "/api/cellar?user=1").toURI());
        String data = "{" +
                "\"description\": \"Autre chose...\"" +
                "}";

        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        request.setEntity(new StringEntity(data, ContentType.APPLICATION_FORM_URLENCODED));

        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(SC_NOT_FOUND);
    }

    @Test
    public void T8_Should_Success_On_Remove_One_Cellar() throws IOException, URISyntaxException, ParseException {
        HttpUriRequest request = new HttpDelete(new URL("http://localhost:" + port + "/api/cellar/1?user=1").toURI());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        Cellar cellar = RetrieveUtil.retrieveResourceFromResponse(response, Cellar.class);
        JSONObject json = JsonReader.toObject(cellar);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(SC_OK);
        assertThat(json).isEqualTo(sampleUnit3);
        assertThat(getCellars().size()).isEqualTo(3);
    }

    @Test
    public void T9_Should_404_On_Remove_Fake_Cellar() throws IOException, URISyntaxException, ParseException {
        HttpUriRequest request = new HttpDelete(new URL("http://localhost:" + port + "/api/cellar/9?user=1").toURI());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(SC_NOT_FOUND);
    }

    @Test
    public void TA_Should_404_On_Remove_One_Cellar_From_Another_User() throws IOException, URISyntaxException, ParseException {
        HttpUriRequest request = new HttpDelete(new URL("http://localhost:" + port + "/api/cellar/2?user=2").toURI());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(SC_NOT_FOUND);
    }
}
