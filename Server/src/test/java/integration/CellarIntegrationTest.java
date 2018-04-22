package integration;

import fr.univ_smb.isc.m2.models.Cellar;
import fr.univ_smb.isc.m2.models.Slot;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
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
    private JSONObject sampleSlot1 = JsonReader.getObject("Slot_1");
    private JSONObject sampleSlot2 = JsonReader.getObject("Slot_2");
    private JSONObject sampleSlot3 = JsonReader.getObject("Slot_3");
    private JSONObject sampleSlot4 = JsonReader.getObject("Slot_4");
    private JSONObject sampleSlot5 = JsonReader.getObject("Slot_5");
    private JSONObject sampleSlot6 = JsonReader.getObject("Slot_6");


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
        assertThat(getCellars().size()).isEqualTo(3);
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
        assertThat(getCellars().size()).isEqualTo(4);
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
        assertThat(getCellars().size()).isEqualTo(5);
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
        assertThat(getCellars().size()).isEqualTo(4);
    }

    @Test
    public void T9_Should_404_On_Remove_Fake_Cellar() throws IOException, URISyntaxException {
        HttpUriRequest request = new HttpDelete(new URL("http://localhost:" + port + "/api/cellar/9?user=1").toURI());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(SC_NOT_FOUND);
    }

    @Test
    public void TA_Should_404_On_Remove_One_Cellar_From_Another_User() throws IOException, URISyntaxException {
        HttpUriRequest request = new HttpDelete(new URL("http://localhost:" + port + "/api/cellar/2?user=2").toURI());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(SC_NOT_FOUND);
    }

    @Test
    public void TB_Should_404_On_Add_One_Bottle_With_Negative_Count() throws IOException, URISyntaxException {
        HttpPost request = new HttpPost(new URL("http://localhost:" + port + "/api/cellar/3?user=1").toURI());
        String data = "{" +
                "\"bottle\": \"2\", " +
                "\"count\": \"-1\"" +
                "}";

        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        request.setEntity(new StringEntity(data, ContentType.APPLICATION_FORM_URLENCODED));

        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(SC_NOT_FOUND);
    }

    @Test
    public void TC_Should_404_On_Add_One_Bottle_With_Null_Count() throws IOException, URISyntaxException {
        HttpPost request = new HttpPost(new URL("http://localhost:" + port + "/api/cellar/3?user=1").toURI());
        String data = "{" +
                "\"bottle\": \"2\", " +
                "\"count\": \"0\"" +
                "}";

        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        request.setEntity(new StringEntity(data, ContentType.APPLICATION_FORM_URLENCODED));

        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(SC_NOT_FOUND);
    }

    @Test
    public void TD_Should_404_On_Add_One_Bottle_In_Unexisting_Cellar() throws IOException, URISyntaxException {
        HttpPost request = new HttpPost(new URL("http://localhost:" + port + "/api/cellar/20?user=1").toURI());
        String data = "{" +
                "\"bottle\": \"2\", " +
                "\"count\": \"2\"" +
                "}";

        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        request.setEntity(new StringEntity(data, ContentType.APPLICATION_FORM_URLENCODED));

        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(SC_NOT_FOUND);
    }

    @Test
    public void TE_Should_Success_On_Add_One_Bottle_In_Existing_Cellar() throws IOException, URISyntaxException, ParseException {
        HttpPost request = new HttpPost(new URL("http://localhost:" + port + "/api/cellar/3?user=1").toURI());
        String data = "{" +
                "\"bottle\": \"2\", " +
                "\"count\": \"2\"" +
                "}";

        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        request.setEntity(new StringEntity(data, ContentType.APPLICATION_FORM_URLENCODED));

        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        Slot slot = RetrieveUtil.retrieveResourceFromResponse(response, Slot.class);
        JSONObject json = JsonReader.toObject(slot);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(SC_OK);
        assertThat(json).isEqualTo(sampleSlot1);
    }

    @Test
    public void TF_Should_Success_On_Add_One_Bottle_In_New_Cellar() throws IOException, URISyntaxException, ParseException {
        HttpPost request = new HttpPost(new URL("http://localhost:" + port + "/api/cellar/3?user=1").toURI());
        String data = "{" +
                "\"bottle\": \"3\", " +
                "\"count\": \"8\"" +
                "}";

        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        request.setEntity(new StringEntity(data, ContentType.APPLICATION_FORM_URLENCODED));

        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        Slot slot = RetrieveUtil.retrieveResourceFromResponse(response, Slot.class);
        JSONObject json = JsonReader.toObject(slot);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(SC_OK);
        assertThat(json).isEqualTo(sampleSlot2);
    }

    @Test
    public void TG_Should_Success_On_Add_Max_Bottle_In_Cellar() throws IOException, URISyntaxException, ParseException {
        HttpPost request = new HttpPost(new URL("http://localhost:" + port + "/api/cellar/3?user=1").toURI());
        String data = "{" +
                "\"bottle\": \"3\", " +
                "\"count\": \"20\"" +
                "}";

        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        request.setEntity(new StringEntity(data, ContentType.APPLICATION_FORM_URLENCODED));

        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        Slot slot = RetrieveUtil.retrieveResourceFromResponse(response, Slot.class);
        JSONObject json = JsonReader.toObject(slot);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(SC_OK);
        assertThat(json).isEqualTo(sampleSlot3);
    }

    @Test
    public void TH_Should_NotChange_On_Up_Cellar_Bottle() throws IOException, URISyntaxException, ParseException {
        HttpUriRequest request = new HttpPut(new URL("http://localhost:" + port + "/api/cellar/3/up?user=1&slot=2").toURI());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        Slot slot = RetrieveUtil.retrieveResourceFromResponse(response, Slot.class);
        JSONObject json = JsonReader.toObject(slot);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(SC_OK);
        assertThat(json).isEqualTo(sampleSlot4);
    }

    @Test
    public void TI_Should_Success_On_Up_Cellar_Bottle_Max_To_Max() throws IOException, URISyntaxException, ParseException {
        HttpUriRequest request = new HttpPut(new URL("http://localhost:" + port + "/api/cellar/3/up?user=1&slot=3").toURI());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        Slot slot = RetrieveUtil.retrieveResourceFromResponse(response, Slot.class);
        JSONObject json = JsonReader.toObject(slot);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(SC_OK);
        assertThat(json).isEqualTo(sampleSlot5);
    }

    @Test
    public void TJ_Should_404_On_Up_Cellar_Unexisting_Bottle() throws IOException, URISyntaxException {
        HttpUriRequest request = new HttpPut(new URL("http://localhost:" + port + "/api/cellar/3/up?user=1&slot=6").toURI());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(SC_NOT_FOUND);
    }

    @Test
    public void TK_Should_404_On_Up_Cellar_Unexisting_Cellar() throws IOException, URISyntaxException {
        HttpUriRequest request = new HttpPut(new URL("http://localhost:" + port + "/api/cellar/5/up?user=1&slot=3").toURI());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(SC_NOT_FOUND);
    }

    @Test
    public void TL_Should_Succes_On_Down_Cellar_Bottle() throws IOException, URISyntaxException, ParseException {
        HttpUriRequest request = new HttpPut(new URL("http://localhost:" + port + "/api/cellar/3/down?user=1&slot=2").toURI());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        Slot slot = RetrieveUtil.retrieveResourceFromResponse(response, Slot.class);
        JSONObject json = JsonReader.toObject(slot);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(SC_OK);
        assertThat(json).isEqualTo(sampleSlot6);
    }
}
