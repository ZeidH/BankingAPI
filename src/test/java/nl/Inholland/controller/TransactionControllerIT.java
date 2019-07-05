package nl.Inholland.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.Inholland.App;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TransactionControllerIT {
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
                createURLWithPort("/Employee/Transactions"),
                HttpMethod.GET, entity, String.class);

        JSONParser helper = new JSONParser();
        List<JSONObject> transactions = (List<JSONObject>) helper.parse(response.getBody());
        assertEquals(1, transactions.size());
    }

    @Test
    public void searchOnCriteriaEquality() throws ParseException {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/Employee/Transactions?search=amount:5.00"),
                HttpMethod.GET, entity, String.class);

        JSONParser helper = new JSONParser();
        List<JSONObject> transactions = (List<JSONObject>) helper.parse(response.getBody());
        assertEquals(1, transactions.size());
    }

    @Test
    public void searchOnCriteriaBiggerThan() throws ParseException {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/Employee/Transactions?search=amount>4"),
                HttpMethod.GET, entity, String.class);

        JSONParser helper = new JSONParser();
        List<JSONObject> transactions = (List<JSONObject>) helper.parse(response.getBody());
        assertEquals(1, transactions.size());
    }

    // NL83INHO1111111111 //NL36INHO2222222222
    @Test
    public void whenGivenIBANReturnAllAssociatedTransactions() throws ParseException {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/Customer/NL83INHO1111111111/Transactions"),
                HttpMethod.GET, entity, String.class);

        JSONParser helper = new JSONParser();
        List<JSONObject> transactions = (List<JSONObject>) helper.parse(response.getBody());
        assertEquals(1, transactions.size());
    }
    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
