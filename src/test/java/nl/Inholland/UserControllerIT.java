package nl.Inholland;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertEquals;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserControllerIT {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Before // If everything fails, login is broken.
    public void setupLogin() throws IOException {
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        HttpEntity<String> entity = new HttpEntity<String>("username=bart&password=1234", headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/Login"),
                HttpMethod.POST, entity, String.class);
        Map<String,String> data = new ObjectMapper().readValue(response.getBody(), HashMap.class);
        headers.add("Authorization", "Bearer "+ data.get("token"));
        headers.remove("Content-Type");
    }
    @Test
    public void searchOnAllUsers() throws ParseException {

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/Employee/Users"),
                HttpMethod.GET, entity, String.class);

        JSONParser helper = new JSONParser();
        List<JSONObject> users = (List<JSONObject>) helper.parse(response.getBody());
        assertEquals(3, users.size());
    }

    @Test
    public void searchOnCriteriaEquality() throws ParseException {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/Employee/Users?search=username:bart"),
                HttpMethod.GET, entity, String.class);

        JSONParser helper = new JSONParser();
        List<JSONObject> users = (List<JSONObject>) helper.parse(response.getBody());
        assertEquals(1, users.size());
    }

    @Test
    public void searchOnCriteriaNegation() throws ParseException {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/Employee/Users?search=username!bart"),
                HttpMethod.GET, entity, String.class);

        JSONParser helper = new JSONParser();
        List<JSONObject> users = (List<JSONObject>) helper.parse(response.getBody());
        assertEquals(2, users.size());
    }

    @Test
    public void searchOnCriteriaLike() throws ParseException {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/Employee/Users?search=email:potato*"),
                HttpMethod.GET, entity, String.class);

        JSONParser helper = new JSONParser();
        List<JSONObject> users = (List<JSONObject>) helper.parse(response.getBody());
        assertEquals(2, users.size());
    }

    @Test
    public void whenGivenPathIdToRetrieveReturnUser() throws ParseException {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/Employee/Users/10000001"),
                HttpMethod.GET, entity, String.class);

        assertNotNull(response.getBody());
    }
    @Test
    public void whenGivenPathIdToDeleteReturn201(){
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/Employee/Users/10000001"),
                HttpMethod.DELETE, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void whenLoginDeletedUserReturn404(){
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        HttpEntity<String> entity = new HttpEntity<String>("username=bartS&password=1234", headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/Login"),
                HttpMethod.POST, entity, String.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }
    @Test
    public void whenGivenUserResult201() throws ParseException {
        String userJSON = "{\n" +
                "  \"firstName\": \"John\",\n" +
                "  \"lastName\": \"Doe\",\n" +
                "  \"email\": \"test@bank.com\",\n" +
                "  \"phone\": \"31631231234\",\n" +
                "  \"username\": \"JohnDoe\",\n" +
                "  \"password\": \"welkom21\",\n" +
                "  \"dateCreated\": \"7/3/2019, 9:21:25 PM\",\n" +
                "  \"birthday\": \"27-11-1998\",\n" +
                "  \"initRole\": \"ROLE_EMPLOYEE\"\n" +
                "}";
        headers.add("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<String>(userJSON, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/Employee/Users"),
                HttpMethod.POST, entity, String.class);

        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        headers.remove("Content-Type");
    }
    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
