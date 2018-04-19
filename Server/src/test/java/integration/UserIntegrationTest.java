package integration;

import fr.univ_smb.isc.m2.models.User;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
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
public class UserIntegrationTest {
    private int port = 8080;
    private JSONArray sample = JsonReader.getArray("Users");
    private JSONObject sampleUnit = JsonReader.getObject("User_1");

    public UserIntegrationTest() throws IOException, ParseException {
    }

    // Récupère les utilisateurs à l'URL /api/user
    List<User> getUsers() throws IOException, URISyntaxException {
        HttpUriRequest request = new HttpGet(new URL("http://localhost:" + port + "/api/user/").toURI());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        return RetrieveUtil.retrieveResourceFromResponse(response, new ArrayList<User>().getClass());
    }

    @Test
    public void T1_Should_200_On_All_Users() throws IOException, URISyntaxException {
        HttpUriRequest request = new HttpGet(new URL("http://localhost:" + port + "/api/user/").toURI());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(SC_OK);
    }

    @Test
    public void T2_Should_Find_All_Users() throws IOException, URISyntaxException {
        assertThat(getUsers().size()).isEqualTo(2);
    }

    @Test
    public void T3_Should_All_Users_Equal_To() throws IOException, URISyntaxException, ParseException {
        JSONArray json = JsonReader.toArray(getUsers());

        assertThat(json).containsExactlyInAnyOrderElementsOf(sample);
    }

    @Test
    public void T4_Should_200_On_One_User() throws IOException, URISyntaxException {
        HttpUriRequest request = new HttpGet(new URL("http://localhost:" + port + "/api/user/2").toURI());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(SC_OK);
    }

    @Test
    public void T5_should_404_On_Fake_User() throws IOException, URISyntaxException {
        HttpUriRequest request = new HttpGet(new URL("http://localhost:" + port + "/api/user/" + 3).toURI());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(SC_NOT_FOUND);
    }

    @Test
    public void T6_Should_One_User_Equals_To() throws IOException, URISyntaxException, ParseException {
        HttpUriRequest request = new HttpGet(new URL("http://localhost:" + port + "/api/user/2").toURI());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        User user = RetrieveUtil.retrieveResourceFromResponse(response, User.class);
        JSONObject json = JsonReader.toObject(user);

        assertThat(json).isEqualTo(sampleUnit);
    }


}
